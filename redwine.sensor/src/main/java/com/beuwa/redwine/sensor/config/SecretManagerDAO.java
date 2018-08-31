package com.beuwa.redwine.sensor.config;

import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.inject.Inject;

public class SecretManagerDAO {
    private static final String ENV = System.getProperty("ENVIRONMENT", "test");

    @Inject
    SecretsManagerClient smClient;

    public String retrieveSecrets() {
        // In case of Certificate Issues, see
        // https://stackoverflow.com/questions/6784463/error-trustanchors-parameter-must-be-non-empty/30699960#30699960
        GetSecretValueResponse response = smClient.getSecretValue( GetSecretValueRequest.builder()
                .secretId( ENV )
                .build() );

        return response.secretString();
    }
}
