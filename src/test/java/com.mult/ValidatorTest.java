package com.mult;

import com.mult.basic.Result;
import com.mult.basic.utils.ValidatorUtil;
import com.mult.model.Seckill;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author Weirdo
 * @date 2018/8/17 11:15
 */
@Slf4j
public class ValidatorTest {

    private static Validator validator;

    @BeforeClass
    public static void setUpVolidator(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }

    @Test
    public void codeNullTest(){
        Result result = new Result();
        result.setCode("a");
        result.setStat(1);
        result.setMsg("123");
        result.setData("强");
        Set<ConstraintViolation<Result>> constraintViolations= validator.validate(result);
        String errorMessage = constraintViolations.iterator().next().getMessage();
        log.info(errorMessage);
    }

    @Test
    public void validTest(){
        Seckill sckill = new Seckill();
        sckill.setName("");
        Result result = ValidatorUtil.validObject(sckill);
        String s = result.getMsg();
        System.err.println(s);
    }
}
