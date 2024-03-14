package com.example.backend.repositories;

import com.example.backend.models.ReviewsList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsListRepository extends JpaRepository<ReviewsList, Long> {
}
