package org.bian.accountbalance.resourceserver.service;

import org.bian.accountbalance.common.data.api.CreateTransactionRequest;
import org.bian.accountbalance.common.data.api.GetTransactionsRequest;
import org.bian.accountbalance.common.data.api.TransactionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Flux<TransactionResponse> getTransactions(String accountNumber, GetTransactionsRequest request);
    Mono<TransactionResponse> createTransaction(CreateTransactionRequest request);
}
