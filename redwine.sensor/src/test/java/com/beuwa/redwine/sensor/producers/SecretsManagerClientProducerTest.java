package com.beuwa.redwine.sensor.producers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecretsManagerClientProducerTest {
    @InjectMocks
    SecretsManagerClientProducer secretsManagerClientProducer;

    @Test
    void createSecretsManagerClient() {
        SecretsManagerClient client = secretsManagerClientProducer.createSecretsManagerClient();
        assertNotNull(client);
    }
}