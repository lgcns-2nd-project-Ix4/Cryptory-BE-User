package com.cryptory.be.news.exception;

import com.cryptory.be.global.exception.CustomException;

public class NewsException extends CustomException {
    public NewsException(NewsErrorCode errorCode) {
        super(errorCode);
    }
}
