package com.example.backend.repositories;

import com.example.backend.models.SharedUsageList;
import com.example.backend.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedUsageListRepository extends JpaRepository<SharedUsageList, Long> {

    SharedUsageList findByUser(User user);
}
