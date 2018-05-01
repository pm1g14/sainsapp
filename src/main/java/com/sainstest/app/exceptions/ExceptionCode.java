package com.sainstest.app.exceptions;

public class ExceptionCode {

    private final String code;
    private final String message;

    public ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
