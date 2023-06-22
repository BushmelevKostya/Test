package com.gui.test.common.exception;

public class NoAuthorizedException extends Exception{
    public NoAuthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
