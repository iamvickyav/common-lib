package com.iamvickyav.commonlib.exception;

public class BusinessException extends RuntimeException {
    private String code;
    private String message;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.message);
        this.code = errorCode.code;
        this.message = errorCode.message;
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
