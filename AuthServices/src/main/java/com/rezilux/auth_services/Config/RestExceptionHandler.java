package com.rezilux.auth_services.Config ;


import com.rezilux.auth_services.Exception.AppException;
import com.rezilux.auth_services.dtos.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler (value = {AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleExecption(AppException ex){
        return ResponseEntity.status(ex.getHttpStatus()).body(new ErrorDto(ex.getMessage()));
    }
}
