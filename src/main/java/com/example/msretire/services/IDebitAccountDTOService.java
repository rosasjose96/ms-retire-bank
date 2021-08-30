package com.example.msretire.services;

import com.example.msretire.models.dto.DebitAccountDTO;
import reactor.core.publisher.Mono;

/**
 * The interface Debit account dto service.
 */
public interface IDebitAccountDTOService {
    /**
     * Find by account number mono.
     *
     * @param typeofdebit   the typeofdebit
     * @param accountNumber the account number
     * @return the mono
     */
    Mono<DebitAccountDTO> findByAccountNumber(String typeofdebit, String accountNumber);

    /**
     * Update debit mono.
     *
     * @param typeofdebit the typeofdebit
     * @param account     the account
     * @return the mono
     */
    Mono<DebitAccountDTO> updateDebit(String typeofdebit, DebitAccountDTO account);
}
