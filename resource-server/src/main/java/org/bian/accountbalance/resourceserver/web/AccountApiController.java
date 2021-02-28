package org.bian.accountbalance.resourceserver.web;

import org.bian.accountbalance.common.data.dto.BalanceDTO;
import org.bian.accountbalance.common.data.dto.TransactionDTO;
import org.bian.accountbalance.common.data.value.ApiAccountBalanceUpdateRequest;
import org.bian.accountbalance.resourceserver.service.AccountService;
import org.bian.accountbalance.resourceserver.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/account")
public class AccountApiController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public AccountApiController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{accountNumber}")
    public Mono<BalanceDTO> getBalance(@PathVariable(required = true) String accountNumber) {
        return accountService.getBalance(accountNumber);
    }

    @PatchMapping("/{accountNumber}/accountbalance")
    public Mono<Void> patchAccountBalance(@RequestBody ApiAccountBalanceUpdateRequest request) {
        return accountService.updateAccountBalance(request);
    }

    @GetMapping("/{accountNumber}/transaction")
    public Flux<TransactionDTO> getTransactions(@PathVariable(required = true) String accountNumber,
                                                @RequestParam(name = "type", required = false) String typeFilter,
                                                @RequestParam(name = "date", required = false) String dateFilter) {
        return transactionService.getTransactions(accountNumber, typeFilter, dateFilter);
    }
}
