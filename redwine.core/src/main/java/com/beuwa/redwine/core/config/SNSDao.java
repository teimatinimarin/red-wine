package com.beuwa.redwine.core.config;

import software.amazon.awssdk.services.sns.SNSClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import javax.inject.Inject;

public class SNSDao {
    @Inject
    SNSClient snsClient;

    @Inject
    PropertiesFacade propertiesFacade;

    public String publish(String subject, String message) {
        PublishRequest request = PublishRequest.builder()
                .topicArn(propertiesFacade.getRedwineSns())
                .subject(subject)
                .message(message)
                .build();
        PublishResponse response = snsClient.publish(request);

        return response.messageId();
    }
}
