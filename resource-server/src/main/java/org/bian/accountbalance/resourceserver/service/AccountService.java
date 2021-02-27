package org.bian.accountbalance.resourceserver.service;

import org.bian.accountbalance.resourceserver.data.dto.BalanceDTO;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<BalanceDTO> getBalance(String id);
}
