package org.bian.accountbalance.resourceserver.service.impl;

import org.bian.accountbalance.common.data.dto.TransactionDTO;
import org.bian.accountbalance.resourceserver.data.model.Transaction;
import org.bian.accountbalance.resourceserver.data.repository.TransactionRepository;
import org.bian.accountbalance.resourceserver.service.TransactionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Flux<TransactionDTO> getTransactions(String accountNumber, String typeFilter, String dateFilter) {
        return transactionRepository
                .findByAccountId(UUID.fromString(accountNumber))
                .flatMap(TransactionServiceImpl::convertToDTO);
    }

    private static Mono<TransactionDTO> convertToDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setTransactionTs(transaction.getCreatedOn());
        transactionDTO.setType(transaction.getType());
        transactionDTO.setAmount(transaction.getAmount());
        return Mono.just(transactionDTO);
    }
}
