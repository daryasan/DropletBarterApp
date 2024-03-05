package com.example.backend.services;

import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import com.example.backend.exceptions.UserException;

import org.springframework.stereotype.Service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository ur;

    public User findUser(Long id) throws UserException {
        Optional<User> optionalUser = ur.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserException("No such user!");
        }
    }

//    public User editUser(Long id, UserEditDTO userEditDTO) throws UserException {
//        Optional<User> optionalUser = ur.findById(id);
//        User user;
//        if (optionalUser.isPresent()) {
//            user = optionalUser.get();
//        } else {
//            throw new UserException("No such user!");
//        }
//
//        user.setFirstName(userEditDTO.getFirstName());
//        user.setLastName(userEditDTO.getLastName());
//
//        return ur.save(user);
//    }

}
