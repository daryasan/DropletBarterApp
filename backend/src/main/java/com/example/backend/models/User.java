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
    private double rating;
    private Long items;
    //@OneToMany(targetEntity=Advertisement.class, mappedBy="user", fetch= FetchType.EAGER)
    //private List<Advertisement> favourites;
//    @OneToMany(targetEntity=Advertisement.class, mappedBy="user", fetch= FetchType.EAGER)
//    private List<Advertisement> purchases;
//    @OneToMany(targetEntity=Review.class, mappedBy="user", fetch= FetchType.EAGER)
//    private List<Review> reviews;
    //private List<Chat> chats;

}
