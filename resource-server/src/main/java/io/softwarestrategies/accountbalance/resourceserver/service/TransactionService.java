package io.softwarestrategies.accountbalance.resourceserver.service;

import io.softwarestrategies.accountbalance.common.data.api.CreateTransactionRequest;
import io.softwarestrategies.accountbalance.common.data.api.GetTransactionsRequest;
import io.softwarestrategies.accountbalance.common.data.api.TransactionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Flux<TransactionResponse> getTransactions(String accountNumber, GetTransactionsRequest request);
    Mono<TransactionResponse> createTransaction(CreateTransactionRequest request);
}
