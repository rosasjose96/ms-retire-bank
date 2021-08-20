package com.example.msretire.services.impl;

import com.example.msretire.models.dto.TransactionDTO;
import com.example.msretire.services.ITransactionDTOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * The type Transaction service.
 */
@Service
public class TransactionServiceImpl implements ITransactionDTOService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final WebClient.Builder client;

    /**
     * Instantiates a new Transaction service.
     *
     * @param client the client
     */
    @Autowired
    public TransactionServiceImpl(WebClient.Builder client) {
        this.client = client;
    }

    @Override
    public Mono<TransactionDTO> saveTransaction(TransactionDTO transaction) {
        LOGGER.info("initializing Transaction create");

        return client
                .baseUrl("http://TRANSACTION-SERVICE/api/transaction")
                .build()
                .post()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transaction)
                .retrieve()
                .bodyToMono(TransactionDTO.class);
    }

}
