package com.mult.basic.utils;

import com.mult.basic.Result;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * 权限校验工具类
 * @author Weirdo
 * @date 2018/8/17 14:12
 */
public class ValidatorUtil {
    public static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public static Result validObject(Object msg) {
        Result result = new Result();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(msg);
        if(constraintViolations.isEmpty()){
            return result;
        }
        StringBuffer buff = new StringBuffer();
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            buff.append(constraintViolation.getPropertyPath());
            buff.append(constraintViolation.getMessage());
            buff.append("!\n");
        }
        result.setMsg(buff.toString().substring(0,buff.toString().length()-1));
        result.setStat(0);
        return result;
    }
}
