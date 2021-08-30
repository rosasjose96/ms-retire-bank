package com.example.msretire.handler;

import com.example.msretire.models.dto.DebitAccountDTO;
import com.example.msretire.models.dto.TransactionDTO;
import com.example.msretire.models.entities.Retire;
import com.example.msretire.services.IDebitAccountDTOService;
import com.example.msretire.services.IRetireService;
import com.example.msretire.services.ITransactionDTOService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


/**
 * The type Retire handler.
 */
@Component
@Slf4j(topic = "RETIRE_HANDLER")
public class RetireHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RetireHandler.class);

    @Autowired
    private IDebitAccountDTOService accountService;

    @Autowired
    private IRetireService service;

    @Autowired
    private ITransactionDTOService transactionService;

    /**
     * Find all mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), Retire.class);
    }

    /**
     * Find debit mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> findDebit(ServerRequest request) {
        String id = request.pathVariable("id");
        return service.findById(id).flatMap((c -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(c))
                .switchIfEmpty(ServerResponse.notFound().build()))
        );
    }

    /**
     * Create retire mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> createRetire(ServerRequest request){

        Mono<Retire> retireMono = request.bodyToMono(Retire.class);


        return retireMono.flatMap( retireRequest -> accountService.findByAccountNumber(retireRequest.getTypeOfAccount(),retireRequest.getAccountNumber())
                .filter(account -> account.getAmount()>=retireRequest.getAmount())
                .flatMap(account -> {
                    if(account.getMaxLimitMovementPerMonth()>=account.getMovementPerMonth()){
                        account.setMovementPerMonth(account.getMovementPerMonth()+1);
                        account.setAmount(account.getAmount()-retireRequest.getAmount());
                    }else if (account.getMaxLimitMovementPerMonth()<account.getMovementPerMonth()){
                        LOGGER.info("La commission es: " + account.getCommission());
                        account.setAmount(account.getAmount()-retireRequest.getAmount()-account.getCommission());
                    }
                    LOGGER.info("El id del débito es: " + account.getId());
                    return accountService.updateDebit(account.getTypeOfAccount(),account);
                })
                .flatMap(ope -> {
                    TransactionDTO transaction = new TransactionDTO();
                    transaction.setTypeOfAccount(ope.getTypeOfAccount());
                    transaction.setTypeoftransaction("RETIRE");
                    transaction.setCustomerIdentityNumber(ope.getCustomerIdentityNumber());
                    transaction.setTransactionAmount(retireRequest.getAmount());
                    transaction.setIdentityNumber(retireRequest.getAccountNumber());
                    return transactionService.saveTransaction(transaction);
                })
                .flatMap(trans ->  accountService.findByAccountNumber(trans.getTypeOfAccount(),trans.getIdentityNumber()))
                .flatMap(debit -> {
                    if(debit.getMaxLimitMovementPerMonth()<debit.getMovementPerMonth()){
                        TransactionDTO Commission = new TransactionDTO();
                        Commission.setTypeOfAccount(debit.getTypeOfAccount());
                        Commission.setTypeoftransaction("COMMISSION");
                        Commission.setCustomerIdentityNumber(debit.getCustomerIdentityNumber());
                        Commission.setTransactionAmount(debit.getCommission());
                        Commission.setIdentityNumber(retireRequest.getAccountNumber());
                        return transactionService.saveTransaction(Commission);
                    } else {
                        return Mono.just(DebitAccountDTO.builder().build());
                    }

                })
                .flatMap(retire ->  service.create(retireRequest)))
                .flatMap( c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Delete debit mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> deleteDebit(ServerRequest request){

        String id = request.pathVariable("id");

        Mono<Retire> retireMono = service.findById(id);

        return retireMono
                .doOnNext(c -> LOGGER.info("delete Paymencard: PaymentCardId={}", c.getId()))
                .flatMap(c -> service.delete(c).then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Update debit mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> updateDebit(ServerRequest request){
        Mono<Retire> retireMono = request.bodyToMono(Retire.class);
        String id = request.pathVariable("id");

        return service.findById(id).zipWith(retireMono, (db,req) -> {
            db.setAmount(req.getAmount());
            return db;
        }).flatMap( c -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.create(c),Retire.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
