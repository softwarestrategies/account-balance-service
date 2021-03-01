package org.bian.accountbalance.resourceserver.service;

import io.r2dbc.spi.ConnectionFactory;
import org.bian.accountbalance.resourceserver.BaseContainerEnabledTest;
import org.bian.accountbalance.common.data.api.BalanceResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootTest()
public class AccountServiceTests extends BaseContainerEnabledTest {

    private final ConnectionFactory connectionFactory;
    private final AccountService accountService;

    @Autowired
    public AccountServiceTests(
            @Qualifier("connectionFactory") ConnectionFactory connectionFactory,
            AccountService accountService
    ) {
        this.connectionFactory = connectionFactory;
        this.accountService = accountService;
    }

    @BeforeEach
    public void setUp(ApplicationContext applicationContext) throws IOException {
        if (!databaseIsInitialized) {
            initializeDatabase(connectionFactory, "accountservicetest-extra-data.sql");
            databaseIsInitialized = true;
        }
    }

    @Test
    @Order(1)
    public void findAll() {
        BalanceResponse balanceResponse = accountService.getBalance("010c0558-7952-11eb-9439-0242ac130002").block();
        Assertions.assertEquals(1500.00, balanceResponse.getBalance().doubleValue());
    }
}
