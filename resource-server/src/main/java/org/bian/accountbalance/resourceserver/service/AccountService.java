package org.bian.accountbalance.resourceserver.service;

import org.bian.accountbalance.common.data.value.ApiAccountBalanceUpdateRequest;
import org.bian.accountbalance.common.data.dto.BalanceDTO;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<BalanceDTO> getBalance(String id);
    Mono<Void> updateAccountBalance(ApiAccountBalanceUpdateRequest request);
}
