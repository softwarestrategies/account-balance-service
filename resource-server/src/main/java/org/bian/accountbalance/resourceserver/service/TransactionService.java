package org.bian.accountbalance.resourceserver.service;

import org.bian.accountbalance.resourceserver.data.dto.TransactionDTO;
import reactor.core.publisher.Flux;

public interface TransactionService {

    Flux<TransactionDTO> getTransactions(String accountNumber, String typeFilter, String dateFilter);
}
