package com.example.backend.services;

import com.example.backend.dto.UserChangeLoginsEmailDto;
import com.example.backend.dto.UserChangeLoginsPasswordDto;
import com.example.backend.dto.UserChangeLoginsPhoneDto;
import com.example.backend.dto.UserEditDto;
import com.example.backend.exceptions.UserException;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findUser(Long id) throws UserException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserException("No such user!");
        }
    }

    public User editUser(Long id, UserEditDto userEditDTO) throws UserException {
        Optional<User> optionalUser = userRepository.findById(id);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new UserException("No such user!");
        }

        user.setFirstName(userEditDTO.getFirstName());
        user.setLastName(userEditDTO.getLastName());
        user.setAddress(userEditDTO.getAddress());
        user.setPhoto(userEditDTO.getPhoto());

        return userRepository.save(user);
    }

    public void changeBalance(Long id, int change) throws UserException {
        Optional<User> optionalUser = userRepository.findById(id);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new UserException("No such user!");
        }

        user.setItems(user.getItems() + change);

        if (user.getItems() >= 0){
            userRepository.save(user);
        } else {
            throw new UserException("Not enough items!");
        }

    }

    public User editEmail(Long id, UserChangeLoginsEmailDto email) throws UserException {
        Optional<User> optionalUser = userRepository.findById(id);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new UserException("No such user!");
        }

        user.setEmail(email.getEmail());

        return userRepository.save(user);
    }

    public User editPhone(Long id, UserChangeLoginsPhoneDto phone) throws UserException {
        Optional<User> optionalUser = userRepository.findById(id);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new UserException("No such user!");
        }

        user.setPhoneNumber(phone.getPhone());
        return userRepository.save(user);
    }

    public User changePassword(Long id, UserChangeLoginsPasswordDto password) throws UserException {
        Optional<User> optionalUser = userRepository.findById(id);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new UserException("No such user!");
        }

        user.setPassword(passwordEncoder.encode(password.getPassword()));

        return userRepository.save(user);
    }

}
