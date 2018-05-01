package com.sainstest.app.exceptions;

public final class SainsAppExceptionCodes {

    private SainsAppExceptionCodes() {}

    public static final ExceptionCode NO_URL_PARAM = new ExceptionCode("001", "No url parameter was passed to the application");
    public static final ExceptionCode PARSING_FAILURE = new ExceptionCode("002", "The html parser has failed to initialize or is null");
}
