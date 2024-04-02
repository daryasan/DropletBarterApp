package com.example.backend.services;

import com.example.backend.dto.JwtTokenResponse;
import com.example.backend.dto.LoginByEmailDto;
import com.example.backend.dto.LoginByPhoneDto;
import com.example.backend.dto.RegisterDto;
import com.example.backend.exceptions.AuthException;
import com.example.backend.exceptions.RefreshException;
import com.example.backend.exceptions.UserException;
import com.example.backend.models.FavouritesList;
import com.example.backend.models.PurchasesList;
import com.example.backend.models.SharedUsageList;
import com.example.backend.models.User;
import com.example.backend.repositories.FavouritesListRepository;
import com.example.backend.repositories.PurchasesListRepository;
import com.example.backend.repositories.SharedUsageListRepository;
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
    private final SharedUsageListRepository sharedUsageListRepository;
    private final PurchasesListRepository purchasesListRepository;
    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


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

    public JwtTokenResponse registerByEmail(RegisterDto registerDto) throws UserException {
        User user = new User();
        if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            throw new UserException("User already exists!");
        }
        user.setEmail(registerDto.getEmail());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPhoneNumber(registerDto.getPhone());
        user.setItems(5L);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        FavouritesList favouritesList = new FavouritesList(user, new ArrayList<>());
        PurchasesList purchasesList = new PurchasesList(user, new ArrayList<>());
        SharedUsageList sharedUsageList = new SharedUsageList(user, new ArrayList<>());

        userRepository.save(user);
        favouritesListRepository.save(favouritesList);
        sharedUsageListRepository.save(sharedUsageList);
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

        PurchasesList purchasesList = new PurchasesList(user, new ArrayList<>());
        purchasesListRepository.save(purchasesList);

        return jwtUtil.generateJWTResponse(user);
    }

    public JwtTokenResponse refreshTokensById(Long id) throws RefreshException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return jwtUtil.generateJWTResponse(user.get());
        } else {
            throw new RefreshException("Wrong user data!");
        }

    }

}
