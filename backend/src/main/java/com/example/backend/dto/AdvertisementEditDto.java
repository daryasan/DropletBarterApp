package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementEditDto {
    private byte[] photo;
    private String name;
    private String description;
    private Boolean statusActive;
    private int category;
}