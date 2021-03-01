package org.bian.accountbalance.common.data.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal balance;

    public BalanceResponse(BigDecimal balance) {
        this.balance = balance;
    }
}

