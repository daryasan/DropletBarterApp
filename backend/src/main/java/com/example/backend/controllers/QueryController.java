package com.example.backend.controllers;

import com.example.backend.dto.QueryDto;
import com.example.backend.exceptions.AdvertisementException;
import com.example.backend.exceptions.QueryException;
import com.example.backend.models.Query;
import com.example.backend.services.QueryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/query")
@RequiredArgsConstructor
public class QueryController {

    private final QueryService queryService;

    @PostMapping("/add")
    private ResponseEntity<Query> addQuery(@RequestBody QueryDto queryDto) {

        return new ResponseEntity<>(queryService.addQuery(queryDto),
                HttpStatus.CREATED);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Query> editQuery(@PathVariable Long id, @RequestBody QueryDto queryDto) throws QueryException {
        try {
            Query query = queryService.editQuery(id, queryDto);
            return ResponseEntity.ok(query);
        } catch (QueryException e) {
            throw e;
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<Query> findAdvertisement(@PathVariable Long id) throws QueryException {
        try {
            Query query = queryService.findQuery(id);
            return ResponseEntity.ok(query);
        } catch (QueryException e) {
            throw e;
        }
    }

    @GetMapping("/user/{id}")
    private List<Query> findQueriesForUser(@PathVariable Long id) throws AdvertisementException {
        return queryService.findQueriesForUser(id);
    }

}
