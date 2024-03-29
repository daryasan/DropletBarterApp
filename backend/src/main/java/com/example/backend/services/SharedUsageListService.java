package com.example.backend.services;

import com.example.backend.dto.AddAdvertisementRequest;
import com.example.backend.exceptions.AdvertisementException;
import com.example.backend.exceptions.UserException;
import com.example.backend.models.Advertisement;
import com.example.backend.models.SharedUsageList;
import com.example.backend.models.User;
import com.example.backend.repositories.SharedUsageListRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SharedUsageListService {

    private final SharedUsageListRepository sharedUsageListRepository;
    private final UserService userService;
    private final AdvertisementService advertisementService;

    @Transactional
    public void addAdvertisementsToSharedUsage(AddAdvertisementRequest request) throws UserException, AdvertisementException {
        SharedUsageList sharedUsageList = findSharedUsageForUser(request.getUserId());
        Advertisement advertisement = advertisementService.findAdvertisement(request.getAdsId());
        List<Advertisement> advertisements = sharedUsageList.getAdvertisements();
        advertisements.add(advertisement);
        sharedUsageList.setAdvertisements(advertisements);
        sharedUsageListRepository.save(sharedUsageList);
    }

    @Transactional
    public void removeAdvertisementsFromSharedUsage(AddAdvertisementRequest request) throws UserException, AdvertisementException {
        SharedUsageList sharedUsageForUser = findSharedUsageForUser(request.getUserId());
        List<Advertisement> advertisements = sharedUsageForUser.getAdvertisements();
        for (Advertisement ad : advertisements) {
            if (Objects.equals(ad.getId(), request.getAdsId())) {
                advertisements.remove(ad);
            }
        }
        sharedUsageForUser.setAdvertisements(advertisements);
        sharedUsageListRepository.save(sharedUsageForUser);
    }

    public SharedUsageList findSharedUsageForUser(Long userId) throws UserException {
        User user = userService.findUser(userId);
        return sharedUsageListRepository.findByUser(user);
    }
}
