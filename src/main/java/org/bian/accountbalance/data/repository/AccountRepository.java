package org.bian.accountbalance.data.repository;

import org.bian.accountbalance.data.model.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, String> {
}
