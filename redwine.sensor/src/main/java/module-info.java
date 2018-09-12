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
    opens com.beuwa.redwine.sensor.config;
    opens com.beuwa.redwine.sensor.events;
    opens com.beuwa.redwine.sensor.observers;
    opens com.beuwa.redwine.sensor.producers;
    opens com.beuwa.redwine.sensor.utils;
}
