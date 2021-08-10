package com.guomz.orderingsys.exception;

import com.guomz.orderingsys.domain.resp.BusinessResponse;
import com.guomz.orderingsys.enums.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理未知异常
     *
     * @param e
     * @param req
     * @return
     */
    @ExceptionHandler(Exception.class)
    ResponseEntity handleUnkonwnException(Exception e, HttpServletRequest req) {
        log.error(e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        HttpHeaders headers = new HttpHeaders();
        BusinessResponse response = new BusinessResponse(ResponseEnum.UNKNOW_ERROR);
        return new ResponseEntity<>(response, headers, httpStatus);
    }

    @ExceptionHandler(BusinessException.class)
    ResponseEntity handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        HttpHeaders headers = new HttpHeaders();
        BusinessResponse response = new BusinessResponse(e.getCode(), e.getMsg());
        return new ResponseEntity<>(response, headers, httpStatus);
    }

    /**
     * 捕获303对于body中的对象字段校验
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String message = fieldErrors.get(0).getDefaultMessage();
            log.error(message, e);
        }
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        HttpHeaders headers = new HttpHeaders();
        BusinessResponse response = new BusinessResponse(ResponseEnum.INVALID_ARGS);
        return new ResponseEntity<>(response, headers, httpStatus);
    }

    /**
     * 捕获303对于request param单个参数的校验
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        HttpHeaders headers = new HttpHeaders();
        BusinessResponse response = new BusinessResponse(ResponseEnum.INVALID_ARGS);
        return new ResponseEntity<>(response, headers, httpStatus);
    }

    /**
     * 捕获303对于get请求对象字段校验
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    ResponseEntity<Object> handleBindException(BindException e, HttpServletRequest request) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String message = fieldErrors.get(0).getDefaultMessage();
            log.error(message, e);
        }
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        HttpHeaders headers = new HttpHeaders();
        BusinessResponse response = new BusinessResponse(ResponseEnum.INVALID_ARGS);
        return new ResponseEntity<>(response, headers, httpStatus);
    }
}
