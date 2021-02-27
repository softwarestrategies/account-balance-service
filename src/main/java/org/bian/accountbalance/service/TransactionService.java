package org.bian.accountbalance.service;

import org.bian.accountbalance.data.dto.TransactionDTO;
import reactor.core.publisher.Flux;

public interface TransactionService {

    Flux<TransactionDTO> getTransactions(String accountNumber, String typeFilter, String dateFilter);
}
