package org.bian.accountbalance.messagingserver.consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bian.accountbalance.common.data.api.CreateTransactionRequest;
import org.bian.accountbalance.common.data.messaging.TransactionCreatedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TransactionMessagesConsumer {

    private final static Log log = LogFactory.getLog(TransactionMessagesConsumer.class);

    @Value("${resourceserver.api.base.url}")
    private String resourceServerApiBaseUrl;

    @Autowired
    private WebClient webClient;

    @KafkaListener(topics = "TransactionCreated", groupId = "TransactionMessages-Consumer")
    public void processTransactionCreated(TransactionCreatedMessage message) {
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAccountNumber(message.getAccountNumber());
        request.setTransactionTs(message.getTransactionTs());
        request.setType(message.getType());
        request.setAmount(message.getAmount());

        String uriToUse = resourceServerApiBaseUrl + "/account/"  + message.getAccountNumber() + "/transaction";

        try {
            webClient.post()
                    .uri(uriToUse)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
        catch (Exception e) {
            // TODO There should be some retry logic or other error handling here.  For another day.
            log.error("Unable to process AccountBalanceUpdate request: " + e.getMessage());
        }
    }
}
