package com.example.msretire.services;

import com.example.msretire.models.dto.TransactionDTO;
import reactor.core.publisher.Mono;

public interface ITransactionDTOService {
    public Mono<TransactionDTO> saveTransaction(TransactionDTO transaction);
}
