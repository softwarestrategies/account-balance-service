package org.bian.accountbalance.resourceserver.data.repository;

import org.bian.accountbalance.resourceserver.data.model.Account;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface AccountRepository extends R2dbcRepository<Account, String> {

    @Modifying
    @Query("UPDATE account SET balance = :balance, modified_on = :modified_on WHERE id = :id")
    Mono<Void> updateBalance(
            @Param(value = "id") UUID id,
            @Param(value = "modified_on")LocalDateTime modified_on,
            @Param(value = "balance") BigDecimal balance
    );
}
