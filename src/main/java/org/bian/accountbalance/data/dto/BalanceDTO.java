package org.bian.accountbalance.data.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDTO {

    private String accountNumber;
    private BigDecimal balance;

    public BalanceDTO(String accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}

