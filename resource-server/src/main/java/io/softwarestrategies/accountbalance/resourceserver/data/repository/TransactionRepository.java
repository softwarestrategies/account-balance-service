package io.softwarestrategies.accountbalance.resourceserver.data.repository;

import io.softwarestrategies.accountbalance.resourceserver.data.model.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface TransactionRepository extends R2dbcRepository<Transaction, Integer> {

    @Query("SELECT id, created_on, type, amount FROM transaction WHERE account_id = :accountId" +
            " AND upper(type) = upper(:type)")
    Flux<Transaction> findByAccountIdAndType(UUID accountId, String type);

    @Query("SELECT id, created_on, type, amount FROM transaction WHERE account_id = :accountId" +
            " AND created_on >= :start AND created_on <= :end"
    )
    Flux<Transaction> findByAccountIdAndDate(UUID accountId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT id, created_on, type, amount FROM transaction WHERE account_id = :accountId" +
            " AND upper(type) = upper(:type)" +
            " AND created_on >= :start AND created_on <= :end"
    )
    Flux<Transaction> findByAccountIdAndTypeAndDate(UUID accountId, String type, LocalDateTime start, LocalDateTime end);
}
