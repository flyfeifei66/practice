package com.zhaojufei.practice.myfeature.repeatsubmit.controller;

import com.zhaojufei.practice.myfeature.repeatsubmit.common.*;
import com.zhaojufei.practice.myfeature.repeatsubmit.constant.UnRepeatSubmitConstant;
import com.zhaojufei.practice.myfeature.repeatsubmit.dto.CurrentUserDTO;
import com.zhaojufei.practice.myfeature.repeatsubmit.dto.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/credit/token")
public class TokenController {

    /**
     * 缓存、分布式锁工具类
     */
    @Autowired
    private ScmRedisTemplate scmRedisTemplate;

    /**
     * 获取token接口
     *
     * @param userInfo
     * @return
     */
    @PostMapping("/get")
    public MessageBody getToken( CurrentUserDTO userInfo) {

        ZjfAssert.notBlank(userInfo.getTenantId(), "系统处理商户信息发生错误，请稍后重试");
        ZjfAssert.notBlank(userInfo.getUserId(), "系统处理用户信息发生错误，请稍后重试");

        log.info("{}获取页面防重复提交token处理", userInfo);

        return scmRedisTemplate.tryLock(RedisKey.getUserFormOpsLockKey(userInfo.getTenantId(), userInfo.getUserId()),
                () -> {

                    String isLocked = RedisKey.getUserFormOpsIsLockedLockKey(userInfo.getTenantId(),
                            userInfo.getUserId());

                    // 如果已经无法获取token，提示出剩余时间
                    if (scmRedisTemplate.hasKey(isLocked)) {
                        long remainSeconds = scmRedisTemplate.getExpireSeconds(isLocked);
                        long remainMins = transMin(remainSeconds);
                        MessageBody messageBody = MessageBody
                                .getErrorMessageBody("您页面无效操作次数过多，请" + remainMins + "分钟后再操作");
                        messageBody.setData(remainMins);
                        return messageBody;
                    }

                    String key = RedisKey.getUserFormOpsTokenMapLockKey(userInfo.getTenantId(), userInfo.getUserId());

                    // 颁发token，每次刷新map的生存时间，保证每次获取的token的可用时间
                    String tokenString = UUIDGenerator.getUUID();
                    Token token = new Token(tokenString, new Date());
                    scmRedisTemplate.mapOpsPut(key, tokenString, token);
                    scmRedisTemplate.expireSeconds(key, UnRepeatSubmitConstant.TOKEN_EXPIRE_TIME);

                    long mapSize = scmRedisTemplate.mapOpsSize(key);
                    log.info("{}目前有{}个值", key, mapSize);

                    // 正常情况下token阈值要大于1，小于等于1没有意义
                    int maxTokenNum = UnRepeatSubmitConstant.MAX_TOKEN_NUM > 1 ? UnRepeatSubmitConstant.MAX_TOKEN_NUM : 1000;

                    // 如果token数量达到阈值
                    if (mapSize >= maxTokenNum) {
                        scmRedisTemplate.set(isLocked, tokenString, UnRepeatSubmitConstant.TOKEN_OUTNUMBER_WAITTIME);
                        // 删除Map的key，下次重新计数
                        scmRedisTemplate.delete(key);
                    }

                    return MessageBody.getMessageBody(token);

                }, () -> MessageBody.getErrorMessageBody("获取Token失败，请稍后重试"));
    }

    /**
     * 秒转分，用户提示
     */
    private long transMin(long seconds) {
        return seconds % 60 == 0 ? seconds / 60 : (seconds / 60) + 1;
    }
}
