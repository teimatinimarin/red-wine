package com.beuwa.redwine.core.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecretManagerDAOTest {
    @InjectMocks
    SecretManagerDAO secretManagerDAO;

    @Mock
    SecretsManagerClient smClient;

    @Test
    void retrieveSecrets() {
        GetSecretValueResponse response = Mockito.mock(GetSecretValueResponse.class);
        when(response.secretString()).thenReturn("Secret String");
        when(smClient.getSecretValue((GetSecretValueRequest) any())).thenReturn( response );
        String secret = secretManagerDAO.retrieveSecrets();
        Assertions.assertEquals("Secret String", secret);
    }
}