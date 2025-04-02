package com.cryptory.be.admin.exception;

import com.cryptory.be.global.exception.CustomException;

public class AdminException extends CustomException {
    public AdminException(AdminErrorCode errorCode) {
        super(errorCode);
    }
}
