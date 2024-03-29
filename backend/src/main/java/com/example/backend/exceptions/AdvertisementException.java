package com.example.backend.exceptions;

public class AdvertisementException extends Exception {
    public AdvertisementException() {
        super();
    }

    public AdvertisementException(String message) {
        super(message);
    }

    public AdvertisementException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdvertisementException(Throwable cause) {
        super(cause);
    }
}
