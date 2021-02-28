# Account Balance-related Interview Challenge

## DOCKER

Startup Docker

    # docker-compose up -d

The first time you run this, a couple of things will happen:

- A PostgreSQL container will be created & started, an application-specific user & database will be created and 2 tables that will be used will be created.  You can check this from the commandline like this:
  
        psql -h localhost -p 5432 -d account_balance -U account_balance_admin --password

              account_balance=> \dt;

- Both a Zookeeper & a Kafka containers will startup, with a topic created in Kafka name "Transactions".  This can be confirmed with the following by listing topics.

        docker exec -it kafka-account-balance bash

              bash# kafka-topics.sh --list --bootstrap-server localhost:9092

Then, on one you can start a producer and type in a few lines:

    bash# kafka-console-producer.sh --broker-list localhost:9092 --topic AccountBalanceUpdated
        >Here is something to process
        >Here is something else to process
        >

    bash# kafka-console-producer.sh --broker-list localhost:9092 --topic TransactionCreated



{"accountNumber": "4fa3179e-78b5-11eb-9439-0242ac130002", "lastUpdateTimestamp": "2020-01-01T01:02:03.8Z", "balance": 89.1}

{"accountNumber": "4fa3179e-78b5-11eb-9439-0242ac130002", "transactionTs": "2020-01-03T01:02:03.8Z", "type": "DEPOSIT", "amount": 89.1}


## Testcontainers

For testcontainers to work properly (at least on my Mac, for now, and I am not alone), these Docker images need to be
pulled to the dev machine where tests will be run:

- testcontainers/ryuk:0.3.0
- testcontainersofficial/ryuk
