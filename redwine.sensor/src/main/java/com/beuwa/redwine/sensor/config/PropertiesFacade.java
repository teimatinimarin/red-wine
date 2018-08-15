package com.beuwa.redwine.sensor.config;

public class PropertiesFacade {
    public static Properties retrieveProperties() {
        String secrets = SecretManagerDAO.retrieveSecrets();
        Properties properties = PropertiesFactory.createProperties(secrets);

        return properties;
    }
}
