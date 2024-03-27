package com.example.backend.controllers;


import com.example.backend.dto.UserChangeLoginsEmailDto;
import com.example.backend.dto.UserChangeLoginsPasswordDto;
import com.example.backend.dto.UserChangeLoginsPhoneDto;
import com.example.backend.dto.UserEditDto;
import com.example.backend.exceptions.UserException;
import com.example.backend.models.User;
import com.example.backend.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> findUser(@PathVariable Long id) throws UserException {
        try {
            User user = userService.findUser(id);
            return ResponseEntity.ok(user);
        } catch (UserException e) {
            throw e;
        }
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody UserEditDto userEditDTO) throws UserException {
        try {
            User user = userService.editUser(id, userEditDTO);
            return ResponseEntity.ok(user);
        } catch (UserException e) {
            throw e;
        }
    }

    @PatchMapping("/changeEmail/{id}")
    public ResponseEntity<User> editUserEmail(@PathVariable Long id, @RequestBody UserChangeLoginsEmailDto email) throws UserException {
        try {
            User user = userService.editEmail(id, email);
            return ResponseEntity.ok(user);
        } catch (UserException e) {
            throw e;
        }
    }


    @PatchMapping("/changePhone/{id}")
    public ResponseEntity<User> editUserPhone(@PathVariable Long id, @RequestBody UserChangeLoginsPhoneDto phone) throws UserException {
        try {
            User user = userService.editPhone(id, phone);
            return ResponseEntity.ok(user);
        } catch (UserException e) {
            throw e;
        }
    }


    @PatchMapping("/changePassword/{id}")
    public ResponseEntity<User> editUserPassword(@PathVariable Long id, @RequestBody UserChangeLoginsPasswordDto password) throws UserException {
        try {
            User user = userService.changePassword(id, password);
            return ResponseEntity.ok(user);
        } catch (UserException e) {
            throw e;
        }
    }


}

