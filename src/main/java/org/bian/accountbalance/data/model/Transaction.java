package org.bian.accountbalance.data.model;

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

    @Version
    @Column("version")
    private Integer version;

    @CreatedDate
    @JsonIgnore
    @Column("created_on")
    private LocalDateTime createdOn = LocalDateTime.now();

    @LastModifiedDate
    @JsonIgnore
    @Column("modified_on")
    private LocalDateTime modifiedOn = LocalDateTime.now();

    @Column("account_id")
    private UUID accountId;

    @Column("type")
    private String type;

    @Column("amount")
    private BigDecimal amount;
}
