package com.beuwa.redwine.core.producers;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SNSClient;

import javax.enterprise.inject.Produces;

public class SNSClientProducer {
    @Produces
    SNSClient createSNSClient() {
        return SNSClient.builder()
                .region(Region.EU_WEST_1)
                .build();
    }
}
