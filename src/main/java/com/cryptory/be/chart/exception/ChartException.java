package com.cryptory.be.chart.exception;

import com.cryptory.be.global.exception.CustomException;

public class ChartException extends CustomException {
    public ChartException(ChartErrorCode errorCode) {
        super(errorCode);
    }
}
