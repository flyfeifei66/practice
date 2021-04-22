package com.zhaojufei.practice.repeatsubmit.advice;

import com.zhaojufei.practice.repeatsubmit.annotation.UnRepeatParam;
import com.zhaojufei.practice.repeatsubmit.annotation.UnRepeatTenantIdField;
import com.zhaojufei.practice.repeatsubmit.annotation.UnRepeatTokenField;
import com.zhaojufei.practice.repeatsubmit.annotation.UnRepeatUserField;
import com.zhaojufei.practice.repeatsubmit.common.*;
import com.zhaojufei.practice.repeatsubmit.constant.UnRepeatSubmitConstant;
import com.zhaojufei.practice.repeatsubmit.dto.Token;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 基于注解的防重复提交切面类，由于仿重复提交是针对的每一个用户，因此需要用户信息。
 * 如果用户信息也是基于注解注入的，需要控制切面的执行顺序，确保本切面在用户切面信息之后。
 * 如果不使用注解，也可以直接使用UnRepeatSubmitService，通过代码显示判断是否是重复提交。
 * 
 * @author zhaojufei
 */
@Aspect
@Component
@Slf4j
@Order(AdviceOrder.UN_REPEAT_SUBMIT)
public class UnRepeatSubmitAdvice {

    @Autowired
    private ScmRedisTemplate scmRedisTemplate;

    @Around("@annotation(com.zhaojufei.practice.repeatsubmit.annotation.UnRepeatSubmit)")
    public Object around(ProceedingJoinPoint point) {
        try {
            return execute(point);
        } catch (BusinessException e) {
            throw e;
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("防重复提交出现异常, errorMsg = {}, error stack = {}", e.getMessage(), e);
            throw new BusinessException("系统异常，提交数据处理失败，请稍后重试");
        }
    }

    /**
     * 判断是否是重复提交方法
     * 
     * @param point
     * @return
     * @throws Throwable
     */
    public Object execute(ProceedingJoinPoint point) throws Throwable {
        // 获取参数
        UnRepeatParaHandler parameterHandler = new UnRepeatParaHandler(point).resolveParams();

        // 先锁住用户form提交这个应用场景key
        return scmRedisTemplate.lock(
                RedisKey.getUserFormOpsLockKey(parameterHandler.getTenantId(), parameterHandler.getUserId()),
                new LockCallback() {
                    // 构造map的key
                    String key = RedisKey.getUserFormOpsTokenMapLockKey(parameterHandler.getTenantId(),
                            parameterHandler.getUserId());

                    @Override
                    public Object success() {
                        try {
                            // 判断用户的token是否合法
                            if (!scmRedisTemplate.mapOpsHashKey(key, parameterHandler.getToken())) {
                                throw new BusinessException("重复提交、页面长时间未操作或非法操作或者，请刷新后重试。");
                            }

                            Object result = point.proceed(point.getArgs());

                            // 如果返回类型为非标准信息类型直接返回，无法重复使用token
                            if (!(result instanceof MessageBody)) {
                                scmRedisTemplate.mapOpsDel(key, parameterHandler.getToken());
                                return result;
                            }
                            // 如果是MessageBody类型， 且操作成功，删除token
                            if (result != null && ((MessageBody) result).getSuccess()) {
                                scmRedisTemplate.mapOpsDel(key, parameterHandler.getToken());
                                // 如果操作不成功，放回token，供下次使用
                            } else {
                                Token token = new Token(parameterHandler.getToken(), new Date());
                                scmRedisTemplate.mapOpsPut(key, parameterHandler.getToken(), token);
                                scmRedisTemplate.expireSeconds(key, UnRepeatSubmitConstant.TOKEN_EXPIRE_TIME);
                            }

                            return result;

                        } catch (BusinessException e) {
                            throw e;
                        } catch (Throwable e) {
                            log.error("防重复提交出现异常, errorMsg = {}, error stack = {}", e.getMessage(), e);
                            throw new BusinessException("系统异常，提交数据处理失败，请稍后重试。");
                        }
                    }
                });
    }

    /**
     * 参数解析内部类
     */
    private class UnRepeatParaHandler {

        private ProceedingJoinPoint point;
        private String userId;
        private String tenantId;
        private String token;

        public UnRepeatParaHandler(ProceedingJoinPoint point) {
            this.point = point;
        }

        public UnRepeatParaHandler resolveParams() throws IllegalAccessException {

            // 1、获取到所有的参数值的数组
            Object[] args = point.getArgs();

            // 2、获取参数
            MethodSignature signature = (MethodSignature) point.getSignature();
            Parameter[] parameters = signature.getMethod().getParameters();

            log.info("参数列表：{}", parameters);
            log.info("值列表{}", args);

            // 3、寻找本aop需要的参数
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];

                // 只有标有VCParam注解的类，才可以执行
                if (parameter.getAnnotation(UnRepeatParam.class) == null) {
                    continue;
                }

                // 获取类型所对应的参数对象
                Object arg = args[i];

                // 获取当前参数类型
                Class<?> paramClazzTmp = parameter.getType();

                // 得到参数的所有成员变量
                List<Field> fieldList = new ArrayList<>();

                // 遍历paramClazzTmp及其所有父类，并收集所有field
                while (paramClazzTmp != null) {
                    fieldList.addAll(Arrays.asList(paramClazzTmp.getDeclaredFields()));
                    paramClazzTmp = paramClazzTmp.getSuperclass();
                }
                for (Field field : fieldList) {
                    field.setAccessible(true);
                    UnRepeatTenantIdField tenantIdField = field.getAnnotation(UnRepeatTenantIdField.class);
                    if (tenantIdField != null) {
                        tenantId = (String) field.get(arg);

                    }
                    UnRepeatUserField userIdField = field.getAnnotation(UnRepeatUserField.class);
                    if (userIdField != null) {
                        userId = (String) field.get(arg);
                    }
                    UnRepeatTokenField unRepeatTokenField = field.getAnnotation(UnRepeatTokenField.class);
                    if (unRepeatTokenField != null) {
                        token = (String) field.get(arg);
                    }
                }

                // 所有参数已经找到，不再继续找
                if (StringUtils.isNotBlank(tenantId) && StringUtils.isNotBlank(userId)
                        && StringUtils.isNotBlank(token)) {
                    break;
                }
            }

            ZjfAssert.notBlank(tenantId, "商户参数获取失败，数据提交失败");
            ZjfAssert.notBlank(userId, "用户参数获取失败，数据提交失败");
            ZjfAssert.notBlank(token, "页面Token参数获取失败，数据提交失败");

            return this;
        }

        public String getUserId() {
            return userId;
        }

        public String getTenantId() {
            return tenantId;
        }

        public String getToken() {
            return token;
        }
    }
}
