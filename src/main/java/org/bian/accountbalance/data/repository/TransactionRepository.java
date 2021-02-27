package org.bian.accountbalance.data.repository;

import org.bian.accountbalance.data.model.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Integer> {

    @Query("SELECT id, created_on, type, amount FROM transaction WHERE account_id = :accountId")
    Flux<Transaction> findByAccountId(UUID accountId);
}
