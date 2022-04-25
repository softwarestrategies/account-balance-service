package io.softwarestrategies.accountbalance.resourceserver.web;

import io.softwarestrategies.accountbalance.common.data.api.*;
import io.softwarestrategies.accountbalance.resourceserver.service.AccountService;
import io.softwarestrategies.accountbalance.resourceserver.service.TransactionService;
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

    @GetMapping("/{accountNumber}/accountbalance")
    public Mono<BalanceResponse> getBalance(@PathVariable(required = true) String accountNumber) {
        return accountService.getBalance(accountNumber);
    }

    @PatchMapping("/{accountNumber}/accountbalance")
    public Mono<Void> patchAccountBalance(@RequestBody UpdateAccountBalanceRequest request) {
        return accountService.updateAccountBalance(request);
    }

    @GetMapping("/{accountNumber}/transaction")
    public Flux<TransactionResponse> getTransactions(@PathVariable(required = true) String accountNumber,
                                                     GetTransactionsRequest getTransactionsRequest) {
        return transactionService.getTransactions(accountNumber, getTransactionsRequest);
    }

    @PostMapping("/{accountNumber}/transaction")
    public Mono<TransactionResponse> createTransaction(@RequestBody CreateTransactionRequest request, @PathVariable String accountNumber) {
        return transactionService.createTransaction(request);
    }
}
