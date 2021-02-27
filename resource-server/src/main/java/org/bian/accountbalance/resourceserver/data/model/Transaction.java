package org.bian.accountbalance.resourceserver.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("transaction")
public class Transaction {

    @Id
    @Column("id")
    private Integer id;

    @CreatedDate
    @JsonIgnore
    @Column("created_on")
    private LocalDateTime createdOn = LocalDateTime.now();

    @Column("account_id")
    private UUID accountId;

    @Column("type")
    private String type;

    @Column("amount")
    private BigDecimal amount;
}
