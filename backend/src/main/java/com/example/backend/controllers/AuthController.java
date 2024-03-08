package com.example.backend.controllers;

import com.example.backend.dto.JwtTokenResponse;
import com.example.backend.dto.LoginByEmailDTO;
import com.example.backend.dto.LoginByPhoneDTO;
import com.example.backend.exceptions.AuthException;
import com.example.backend.exceptions.UserException;
import com.example.backend.services.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
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
    ResponseEntity<JwtTokenResponse> signInByEmail(@RequestBody LoginByEmailDTO loginByEmailDTO) throws AuthException {
        try {
            return ResponseEntity.ok(auth.loginByEmail(loginByEmailDTO));
        } catch (AuthException e) {
            throw e;
        }

    }

    @PostMapping("/loginPhone")
    ResponseEntity<JwtTokenResponse> signInByPhone(@RequestBody LoginByPhoneDTO loginByPhoneDTO) throws AuthException {
        try {
            return ResponseEntity.ok(auth.loginByPhone(loginByPhoneDTO));
        } catch (AuthException e) {
            throw e;
        }
    }

    @PostMapping("/registerEmail")
    ResponseEntity<JwtTokenResponse> signUpByEmail(@RequestBody LoginByEmailDTO loginByEmailDTO, BindingResult bindingResult) throws UserException {
        validator.validate(loginByEmailDTO, bindingResult);
        try {
            return new ResponseEntity<>(auth.registerByEmail(loginByEmailDTO), HttpStatus.CREATED);
        } catch (UserException e) {
            throw e;
        }

    }

    @PostMapping("/registerPhone")
    ResponseEntity<JwtTokenResponse> signUpByPhone(@RequestBody LoginByPhoneDTO loginByPhoneDTO, BindingResult bindingResult) {
        validator.validate(loginByPhoneDTO, bindingResult);
        return new ResponseEntity<>(auth.registerByPhone(loginByPhoneDTO), HttpStatus.CREATED);
    }

}
