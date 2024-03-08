package com.example.backend.security;

/**
 * @author Vlad Utts
 */
public class JwtException extends RuntimeException{
    public JwtException(String message) {
        super(message);
    }
}
