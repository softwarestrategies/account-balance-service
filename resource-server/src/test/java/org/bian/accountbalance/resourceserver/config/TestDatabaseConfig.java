package org.bian.accountbalance.resourceserver.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
@Profile("Test")
class TestDatabaseConfig {

    @Bean
    public ConnectionFactoryInitializer connectionFactoryInitializer(
            @Qualifier("connectionFactory") ConnectionFactory connectionFactory
    ) {
        ConnectionFactoryInitializer connectionFactoryInitializer = new ConnectionFactoryInitializer();
        connectionFactoryInitializer.setConnectionFactory(connectionFactory);

        CompositeDatabasePopulator compositeDatabasePopulator = new CompositeDatabasePopulator();
        compositeDatabasePopulator.addPopulators(
                new ResourceDatabasePopulator(new ClassPathResource("create-schema-and-populate-as-base-for-tests.sql"))
        );

        connectionFactoryInitializer.setDatabasePopulator(compositeDatabasePopulator);
        return connectionFactoryInitializer;
    }
}