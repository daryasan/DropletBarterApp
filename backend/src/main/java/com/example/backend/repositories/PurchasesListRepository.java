package com.example.backend.repositories;

import com.example.backend.models.PurchasesList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasesListRepository extends JpaRepository<PurchasesList, Long> {
}
