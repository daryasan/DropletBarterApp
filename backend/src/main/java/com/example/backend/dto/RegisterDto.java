package com.example.backend.dto;

import lombok.Data;

@Data
public class RegisterDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Long phone;

}
