package com.example.msretire.services;

import com.example.msretire.models.dto.DebitAccountDTO;
import reactor.core.publisher.Mono;

public interface IDebitAccountDTOService {
    public Mono<DebitAccountDTO> findByAccountNumber(String typeofdebit, String accountNumber);
    public Mono<DebitAccountDTO> updateDebit(String typeofdebit, DebitAccountDTO account);
}
