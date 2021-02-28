package org.bian.accountbalance.messagingserver.consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bian.accountbalance.messagingserver.data.TransactionCreatedMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionMessagesConsumer {

    private final static Log log = LogFactory.getLog(TransactionMessagesConsumer.class);

    @KafkaListener(topics = "TransactionCreated", groupId = "TransactionMessages-Consumer")
    public void processTransactionCreated(TransactionCreatedMessage transactionCreatedMessage) {
        log.info("Receiving & Performing Starting Process: ");
        log.info("......... Done");
    }
}
