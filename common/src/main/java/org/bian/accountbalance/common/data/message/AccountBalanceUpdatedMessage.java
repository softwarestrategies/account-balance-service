package org.bian.accountbalance.common.data.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.bian.accountbalance.common.utils.BigDecimal2DigitDeserializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AccountBalanceUpdatedMessage {

    private UUID accountNumber;
    private LocalDateTime lastUpdateTimestamp;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = BigDecimal2DigitDeserializer.class)
    private BigDecimal balance;

    public AccountBalanceUpdatedMessage() {}
}
