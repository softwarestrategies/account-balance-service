package org.bian.accountbalance.resourceserver.service;

import org.bian.accountbalance.common.data.api.UpdateAccountBalanceRequest;
import org.bian.accountbalance.common.data.api.BalanceResponse;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<BalanceResponse> getBalance(String id);
    Mono<Void> updateAccountBalance(UpdateAccountBalanceRequest request);
}
