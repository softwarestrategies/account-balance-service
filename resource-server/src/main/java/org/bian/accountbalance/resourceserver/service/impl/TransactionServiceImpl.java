package org.bian.accountbalance.resourceserver.service.impl;

import org.bian.accountbalance.common.data.api.CreateTransactionRequest;
import org.bian.accountbalance.common.data.api.TransactionResponse;
import org.bian.accountbalance.resourceserver.data.model.Transaction;
import org.bian.accountbalance.resourceserver.data.repository.TransactionRepository;
import org.bian.accountbalance.resourceserver.service.TransactionService;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Flux<TransactionResponse> getTransactions(String accountNumber, String typeFilter, String dateFilter) {
        return transactionRepository
                .findByAccountId(UUID.fromString(accountNumber))
                .flatMap(TransactionServiceImpl::convertModelToResponse);
    }

    private static Mono<TransactionResponse> convertModelToResponse(Transaction transaction) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(transaction.getId());
        transactionResponse.setTransactionTs(transaction.getCreatedOn());
        transactionResponse.setType(transaction.getType());
        transactionResponse.setAmount(transaction.getAmount());
        return Mono.just(transactionResponse);
    }

    @Override
    public Mono<TransactionResponse> createTransaction(CreateTransactionRequest request) {
        return transactionRepository
                .save(convertRequestToModel(request))
                .flatMap(TransactionServiceImpl::convertModelToResponse)
                .onErrorResume(e -> Mono.error(new RuntimeException(e.getMessage())));
    }

    private static Transaction convertRequestToModel(CreateTransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(request.getAccountNumber());
        transaction.setCreatedOn(request.getTransactionTs());
        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        return transaction;
    }
}
