module redwine.sensor {
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

    exports com.beuwa.redwine.sensor;
    opens com.beuwa.redwine.sensor.config;

}
