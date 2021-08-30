package com.example.msretire.services;

import com.example.msretire.models.dto.TransactionDTO;
import reactor.core.publisher.Mono;

/**
 * The interface Transaction dto service.
 */
public interface ITransactionDTOService {
    /**
     * Save transaction mono.
     *
     * @param transaction the transaction
     * @return the mono
     */
    Mono<TransactionDTO> saveTransaction(TransactionDTO transaction);
}
