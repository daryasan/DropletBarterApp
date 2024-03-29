package com.example.backend.controllers;

import com.example.backend.dto.AddAdvertisementRequest;
import com.example.backend.exceptions.AdvertisementException;
import com.example.backend.exceptions.UserException;
import com.example.backend.models.Advertisement;
import com.example.backend.services.SharedUsageListService;

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
@RequestMapping("/shared")
@RequiredArgsConstructor
public class SharedUsageListController {

    private final SharedUsageListService sharedUsageListService;

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addToFavourites(@RequestBody AddAdvertisementRequest request) throws AdvertisementException, UserException {
        sharedUsageListService.addAdvertisementsToSharedUsage(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/delete")
    public ResponseEntity<HttpStatus> removeFromFavourites(@RequestBody AddAdvertisementRequest request) throws AdvertisementException, UserException {
        sharedUsageListService.removeAdvertisementsFromSharedUsage(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public List<Advertisement> findForUser(@PathVariable Long id) throws UserException {
        return sharedUsageListService.findSharedUsageForUser(id).getAdvertisements();
    }

}
