package org.bian.accountbalance.resourceserver.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDTO {

    private String accountNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal balance;

    public BalanceDTO(String accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}

