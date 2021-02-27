package org.bian.accountbalance.service.impl;

import org.bian.accountbalance.data.dto.BalanceDTO;
import org.bian.accountbalance.data.repository.AccountRepository;
import org.bian.accountbalance.service.AccountService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<BalanceDTO> getBalance(String accountNumber) {
        return accountRepository.findById(accountNumber)
                .flatMap(a -> Mono.just(new BalanceDTO(a.getId().toString(),a.getBalance())));
    }
}
