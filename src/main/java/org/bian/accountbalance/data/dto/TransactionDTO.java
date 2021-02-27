package org.bian.accountbalance.data.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private Integer id;
    private LocalDateTime transactionTs;
    private String type;
    private BigDecimal amount;
}
