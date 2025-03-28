package com.cryptory.be.issue.exception;

import com.cryptory.be.global.exception.CustomException;

public class IssueException extends CustomException {
    public IssueException(IssueErrorCode errorCode) {
        super(errorCode);
    }
}
