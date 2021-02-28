package org.bian.accountbalance.resourceserver.service.impl;

import org.bian.accountbalance.common.data.dto.BalanceDTO;
import org.bian.accountbalance.common.data.value.ApiAccountBalanceUpdateRequest;
import org.bian.accountbalance.common.exception.EntityNotFoundException;
import org.bian.accountbalance.resourceserver.data.repository.AccountRepository;
import org.bian.accountbalance.resourceserver.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<BalanceDTO> getBalance(String id) {
        return accountRepository.findById(id)
                .flatMap(a -> Mono.just(new BalanceDTO(a.getBalance())))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Account not found: " + id)));
    }

    @Override
    @Transactional(readOnly = false)
    public Mono<Void> updateAccountBalance(ApiAccountBalanceUpdateRequest request) {
        return accountRepository.updateBalance(request.getAccountNumber(), request.getLastUpdateTimestamp(), request.getBalance());
    }
}
