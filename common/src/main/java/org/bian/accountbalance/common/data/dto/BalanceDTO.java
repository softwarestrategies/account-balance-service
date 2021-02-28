package org.bian.accountbalance.common.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal balance;

    public BalanceDTO(BigDecimal balance) {
        this.balance = balance;
    }
}

