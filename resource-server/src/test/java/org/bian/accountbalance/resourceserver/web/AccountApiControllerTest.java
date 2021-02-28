package org.bian.accountbalance.resourceserver.web;

import io.r2dbc.spi.ConnectionFactory;
import org.bian.accountbalance.resourceserver.BaseContainerEnabledTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountApiControllerTest extends BaseContainerEnabledTest {

    private final ConnectionFactory connectionFactory;
    private final WebTestClient webTestClient;

    @Autowired
    public AccountApiControllerTest(
            @Qualifier("connectionFactory") ConnectionFactory connectionFactory,
            WebTestClient webTestClient
    ) {
        this.connectionFactory = connectionFactory;
        this.webTestClient = webTestClient;
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
        webTestClient.get()
                .uri("/api/account/010c0558-7952-11eb-9439-0242ac130002")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.balance").isEqualTo("1500.00");
    }
}
