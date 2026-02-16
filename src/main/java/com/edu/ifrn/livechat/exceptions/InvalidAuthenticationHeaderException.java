package com.edu.ifrn.livechat.exceptions;

public class InvalidAuthenticationHeaderException extends RuntimeException {
    public InvalidAuthenticationHeaderException(String message) {
        super(message);
    }
}
