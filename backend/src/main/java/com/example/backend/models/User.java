package com.example.backend.models;


import org.springframework.data.annotation.Id;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private byte[] photo;
    private double rating;
    private Long items;
    private List<Advertisement> favourites;
    private List<Advertisement> purchases;
    private List<Review> reviews;
    private List<Chat> chats;

}
