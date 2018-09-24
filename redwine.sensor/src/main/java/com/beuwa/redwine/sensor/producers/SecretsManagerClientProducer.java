package com.beuwa.redwine.sensor.producers;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

import javax.enterprise.inject.Produces;

public class SecretsManagerClientProducer {
    @Produces
    SecretsManagerClient createSecretsManagerClient() {
        return SecretsManagerClient.builder()
                .region(Region.EU_WEST_1)
                .build();
    }
}
