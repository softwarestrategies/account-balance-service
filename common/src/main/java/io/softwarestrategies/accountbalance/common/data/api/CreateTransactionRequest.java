package io.softwarestrategies.accountbalance.common.data.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.softwarestrategies.accountbalance.common.utils.BigDecimal2DigitDeserializer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreateTransactionRequest {

    private UUID accountNumber;
    private LocalDateTime transactionTs;
    private String type;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = BigDecimal2DigitDeserializer.class)
    private BigDecimal amount;

    public CreateTransactionRequest() {}
}
