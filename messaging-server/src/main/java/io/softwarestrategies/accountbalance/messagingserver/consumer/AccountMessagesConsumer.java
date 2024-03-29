package io.softwarestrategies.accountbalance.messagingserver.consumer;

import io.softwarestrategies.accountbalance.common.data.api.UpdateAccountBalanceRequest;
import io.softwarestrategies.accountbalance.common.data.messaging.AccountBalanceUpdatedMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AccountMessagesConsumer {

    private final static Log log = LogFactory.getLog(AccountMessagesConsumer.class);

    @Value("${resourceserver.api.base.url}")
    private String resourceServerApiBaseUrl;

    @Autowired
    private WebClient webClient;

    @KafkaListener(topics = "AccountBalanceUpdated", groupId = "AccountMessages-Consumer")
    public void processAccountBalanceUpdated(AccountBalanceUpdatedMessage message) {
        UpdateAccountBalanceRequest request = new UpdateAccountBalanceRequest();
        request.setAccountNumber(message.getAccountNumber());
        request.setLastUpdateTimestamp(message.getLastUpdateTimestamp());
        request.setBalance(message.getBalance());

        String uriToUse = resourceServerApiBaseUrl + "/account/"  + message.getAccountNumber() + "/accountbalance";

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
            // TODO There should be some retry logic or other error handling here.  For another day.
            log.error("Unable to process AccountBalanceUpdate request: " + e.getMessage());
        }
    }
}
