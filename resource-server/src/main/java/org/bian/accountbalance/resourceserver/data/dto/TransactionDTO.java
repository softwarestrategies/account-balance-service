package org.bian.accountbalance.resourceserver.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private Integer id;
    private LocalDateTime transactionTs;
    private String type;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;
}
