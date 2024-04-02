package com.example.backend.services;

import com.example.backend.dto.AddAdvertisementRequest;
import com.example.backend.dto.AdvertisementEditDto;
import com.example.backend.dto.QueryDto;
import com.example.backend.exceptions.AdvertisementException;
import com.example.backend.exceptions.QueryException;
import com.example.backend.exceptions.UserException;
import com.example.backend.models.Advertisement;
import com.example.backend.models.PurchasesList;
import com.example.backend.models.Query;
import com.example.backend.models.User;
import com.example.backend.repositories.PurchasesListRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchasesListService {

    private final PurchasesListRepository purchasesListRepository;
    private final UserService userService;
    private final QueryService queryService;
    private final AdvertisementService advertisementService;

    @Transactional
    public void addAdvertisementsToPurchases(AddAdvertisementRequest request) throws UserException, AdvertisementException, QueryException {
        PurchasesList purchasesList = findPurchasesForUser(request.getUserId());
        Advertisement advertisement = advertisementService.findAdvertisement(request.getAdsId());
        Query query = queryService.findQuery(request.getQueryId());

        List<Advertisement> purchases = purchasesList.getAdvertisements();
        purchases.add(advertisement);

        QueryDto queryDto = new QueryDto();
        queryDto.setAdsId(query.getAdsId());
        queryDto.setUserId(query.getUserId());
        queryDto.setStatus(2);
        queryService.editQuery(query.getId(), queryDto);

        AdvertisementEditDto advertisementEditDto = new AdvertisementEditDto();
        advertisementEditDto.setPhoto(advertisement.getPhoto());
        advertisementEditDto.setCategory(advertisement.getCategory());
        advertisementEditDto.setDescription(advertisement.getDescription());
        advertisementEditDto.setName(advertisement.getName());
        advertisementEditDto.setStatusActive(false);
        advertisementService.editAdvertisements(advertisement.getId(), advertisementEditDto);

        userService.changeBalance(advertisement.getOwnerId(), 1);
        userService.changeBalance(request.getUserId(), -1);

        purchasesList.setAdvertisements(purchases);
        purchasesListRepository.save(purchasesList);
    }


    public PurchasesList findPurchasesForUser(Long userId) throws UserException {
        User user = userService.findUser(userId);
        return purchasesListRepository.findByUser(user);
    }

}
