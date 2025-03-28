package com.cryptory.be.admin.exception;

import com.cryptory.be.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AdminErrorCode implements ErrorCode {

    UNAUTHORIZED_ROLE(HttpStatus.BAD_REQUEST, "작업을 수행할 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
