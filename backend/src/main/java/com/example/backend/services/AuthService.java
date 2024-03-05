package com.example.backend.services;

import com.example.backend.dto.LoginByEmailDTO;
import com.example.backend.dto.LoginByPhoneDTO;
import com.example.backend.exceptions.AuthException;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;

    }

    public User loginByEmail(LoginByEmailDTO loginByEmailDTO) throws AuthException {
        User user;
        if (userRepository.findByEmail(loginByEmailDTO.getEmail()).isPresent()) {
            user = userRepository.findByEmail(loginByEmailDTO.getEmail()).get();
        } else {
            throw new AuthException("Wrong login!");
        }

        if (user.getPassword().equals(passwordEncoder.encode(loginByEmailDTO.getPassword()))) {
            return user;
        }
        throw new AuthException("Wrong password!");
    }

    public User loginByPhone(LoginByPhoneDTO loginByPhoneDTO) throws AuthException {
        User user;
        if (userRepository.findByPhoneNumber(loginByPhoneDTO.getPhone()).isPresent()) {
            user = userRepository.findByPhoneNumber(loginByPhoneDTO.getPhone()).get();
        } else {
            throw new AuthException("Wrong login!");
        }

        if (user.getPassword().equals(passwordEncoder.encode(loginByPhoneDTO.getPassword()))) {
            return user;
        }
        throw new AuthException("Wrong password!");
    }

    public User registerByEmail(LoginByEmailDTO loginByEmailDTO) {
        User user = new User();
        user.setEmail(loginByEmailDTO.getEmail());
        user.setPassword(passwordEncoder.encode(loginByEmailDTO.getPassword()));
        userRepository.save(user);
        return user;
    }

}
