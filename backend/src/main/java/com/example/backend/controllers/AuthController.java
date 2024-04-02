package com.example.backend.controllers;

import com.example.backend.dto.JwtTokenResponse;
import com.example.backend.dto.LoginByEmailDto;
import com.example.backend.dto.LoginByPhoneDto;
import com.example.backend.dto.RegisterDto;
import com.example.backend.exceptions.AuthException;
import com.example.backend.exceptions.RefreshException;
import com.example.backend.exceptions.UserException;
import com.example.backend.services.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService auth;
    private final Validator validator;

    @PostMapping("/loginEmail")
    ResponseEntity<JwtTokenResponse> signInByEmail(@RequestBody LoginByEmailDto loginByEmailDTO) throws AuthException {
        try {
            return ResponseEntity.ok(auth.loginByEmail(loginByEmailDTO));
        } catch (AuthException e) {
            throw e;
        }

    }

    @PostMapping("/loginPhone")
    ResponseEntity<JwtTokenResponse> signInByPhone(@RequestBody LoginByPhoneDto loginByPhoneDTO) throws AuthException {
        try {
            return ResponseEntity.ok(auth.loginByPhone(loginByPhoneDTO));
        } catch (AuthException e) {
            throw e;
        }
    }

    @PostMapping("/registerEmail")
    ResponseEntity<JwtTokenResponse> signUpByEmail(@RequestBody RegisterDto registerDto, BindingResult bindingResult) throws UserException {
        validator.validate(registerDto, bindingResult);
        try {
            return new ResponseEntity<>(auth.registerByEmail(registerDto), HttpStatus.CREATED);
        } catch (UserException e) {
            throw e;
        }

    }

    @PostMapping("/registerPhone")
    ResponseEntity<JwtTokenResponse> signUpByPhone(@RequestBody LoginByPhoneDto loginByPhoneDTO, BindingResult bindingResult) throws UserException {
        validator.validate(loginByPhoneDTO, bindingResult);
        try {
            return new ResponseEntity<>(auth.registerByPhone(loginByPhoneDTO), HttpStatus.CREATED);
        } catch (UserException e) {
            throw e;
        }
    }

    @PostMapping("/refresh/{id}")
    ResponseEntity<JwtTokenResponse> refreshById(@PathVariable Long id) throws RefreshException {
        try {
            return ResponseEntity.ok(auth.refreshTokensById(id));
        } catch (RefreshException e) {
            throw e;
        }

    }

}
