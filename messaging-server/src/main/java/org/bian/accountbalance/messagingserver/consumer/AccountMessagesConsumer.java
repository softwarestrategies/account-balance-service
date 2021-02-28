package org.bian.accountbalance.messagingserver.consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bian.accountbalance.common.data.message.AccountBalanceUpdatedMessage;
import org.bian.accountbalance.common.data.value.ApiAccountBalanceUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AccountMessagesConsumer {

    private final static Log log = LogFactory.getLog(AccountMessagesConsumer.class);

    @Value("${resourceserver.api.project.url}")
    private String resourceServerAccountApiBaseUrl;

    @Autowired
    private WebClient webClient;

    @KafkaListener(topics = "AccountBalanceUpdated", groupId = "AccountMessages-Consumer")
    public void processAccountBalanceUpdated(AccountBalanceUpdatedMessage message) {
        ApiAccountBalanceUpdateRequest request = new ApiAccountBalanceUpdateRequest();
        request.setAccountNumber(message.getAccountNumber());
        request.setLastUpdateTimestamp(message.getLastUpdateTimestamp());
        request.setBalance(message.getBalance());

        String uriToUse = resourceServerAccountApiBaseUrl + message.getAccountNumber() + "/accountbalance";

        try {
            webClient.patch()
                    .uri( uriToUse )
                    .bodyValue( request )
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            log.info("Request processed");
        }
        catch (Exception e) {
            log.error("Unable to process AccountBalanceUpdate request: " + e.getMessage());
        }
    }
}
