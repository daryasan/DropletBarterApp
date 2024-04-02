package com.example.backend.services;

import com.example.backend.dto.AddAdvertisementRequest;
import com.example.backend.dto.QueryDto;
import com.example.backend.exceptions.AdvertisementException;
import com.example.backend.exceptions.QueryException;
import com.example.backend.exceptions.UserException;
import com.example.backend.models.Advertisement;
import com.example.backend.models.Query;
import com.example.backend.models.SharedUsageList;
import com.example.backend.models.User;
import com.example.backend.repositories.SharedUsageListRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SharedUsageListService {

    private final SharedUsageListRepository sharedUsageListRepository;
    private final UserService userService;
    private final AdvertisementService advertisementService;
    private final QueryService queryService;

    @Transactional
    public void addAdvertisementsToSharedUsage(AddAdvertisementRequest request) throws UserException, AdvertisementException, QueryException {
        SharedUsageList sharedUsageList = findSharedUsageForUser(request.getUserId());
        Advertisement advertisement = advertisementService.findAdvertisement(request.getAdsId());
        List<Advertisement> advertisements = sharedUsageList.getAdvertisements();

        Query query = queryService.findQuery(request.getQueryId());
        List<Advertisement> purchases;
        if (sharedUsageList != null) {
            purchases = sharedUsageList.getAdvertisements();
        } else {
            purchases = Collections.emptyList();
        }

        purchases.add(advertisement);
        QueryDto queryDto = new QueryDto();
        queryDto.setAdsId(query.getAdsId());
        queryDto.setUserId(query.getUserId());
        queryDto.setStatus(2);
        queryService.editQuery(query.getId(), queryDto);

        userService.changeBalance(advertisement.getOwnerId(), 1);
        userService.changeBalance(request.getUserId(), -1);

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
