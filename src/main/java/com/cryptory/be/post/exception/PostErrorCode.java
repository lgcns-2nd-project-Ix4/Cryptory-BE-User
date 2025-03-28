package com.cryptory.be.post.exception;

import com.cryptory.be.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {

    NOT_EXIST_POST(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
