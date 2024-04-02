package com.example.backend.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_user")
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long phoneNumber;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private byte[] photo;
    private Long items;
//    private List<Advertisement> favourites;
//    private List<Advertisement> purchases;
//    private List<Review> reviews;
//    private List<Chat> chats;

}
