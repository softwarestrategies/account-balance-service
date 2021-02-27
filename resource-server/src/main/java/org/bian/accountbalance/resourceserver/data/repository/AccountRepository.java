package org.bian.accountbalance.resourceserver.data.repository;

import org.bian.accountbalance.resourceserver.data.model.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, String> {
}
