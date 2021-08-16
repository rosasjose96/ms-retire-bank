package com.example.msretire.repository;

import com.example.msretire.models.entities.Retire;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RetireRepository extends ReactiveMongoRepository<Retire, String> {
}
