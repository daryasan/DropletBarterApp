package com.example.backend.services;

import com.example.backend.dto.AdvertisementsDataDto;
import com.example.backend.exceptions.AdvertisementException;
import com.example.backend.models.Advertisement;
import com.example.backend.repositories.AdvertisementRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    public Advertisement addAdvertisement(AdvertisementsDataDto dto) {
        Advertisement ads = new Advertisement();
        ads.setPhoto(dto.getPhoto());
        ads.setName(dto.getName());
        ads.setDescription(dto.getDescription());
        ads.setStatusActive(dto.getStatusActive());
        ads.setCategory(dto.getCategory());
        ads.setOwnerId(dto.getOwnerId());

        advertisementRepository.save(ads);
        return ads;

    }

    public Advertisement findAdvertisement(Long id) throws AdvertisementException {
        Optional<Advertisement> ads = advertisementRepository.findById(id);
        if (ads.isPresent()) {
            return ads.get();
        } else {
            throw new AdvertisementException("No such user!");
        }
    }


    public List<Advertisement> findAllAdvertisements() {
        return advertisementRepository.findAll();
    }


    public List<Advertisement> findAdvertisementsByCategory(int category) {
        List<Advertisement> ads = advertisementRepository.findAll();
        ArrayList<Advertisement> adsByCategory = new ArrayList<>();
        for (Advertisement ad : ads) {
            if (ad.getCategory() == category) {
                adsByCategory.add(ad);
            }
        }
        return adsByCategory;
    }

    public List<Advertisement> findAdvertisementsOwnedByUser(Long ownerId) {
        List<Advertisement> ads = advertisementRepository.findAll();
        ArrayList<Advertisement> adsByOwner = new ArrayList<>();
        for (Advertisement ad :
                ads) {
            if (Objects.equals(ad.getOwnerId(), ownerId)) {
                adsByOwner.add(ad);
            }
        }
        return adsByOwner;
    }

}
