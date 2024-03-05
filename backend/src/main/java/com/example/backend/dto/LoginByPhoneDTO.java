package com.example.backend.dto;

import lombok.Data;

@Data
public class LoginByPhoneDTO {

    private Long phone;
    private String password;

}
