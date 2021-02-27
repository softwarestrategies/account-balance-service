package org.bian.accountbalance.service;

import org.bian.accountbalance.data.dto.BalanceDTO;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<BalanceDTO> getBalance(String accountNumber);
}
