package com.beuwa.redwine.sensor.config;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class SecretManagerDAO {
    public static void main(String[] args) {
        SecretsManagerClient smClient = SecretsManagerClient.builder()
                .region(Region.EU_WEST_1)
                .build();

        // In case of Certificate Issues, see
        // https://stackoverflow.com/questions/6784463/error-trustanchors-parameter-must-be-non-empty/30699960#30699960
        GetSecretValueResponse response = smClient.getSecretValue( GetSecretValueRequest.builder()
                .secretId("test")
                .build() );

        JsonReader jsonReader = Json.createReader( new StringReader(response.secretString()) );
        JsonObject jsonObject = jsonReader.readObject();
        System.out.println( jsonObject.getString("endpoint") );
    }
}
