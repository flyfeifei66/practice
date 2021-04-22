package com.zhaojufei.practice.web.common.validate;

import com.zhaojufei.practice.web.common.exception.BusinessException;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class ParamsValidator implements SmartValidator {

    private javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        validate(target, errors, null);
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        if (target == null) {
            throw new BusinessException("参数不能为空！");
        } else {
            if (target instanceof String) {
                if (StringUtils.isEmpty(target)) {
                    throw new BusinessException("参数不能为空！");
                }
            } else if (target instanceof Collection) {
                for (Object o : (Collection) target) {
                    validate(o, validationHints);
                }
            } else {
                validate(target, validationHints);
            }
        }
    }

    private void validate(Object target, Object... objs) {
        Set<ConstraintViolation<Object>> violations;
        if (objs == null || objs.length == 0) {
            violations = validator.validate(target);
        } else {
            Set<Class<?>> groups = new LinkedHashSet<Class<?>>();
            for (Object hint : objs) {
                if (hint instanceof Class) {
                    groups.add((Class<?>) hint);
                }
            }
            violations = validator.validate(target, ClassUtils.toClassArray(groups));
        }
        if (violations == null || violations.isEmpty()) {
            return;
        }
        for (ConstraintViolation item : violations) {
            throw new BusinessException(item.getMessage());
        }
    }
}
