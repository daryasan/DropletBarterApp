package com.example.backend.controllers;

import com.example.backend.dto.AdvertisementEditDto;
import com.example.backend.dto.AdvertisementsDataDto;
import com.example.backend.exceptions.AdvertisementException;
import com.example.backend.models.Advertisement;
import com.example.backend.services.AdvertisementService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/advertisement")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping("/add")
    private ResponseEntity<Advertisement> addAdvertisement(@RequestBody AdvertisementsDataDto advertisementsDataDto) {

        return new ResponseEntity<>(advertisementService.addAdvertisement(advertisementsDataDto),
                HttpStatus.CREATED);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Advertisement> editAdvertisement(@PathVariable Long id, @RequestBody AdvertisementEditDto advertisementEditDto) throws AdvertisementException {
        try {
            Advertisement ads = advertisementService.editAdvertisements(id, advertisementEditDto);
            return ResponseEntity.ok(ads);
        } catch (AdvertisementException e) {
            throw e;
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<Advertisement> findAdvertisement(@PathVariable Long id) throws AdvertisementException {
        try {
            Advertisement ads = advertisementService.findAdvertisement(id);
            return ResponseEntity.ok(ads);
        } catch (AdvertisementException e) {
            throw e;
        }
    }

    @GetMapping("/all")
    private List<Advertisement> findAllAdvertisements() {
        return advertisementService.findAllAdvertisements();
    }

    @GetMapping("/category/{id}")
    private List<Advertisement> findAdvertisementsByCategory(@PathVariable int id) {
        return advertisementService.findAdvertisementsByCategory(id);
    }

    @GetMapping("/user/{id}")
    private List<Advertisement> findAdvertisementsOwnedByUser(@PathVariable Long id) {
        return advertisementService.findAdvertisementsOwnedByUser(id);
    }

}
