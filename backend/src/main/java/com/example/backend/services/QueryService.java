package com.example.backend.services;

import com.example.backend.dto.QueryDto;
import com.example.backend.exceptions.QueryException;
import com.example.backend.models.Advertisement;
import com.example.backend.models.Query;
import com.example.backend.repositories.AdvertisementRepository;
import com.example.backend.repositories.QueryRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueryService {

    private final QueryRepository queryRepository;
    private final AdvertisementRepository advertisementRepository;

    public Query addQuery(QueryDto queryDto) {
        Query query = new Query();
        query.setAdsId(queryDto.getAdsId());
        query.setUserId(queryDto.getUserId());
        query.setStatus(queryDto.getStatus());
        queryRepository.save(query);
        return query;
    }

    public Query editQuery(Long id, QueryDto queryDto) throws QueryException {
        Optional<Query> query = queryRepository.findById(id);
        if (query.isPresent()) {
            Query queryPresent = query.get();
            queryPresent.setAdsId(queryDto.getAdsId());
            queryPresent.setUserId(queryDto.getUserId());
            queryPresent.setStatus(queryDto.getStatus());

            queryRepository.save(queryPresent);
            return queryPresent;
        } else {
            throw new QueryException("No such query!");
        }
    }

    public Query findQuery(Long id) throws QueryException {
        Optional<Query> query = queryRepository.findById(id);
        if (query.isPresent()) {
            return query.get();
        } else {
            throw new QueryException("No such query!");
        }
    }

    public List<Query> findQueriesForUser(Long id) {
        List<Query> queries = queryRepository.findAll();
        ArrayList<Query> queriesForUser = new ArrayList<>();
        for (Query q : queries) {
            Optional<Advertisement> ad = advertisementRepository.findById(q.getAdsId());
            if (ad.isPresent()) {
                if (Objects.equals(ad.get().getOwnerId(), id)) {
                    queriesForUser.add(q);
                }
            }
        }
        return queriesForUser;
    }

}
