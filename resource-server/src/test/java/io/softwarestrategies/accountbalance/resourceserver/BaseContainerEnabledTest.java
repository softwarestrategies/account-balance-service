package io.softwarestrategies.accountbalance.resourceserver;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@ActiveProfiles("Test")
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource("classpath:application-test.properties")
public abstract class BaseContainerEnabledTest {

    protected boolean databaseIsInitialized = false;

    protected void initializeDatabase(ConnectionFactory connectionFactory, String... scripts) throws IOException{
        for (String scriptFileName : scripts) {
            File file = new File(ClassLoader.getSystemResource(scriptFileName).getFile());
            Files.readAllLines(file.toPath()).forEach(statement -> executeSqlStatement(connectionFactory, statement)  );
        }
    }

    public void executeSqlStatement(ConnectionFactory connectionFactory, String sqlStatement) {
        Flux.from(connectionFactory.create())
                .flatMap(c -> Flux.from(c.createBatch().add(sqlStatement).execute()))
                .log().blockLast();
    }

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.0-alpine")
            .withDatabaseName("account_balance");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        String databaseUrl = postgreSQLContainer.getJdbcUrl().replace("jdbc", "r2dbc")
                + "&TC_IMAGE_TAG=13.0-alpine-test";
        registry.add("spring.r2dbc.url", () -> databaseUrl);
        registry.add("spring.r2dbc.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.r2dbc.password", () -> postgreSQLContainer.getPassword());
        registry.add("spring.r2dbc.port", () -> postgreSQLContainer.getFirstMappedPort());
    }
}



