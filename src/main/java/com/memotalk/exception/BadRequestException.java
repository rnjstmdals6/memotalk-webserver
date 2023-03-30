package com.memotalk.exception;

import com.memotalk.exception.enumeration.ErrorCode;

public class BadRequestException extends RuntimeException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

}
