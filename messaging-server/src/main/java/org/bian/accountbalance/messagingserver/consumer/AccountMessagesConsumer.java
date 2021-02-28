package org.bian.accountbalance.messagingserver.consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bian.accountbalance.messagingserver.data.AccountBalanceUpdatedMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AccountMessagesConsumer {

    private final static Log log = LogFactory.getLog(AccountMessagesConsumer.class);

    @KafkaListener(topics = "AccountBalanceUpdated", groupId = "AccountMessages-Consumer")
    public void processAccountBalanceUpdated(AccountBalanceUpdatedMessage accountBalanceUpdatedMessage) {
        log.info("Receiving & Performing Starting Process: ");
        log.info("......... Done");
    }
}
