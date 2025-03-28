package com.cryptory.be.user.exception;

import com.cryptory.be.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    DUPLICATE_LOGIN_ID(HttpStatus.CONFLICT, "이미 가입된 아이디입니다."),

    NOT_EXIST_USER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    INVALID_LOGIN_ID(HttpStatus.BAD_REQUEST, "일치하는 아이디가 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    UNAUTHORIZED_ROLE(HttpStatus.FORBIDDEN, "작업을 수행할 권한이 없습니다."),

    DENIED_USER(HttpStatus.FORBIDDEN, "차단된 사용자입니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "유효하지 않은 서명입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "로그인이 만료되었습니다. 다시 로그인해 주세요.");

    private final HttpStatus httpStatus;
    private final String message;
}
