package com.example.backend.repositories;

import com.example.backend.models.Advertisement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
