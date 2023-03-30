package com.memotalk.exception;

import com.memotalk.exception.enumeration.ErrorCode;

public class InternalServerException extends RuntimeException {

    public InternalServerException(ErrorCode errorCodeMessage) {
        super(errorCodeMessage.getMessage());
    }
}