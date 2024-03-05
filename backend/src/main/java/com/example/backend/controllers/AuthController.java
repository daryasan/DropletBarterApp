package com.example.backend.controllers;

import com.example.backend.dto.LoginByEmailDTO;
import com.example.backend.dto.LoginByPhoneDTO;
import com.example.backend.exceptions.AuthException;
import com.example.backend.models.User;
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

    @PostMapping("/login")
    ResponseEntity<User> signInByEmail(@RequestBody LoginByEmailDTO loginByEmailDTO) throws AuthException {
        return ResponseEntity.ok(auth.loginByEmail(loginByEmailDTO));
    }

    @PostMapping("/login")
    ResponseEntity<User> signInByPhone(@RequestBody LoginByPhoneDTO loginByPhoneDTO) throws AuthException {
        return ResponseEntity.ok(auth.loginByPhone(loginByPhoneDTO));
    }

    @PostMapping("/register")
    ResponseEntity<User> signUpByEmail(@RequestBody LoginByEmailDTO loginByEmailDTO, BindingResult bindingResult) {
        validator.validate(loginByEmailDTO, bindingResult);
        return new ResponseEntity<>(auth.registerByEmail(loginByEmailDTO), HttpStatus.CREATED);
    }
}
