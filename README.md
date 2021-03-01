# Account Balance-related Interview Challenge

## About

This project is for an interview challenge/test conversation. It is using the following:
- Java
- Spring Boot, Spring Data, Spring Kafka
- Docker, Docker Compose  
- PostgreSQL
- Kafka
- (Integration) Testing with JUnit & TestContainers 
- Gradle

This document is just to provide some context and some instructions, as I am not sure what may or may not be obvious.  Hopefully this is clear enough and that I didn't leave anything key out.

## Initial Setup

These should be done before testing.

Make sure that both Java and Docker are installed on the test machine

Testcontainers needs (and least it seems to on my Mac) these 2 Docker Images pulled before testing
- testcontainers/ryuk:0.3.0
- testcontainersofficial/ryuk

## (Automated) Testing

*NOTE: There are still more tests to write.*

From the root directory, simply run the following:

    %  ./gradlew clean build

## (Non-Automated) Testing or Use

### 1) Start up the needed infrastructure using Docker Compose

    %  docker-compose up -d

    Note:  

      This starts up Zookeeper, Kafka and PostgreSQL in Docker containers .. the "-d" running them in the background

      And with the first running, it creates in Kafka the topics we'll be using.  These are specified in the docker-compose.yml file

      And in PostgreSQL the database schema & data we'll be using is created.  This is a combination of the values in the docker-compose file and SQL in an auxilary file name ./scripts/db/init.sql 

      Remember: Later, the Docker containers can/should be collectively be shut down with:  docker-compose -stop

### 2) Though not necessary, you can look at data in the running containers to make sure that all is as it should be

#### Checking PostgreSQL

I happen to have PostgreSQL installed on my Mac and so can run psql from the command-line.  However, any PostgreSQL-enabled database client (e.g. DataGrip, Intellij Ultimate) can connect to the locally-running database.

From the command-line (and the password, which is specified in the docker-compose.yml file, is "changeme":

        psql -h localhost -p 5432 -d account_balance -U account_balance_admin --password

              account_balance=> \dt;

              account_balance=> select * from transaction;

              account_balance=> \q

#### Checking Kafka

To see that the topics will be using were created, one needs to go onto the Kafka docker container.  And then, with the right command, one can see the topics were created.

    %  docker exec -it kafka-account-balance bash

        #  kafka-topics.sh --list --bootstrap-server localhost:9092

              AccountBalanceUpdated
              TransactionCreated

        #  exit

### 3) Though not necessary, you can look at data in the running containers to make sure that all is as it should be

#### Startup 2 Spring Boot applications

Obviously, all the following building & running can be done in the IDE (I use Intellij), but I will show how to do it from the command-line

If not done above, make sure that the application jars were built:

    %  ./gradlew clean build

One on tab, startup the Messaging Server

    %  java -jar ./messaging-server/build/libs/messaging-server-0.0.1-SNAPSHOT.jar

And on another tab, startup the Resource Server

    %  java -jar ./resource-server/build/libs/resource-server-0.0.1-SNAPSHOT.jar

#### Test the REST API GET calls

Open up a 3rd tab.

Try the call to GET the Account Balance for an Account created at initial startup.  And you should get the following JSON as a response.

    %  curl -X GET -H "Accept: application/json" "localhost:7070/api/account/4fa3179e-78b5-11eb-9439-0242ac130002/accountbalance"

        { "balance":"4316.00" }

Now try the call to GET the Transactions based on the Type "WITHDRAW"

    %  curl -X GET -H "Accept: application/json" "localhost:7070/api/account/4fa3179e-78b5-11eb-9439-0242ac130002/transaction?type=WiTHDRAW"

        [
            { "id":11,"transactionTs":"2021-02-16T16:55:20.154656","type":"WITHDRAW","amount":"500.00"},
            { "id":13,"transactionTs":"2021-02-22T16:55:20.386515","type":"WITHDRAW","amount":"300.00" }
        ]

Now try the call to GET the Transaction based on dates (in this example, a custom date range)

    %  curl -X GET -H "Accept: application/json" "localhost:7070/api/account/4fa3179e-78b5-11eb-9439-0242ac130002/transaction?dateRangeDescriptor=custom&startDate=2021-02-15&endDate=2021-02-17"

        [
            { "id":3,"transactionTs":"2021-02-17T15:23:01.124295","type":"DEPOSIT","amount":"5000.00" },
            { "id":4,"transactionTs":"2021-02-17T15:23:01.125471","type":"DEPOSIT","amount":"5000.00"},
            { "id":5,"transactionTs":"2021-02-17T15:23:01.126586","type":"DEPOSIT","amount":"5000.00" } 
        ]

    NOTE:  The Date filtering is primarily based on a query param named "dateRangeDescriptor"

        Valid values:  Custom, Today, last7days, last30days

            If the dateRangeDescriptor is Custom, then there needs to be a startDate parameter too.  endDate is optional, defaulting to Now.

            Else, there is no need to include a startDate or endDate

For good measure, I also allow the call to get transaction to filter on both Type and Date:

    %  curl -X GET -H "Accept: application/json" "localhost:7070/api/account/4fa3179e-78b5-11eb-9439-0242ac130002/transaction?dateRangeDescriptor=custom&startDate=2021-02-13&type=withdraw"

#### Test the Messaging, and in turn, REST API POST and PATCH calls

From a tab, go onto the Kafka Docker container and run a message producer for the AccountBalanceUpdated topic

    %  docker exec -it kafka-account-balance bash

        #  kafka-console-producer.sh --broker-list localhost:9092 --topic AccountBalanceUpdated

            >   {"accountNumber": "4fa3179e-78b5-11eb-9439-0242ac130002", "lastUpdateTimestamp": "2020-01-01T01:02:03.8Z", "balance": 101.01}

And from some other tab, try making the call to get the Account Balance for this Account and see if it was updated.  We are using that call that was shown earlier.  And when we did it originally, the balance was 4316.00.  Now it should be "101.01"

    %  curl -X GET -H "Accept: application/json" "localhost:7070/api/account/4fa3179e-78b5-11eb-9439-0242ac130002/accountbalance"

        { "balance":"101.01" }

Now we can test adding a new Transaction.  You can break out of the previously-created message producer or open another tab and create one for the other topic

    %  docker exec -it kafka-account-balance bash

        #  kafka-console-producer.sh --broker-list localhost:9092 --topic TransactionCreated
    
            > {"accountNumber": "4fa3179e-78b5-11eb-9439-0242ac130002", "transactionTs": "1999-01-01T01:02:03.8Z", "type": "DEPOSIT", "amount": 1999.01}

And from some other tab, try making the call to get the transactions for a customer date range (in this case, all of 1999) and see if the one just pushed to the queue ultimately went thru via API call and was saved in the database.

    %  curl -X GET -H "Accept: application/json" "localhost:7070/api/account/4fa3179e-78b5-11eb-9439-0242ac130002/transaction?dateRangeDescriptor=custom&startDate=1999-01-01&endDate=1999-12-31"

        [ {"id":11,"transactionTs":"1999-01-01T01:02:03.8","type":"DEPOSIT","amount":"1999.01"} ]