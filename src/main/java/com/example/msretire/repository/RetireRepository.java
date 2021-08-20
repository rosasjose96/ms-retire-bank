package com.example.msretire.repository;

import com.example.msretire.models.entities.Retire;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * The interface Retire repository.
 */
public interface RetireRepository extends ReactiveMongoRepository<Retire, String> {
}
