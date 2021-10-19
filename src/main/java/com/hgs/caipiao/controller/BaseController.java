package com.hgs.caipiao.controller;


import com.hgs.caipiao.entity.ErrorCodeList;
import com.hgs.caipiao.entity.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;


@ControllerAdvice
public class BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public <T> HttpResponse<T> ok(T entity) {
        HttpResponse<T> httpResponse = new HttpResponse<>();
        httpResponse.setCode(HttpResponse.Type.OK.getVal());
        httpResponse.setType(HttpResponse.Type.OK);
        httpResponse.setData(entity);
        return httpResponse;
    }

    public <T> HttpResponse<T> ok(T entity, String operationObject) {
        HttpResponse<T> httpResponse = new HttpResponse<>();
        httpResponse.setCode(HttpResponse.Type.OK.getVal());
        httpResponse.setType(HttpResponse.Type.OK);
        httpResponse.setData(entity);
        httpResponse.setOperationObject(operationObject);
        return httpResponse;
    }

    public <T> HttpResponse<T> error(T entity, String msg) {
        HttpResponse<T> httpResponse = new HttpResponse<>();
        httpResponse.setCode(HttpResponse.Type.ERROR.getVal());
        httpResponse.setType(HttpResponse.Type.ERROR);
        httpResponse.setData(entity);
        httpResponse.setMessage(msg);
        return httpResponse;
    }

    public <T> HttpResponse<T> error(T entity, String msg, String operationObject) {
        HttpResponse<T> httpResponse = new HttpResponse<>();
        httpResponse.setCode(HttpResponse.Type.ERROR.getVal());
        httpResponse.setType(HttpResponse.Type.ERROR);
        httpResponse.setData(entity);
        httpResponse.setMessage(msg);
        httpResponse.setOperationObject(operationObject);
        return httpResponse;
    }

    @ExceptionHandler(Exception.class)
    public HttpResponse<String> exceptionHandler(Exception e) {
        logger.error(e.getMessage(), e);
        if (e instanceof ConstraintViolationException) {
            for (ConstraintViolation cv : ((ConstraintViolationException) e).getConstraintViolations()) {
                return error("", cv.getMessage());
            }
        }
        if(e instanceof MethodArgumentNotValidException) {
            List<ObjectError> oes = ((MethodArgumentNotValidException)e).getBindingResult().getAllErrors();
            return error("", oes.get(0).getDefaultMessage());
        }
        return error("", "请求超时");
    }

    public <T> HttpResponse<T> checkResult(T result, String operationObject) {
        if (Objects.isNull(result) || result.equals(0)) {
            return error(result, "", operationObject);
        }
        if (result instanceof String && ErrorCodeList.getErrorCodeList().contains(result)) {
            return error(result, (String) result, operationObject);
        }
        return ok(result, operationObject);
    }

    public <T> HttpResponse<T> checkResult(T result) {
        if (Objects.isNull(result) || result.equals(0)) {
            return error(result, "");
        }
        if (result instanceof String && ErrorCodeList.getErrorCodeList().contains(result)) {
            return error(result, (String) result);
        }
        return ok(result);
    }
}
