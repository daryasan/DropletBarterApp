package com.example.backend.services;

import com.example.backend.dto.UserEditDto;
import com.example.backend.exceptions.UserException;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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

        return userRepository.save(user);
    }

}
