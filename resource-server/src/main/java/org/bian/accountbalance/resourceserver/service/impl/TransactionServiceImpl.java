package org.bian.accountbalance.resourceserver.service.impl;

import io.micrometer.core.instrument.util.StringUtils;
import org.bian.accountbalance.common.data.api.CreateTransactionRequest;
import org.bian.accountbalance.common.data.api.GetTransactionsRequest;
import org.bian.accountbalance.common.data.api.TransactionResponse;
import org.bian.accountbalance.common.data.value.DateRange;
import org.bian.accountbalance.resourceserver.data.model.Transaction;
import org.bian.accountbalance.resourceserver.data.repository.TransactionRepository;
import org.bian.accountbalance.resourceserver.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Flux<TransactionResponse> getTransactions(String accountNumber, GetTransactionsRequest request) {
        if (!StringUtils.isEmpty(request.getDateRangeDescriptor())) {
            DateRange dateRange
                    = new DateRange(request.getDateRangeDescriptor(),request.getStartDate(), request.getEndDate());

            if (!StringUtils.isEmpty(request.getType())) {
                return transactionRepository
                        .findByAccountIdAndTypeAndDate(UUID.fromString(accountNumber), request.getType(), dateRange.getStartDate(), dateRange.getEndDate())
                        .flatMap(TransactionServiceImpl::convertModelToResponse);
            }
            else {
                return transactionRepository
                        .findByAccountIdAndDate(UUID.fromString(accountNumber), dateRange.getStartDate(), dateRange.getEndDate())
                        .flatMap(TransactionServiceImpl::convertModelToResponse);
            }
        }
        else if (!StringUtils.isEmpty(request.getType())) {
            return transactionRepository
                    .findByAccountIdAndType(UUID.fromString(accountNumber), request.getType())
                    .flatMap(TransactionServiceImpl::convertModelToResponse);
        }
        else {
            throw new RuntimeException("There needs to be some criteria");
        }
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
    @Transactional(readOnly = false)
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
