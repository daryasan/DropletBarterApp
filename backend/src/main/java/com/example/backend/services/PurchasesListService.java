package com.example.backend.services;

import com.example.backend.dto.AddAdvertisementRequest;
import com.example.backend.exceptions.AdvertisementException;
import com.example.backend.exceptions.UserException;
import com.example.backend.models.Advertisement;
import com.example.backend.models.PurchasesList;
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
    private final AdvertisementService advertisementService;

    @Transactional
    public void addAdvertisementsToPurchases(AddAdvertisementRequest request) throws UserException, AdvertisementException {
        PurchasesList purchasesList = findPurchasesForUser(request.getUserId());
        Advertisement advertisement = advertisementService.findAdvertisement(request.getAdsId());
        List<Advertisement> advertisements = purchasesList.getAdvertisements();
        advertisements.add(advertisement);
        purchasesList.setAdvertisements(advertisements);
        purchasesListRepository.save(purchasesList);
    }

//    @Transactional
//    public void removeAdvertisementsFromPurchases(AddAdvertisementRequest request) throws UserException, AdvertisementException {
//        PurchasesList purchasesList = findPurchasesForUser(request.getUserId());
//        List<Advertisement> advertisements = purchasesList.getAdvertisements();
//        for (Advertisement ad : advertisements) {
//            if (Objects.equals(ad.getId(), request.getAdsId())) {
//                advertisements.remove(ad);
//            }
//        }
//        purchasesList.setAdvertisements(advertisements);
//        purchasesListRepository.save(purchasesList);
//    }

    public PurchasesList findPurchasesForUser(Long userId) throws UserException {
        User user = userService.findUser(userId);
        return purchasesListRepository.findByUser(user);
    }

}
