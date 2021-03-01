package org.bian.accountbalance.common.data.messaging;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.bian.accountbalance.common.utils.BigDecimal2DigitDeserializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionCreatedMessage {

    private UUID accountNumber;
    private LocalDateTime transactionTs;
    private String type;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = BigDecimal2DigitDeserializer.class)
    private BigDecimal amount;

    public TransactionCreatedMessage() {}
}
