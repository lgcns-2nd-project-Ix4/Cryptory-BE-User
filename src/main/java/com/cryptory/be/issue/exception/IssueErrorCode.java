package com.cryptory.be.issue.exception;

import com.cryptory.be.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum IssueErrorCode implements ErrorCode {

    NOT_EXIST_ISSUE(HttpStatus.NOT_FOUND, "존재하지 않는 이슈입니다."),

    NOT_EXIST_ISSUE_COMMENT(HttpStatus.NOT_FOUND, "존재하지 않는 코멘트입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
