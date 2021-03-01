package org.bian.accountbalance.common.data.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {

    private Integer id;
    private LocalDateTime transactionTs;
    private String type;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;
}
