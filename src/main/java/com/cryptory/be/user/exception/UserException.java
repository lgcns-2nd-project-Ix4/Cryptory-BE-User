package com.cryptory.be.user.exception;

import com.cryptory.be.global.exception.CustomException;

public class UserException extends CustomException {
    public UserException(UserErrorCode errorCode) {
        super(errorCode);
    }
}
