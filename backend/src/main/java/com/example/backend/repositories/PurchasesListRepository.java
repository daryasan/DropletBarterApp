package com.example.backend.repositories;

import com.example.backend.models.PurchasesList;
import com.example.backend.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasesListRepository extends JpaRepository<PurchasesList, Long> {

    PurchasesList findByUser(User user);
}
