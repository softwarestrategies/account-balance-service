package org.bian.accountbalance.resourceserver.web;

import org.bian.accountbalance.common.data.api.BalanceResponse;
import org.bian.accountbalance.common.data.api.CreateTransactionRequest;
import org.bian.accountbalance.common.data.api.TransactionResponse;
import org.bian.accountbalance.common.data.api.UpdateAccountBalanceRequest;
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
    public Mono<BalanceResponse> getBalance(@PathVariable(required = true) String accountNumber) {
        return accountService.getBalance(accountNumber);
    }

    @PatchMapping("/{accountNumber}/accountbalance")
    public Mono<Void> patchAccountBalance(@RequestBody UpdateAccountBalanceRequest request) {
        return accountService.updateAccountBalance(request);
    }

    @GetMapping("/{accountNumber}/transaction")
    public Flux<TransactionResponse> getTransactions(@PathVariable(required = true) String accountNumber,
                                                     @RequestParam(name = "type", required = false) String typeFilter,
                                                     @RequestParam(name = "date", required = false) String dateFilter) {
        return transactionService.getTransactions(accountNumber, typeFilter, dateFilter);
    }

    @PostMapping("/{accountNumber}/transaction")
    public Mono<TransactionResponse> createTransaction(@RequestBody CreateTransactionRequest request) {
        return transactionService.createTransaction(request);
    }
}
