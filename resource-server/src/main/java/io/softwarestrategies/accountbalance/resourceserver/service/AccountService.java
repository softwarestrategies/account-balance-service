package io.softwarestrategies.accountbalance.resourceserver.service;

import io.softwarestrategies.accountbalance.common.data.api.BalanceResponse;
import io.softwarestrategies.accountbalance.common.data.api.UpdateAccountBalanceRequest;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<BalanceResponse> getBalance(String id);
    Mono<Void> updateAccountBalance(UpdateAccountBalanceRequest request);
}
