package com.cryptory.be.coin.exception;

import com.cryptory.be.global.exception.CustomException;

public class CoinException extends CustomException {
    public CoinException(CoinErrorCode errorCode) {
        super(errorCode);
    }
}
