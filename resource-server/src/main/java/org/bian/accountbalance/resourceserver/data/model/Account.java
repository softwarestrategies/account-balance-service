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
@Table("account")
public class Account {

    @Id
    @Column("id")
    private UUID id;

    @CreatedDate
    @JsonIgnore
    @Column("created_on")
    private LocalDateTime createdOn = LocalDateTime.now();

    @LastModifiedDate
    @JsonIgnore
    @Column("modified_on")
    private LocalDateTime modifiedOn = LocalDateTime.now();

    @Column("name")
    private String name;

    @Column("balance")
    private BigDecimal balance;
}
