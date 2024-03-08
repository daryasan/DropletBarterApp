package com.example.backend.services;

import com.example.backend.dto.JwtTokenResponse;
import com.example.backend.dto.LoginByEmailDTO;
import com.example.backend.dto.LoginByPhoneDTO;
import com.example.backend.exceptions.AuthException;
import com.example.backend.exceptions.UserException;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import com.example.backend.security.JwtUtil;
import com.example.backend.security.SecurityConfig;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final SecurityConfig securityConfig;


    public JwtTokenResponse loginByEmail(LoginByEmailDTO loginByEmailDTO) throws AuthException {
        Optional<User> user = userRepository.findByEmail(loginByEmailDTO.getEmail());
        if (user.isPresent()) {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(loginByEmailDTO.getEmail(), loginByEmailDTO.getPassword());
            authenticationManager.authenticate(auth);
            return jwtUtil.generateJWTResponseByPhone(user.get());
        } else {
            throw new AuthException("Wrong user data!");
        }

    }

    public JwtTokenResponse loginByPhone(LoginByPhoneDTO loginByPhoneDTO) throws AuthException {
        Optional<User> user = userRepository.findByPhoneNumber(loginByPhoneDTO.getPhone());
        if (user.isPresent()) {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(loginByPhoneDTO.getPhone(), loginByPhoneDTO.getPassword());
            authenticationManager.authenticate(auth);
            return jwtUtil.generateJWTResponseByPhone(user.get());
        } else {
            throw new AuthException("Wrong user data!");
        }

    }

    public JwtTokenResponse registerByEmail(LoginByEmailDTO loginByEmailDTO) throws UserException {
        User user = new User();
        if (userRepository.findByEmail(loginByEmailDTO.getEmail()).isPresent()) {
            throw new UserException("User already exists!");
        }
        user.setEmail(loginByEmailDTO.getEmail());
        user.setPassword(passwordEncoder.encode(loginByEmailDTO.getPassword()));
        userRepository.save(user);
        return jwtUtil.generateJWTResponseByEmail(user);
    }


    public JwtTokenResponse registerByPhone(LoginByPhoneDTO loginByPhoneDTO) {
        User user = new User();
        user.setPhoneNumber(loginByPhoneDTO.getPhone());
        user.setPassword(passwordEncoder.encode(loginByPhoneDTO.getPassword()));
        userRepository.save(user);
        return jwtUtil.generateJWTResponseByPhone(user);
    }

}
