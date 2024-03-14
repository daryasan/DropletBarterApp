package com.example.backend.services;

import com.example.backend.dto.JwtTokenResponse;
import com.example.backend.dto.LoginByEmailDto;
import com.example.backend.dto.LoginByPhoneDto;
import com.example.backend.exceptions.AuthException;
import com.example.backend.exceptions.UserException;
import com.example.backend.models.FavouritesList;
import com.example.backend.models.PurchasesList;
import com.example.backend.models.ReviewsList;
import com.example.backend.models.User;
import com.example.backend.repositories.FavouritesListRepository;
import com.example.backend.repositories.PurchasesListRepository;
import com.example.backend.repositories.ReviewsListRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.security.JwtUtil;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final FavouritesListRepository favouritesListRepository;
    private final ReviewsListRepository reviewsListRepository;
    private final PurchasesListRepository purchasesListRepository;
    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    //private final SecurityConfig securityConfig;


    public JwtTokenResponse loginByEmail(LoginByEmailDto loginByEmailDTO) throws AuthException {
        Optional<User> user = userRepository.findByEmail(loginByEmailDTO.getEmail());
        if (user.isPresent()) {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(loginByEmailDTO.getEmail(), loginByEmailDTO.getPassword());
            authenticationManager.authenticate(auth);
            return jwtUtil.generateJWTResponse(user.get());
        } else {
            throw new AuthException("Wrong user data!");
        }

    }

    public JwtTokenResponse loginByPhone(LoginByPhoneDto loginByPhoneDTO) throws AuthException {
        Optional<User> user = userRepository.findByPhoneNumber(loginByPhoneDTO.getPhone());
        if (user.isPresent()) {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(loginByPhoneDTO.getPhone(), loginByPhoneDTO.getPassword());
            authenticationManager.authenticate(auth);
            return jwtUtil.generateJWTResponse(user.get());
        } else {
            throw new AuthException("Wrong user data!");
        }

    }

    public JwtTokenResponse registerByEmail(LoginByEmailDto loginByEmailDTO) throws UserException {
        User user = new User();
        if (userRepository.findByEmail(loginByEmailDTO.getEmail()).isPresent()) {
            throw new UserException("User already exists!");
        }
        user.setEmail(loginByEmailDTO.getEmail());
        user.setPassword(passwordEncoder.encode(loginByEmailDTO.getPassword()));
        userRepository.save(user);

        // init lists
        FavouritesList favouritesList = new FavouritesList(user, new ArrayList<>());
        favouritesListRepository.save(favouritesList);

        ReviewsList reviewsList = new ReviewsList(user, new ArrayList<>());
        reviewsListRepository.save(reviewsList);

        PurchasesList purchasesList = new PurchasesList(user, new ArrayList<>());
        purchasesListRepository.save(purchasesList);

        return jwtUtil.generateJWTResponse(user);
    }


    public JwtTokenResponse registerByPhone(LoginByPhoneDto loginByPhoneDTO) throws UserException {
        User user = new User();
        if (userRepository.findByPhoneNumber(loginByPhoneDTO.getPhone()).isPresent()) {
            throw new UserException("User already exists!");
        }
        user.setPhoneNumber(loginByPhoneDTO.getPhone());
        user.setPassword(passwordEncoder.encode(loginByPhoneDTO.getPassword()));
        userRepository.save(user);

        // init lists
        FavouritesList favouritesList = new FavouritesList(user, new ArrayList<>());
        favouritesListRepository.save(favouritesList);

        ReviewsList reviewsList = new ReviewsList(user, new ArrayList<>());
        reviewsListRepository.save(reviewsList);

        PurchasesList purchasesList = new PurchasesList(user, new ArrayList<>());
        purchasesListRepository.save(purchasesList);

        return jwtUtil.generateJWTResponse(user);
    }

}
