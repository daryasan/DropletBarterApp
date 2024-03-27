package com.example.backend.exceptions;

public class RefreshException extends Exception {
    public RefreshException() {
        super();
    }

    public RefreshException(String message) {
        super(message);
    }

    public RefreshException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshException(Throwable cause) {
        super(cause);
    }
}

