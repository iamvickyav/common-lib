package com.iamvickyav.commonlib.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    HTTP_CALL_ERROR("HTTP_CALL_ERROR", "Error occurred while making internal HTTP call", HttpStatus.INTERNAL_SERVER_ERROR);

    String code;
    String message;
    HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
