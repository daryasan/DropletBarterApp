package com.example.backend.repositories;

import com.example.backend.models.FavouritesList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouritesListRepository extends JpaRepository<FavouritesList, Long> {
}
