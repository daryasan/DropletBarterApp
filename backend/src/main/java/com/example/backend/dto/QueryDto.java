package com.example.backend.dto;

import lombok.Data;

@Data
public class QueryDto {
    Long userId;
    Long adsId;
    int status;
}
