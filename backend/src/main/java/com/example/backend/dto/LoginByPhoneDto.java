package com.example.backend.dto;

import lombok.Data;

@Data
public class LoginByPhoneDto {

    private Long phone;
    private String password;

}
