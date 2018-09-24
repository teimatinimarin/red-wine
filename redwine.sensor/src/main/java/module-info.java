module redwine.sensor {
    // WebBeans
    requires cdi.api;
    requires javax.inject;
    requires java.annotation;

    // JSON
    requires org.glassfish.java.json;

    // Websocket Client
    requires tyrus.client;
    requires javax.websocket.api;
    requires jdk.incubator.httpclient;

    // Logging
    requires org.apache.logging.log4j;

    // AWS
    requires aws.core;
    requires secretsmanager;
    requires regions;

    exports com.beuwa.redwine.sensor.app to tyrus.core;


    // Temporal for junit in intellij
    opens com.beuwa.redwine.sensor;
    opens com.beuwa.redwine.sensor.app;
    opens com.beuwa.redwine.core.config;
    opens com.beuwa.redwine.core.events;
    opens com.beuwa.redwine.sensor.observers;
    opens com.beuwa.redwine.sensor.producers;
    opens com.beuwa.redwine.sensor.utils;
    opens com.beuwa.redwine.strategy.sma.statistics;
    opens com.beuwa.redwine.strategy.sma.observers;
    opens com.beuwa.redwine.strategy.sma.dao;
}
