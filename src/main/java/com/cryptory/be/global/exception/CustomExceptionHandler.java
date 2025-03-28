package com.cryptory.be.global.exception;

import com.cryptory.be.global.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<?> handleCustomException(CustomException e) {
        return new ApiResponse<>(e);
    }

}
