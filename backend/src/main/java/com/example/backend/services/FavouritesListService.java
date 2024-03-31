package com.example.backend.services;

import com.example.backend.dto.AddAdvertisementRequest;
import com.example.backend.exceptions.AdvertisementException;
import com.example.backend.exceptions.UserException;
import com.example.backend.models.Advertisement;
import com.example.backend.models.FavouritesList;
import com.example.backend.models.User;
import com.example.backend.repositories.FavouritesListRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavouritesListService {

    private final FavouritesListRepository favouritesListRepository;
    private final UserService userService;
    private final AdvertisementService advertisementService;

    @Transactional
    public void addAdvertisementsToFavourites(AddAdvertisementRequest request) throws UserException, AdvertisementException {
        FavouritesList favouritesList = findFavouritesForUser(request.getUserId());
        Advertisement advertisement = advertisementService.findAdvertisement(request.getAdsId());
        List<Advertisement> advertisements = favouritesList.getAdvertisements();
        advertisements.add(advertisement);
        favouritesList.setAdvertisements(advertisements);
        favouritesListRepository.save(favouritesList);
    }

    @Transactional
    public void removeAdvertisementsFromFavourites(AddAdvertisementRequest request) throws UserException, AdvertisementException {
        FavouritesList favouritesList = findFavouritesForUser(request.getUserId());
        List<Advertisement> advertisements = favouritesList.getAdvertisements();
        advertisements.removeIf(ad -> Objects.equals(ad.getId(), request.getAdsId()));
        favouritesList.setAdvertisements(advertisements);
        favouritesListRepository.save(favouritesList);
    }

    public FavouritesList findFavouritesForUser(Long userId) throws UserException {
        User user = userService.findUser(userId);
        return favouritesListRepository.findByUser(user);
    }

}
