package com.example.msretire.services.impl;

import com.example.msretire.models.entities.Retire;
import com.example.msretire.repository.RetireRepository;
import com.example.msretire.services.IRetireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The type Retire service.
 */
@Service
public class RetireServiceImpl implements IRetireService {

    @Autowired
    private RetireRepository repository;

    @Override
    public Mono<Retire> create(Retire o) {
        return repository.save(o);
    }

    @Override
    public Flux<Retire> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Retire> findById(String s) {
        return repository.findById(s);
    }

    @Override
    public Mono<Retire> update(Retire o) {
        return repository.save(o);
    }

    @Override
    public Mono<Void> delete(Retire o) {
        return repository.delete(o);
    }
}
