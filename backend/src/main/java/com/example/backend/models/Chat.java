package com.example.backend.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.util.Pair;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Data
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Pair<User, User> owners;
    private List<ChatMessage> messages;
    private Advertisement advertisement;
}
