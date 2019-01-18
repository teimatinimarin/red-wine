module redwine.sensor {
    requires redwine.core;
    requires postgresql;

    requires jdk.unsupported;

    // JSON
    requires org.glassfish.java.json;

    // Websocket Client
    requires java.net.http;

    // Temporal for junit in intellij
    opens com.beuwa.redwine.sensor;
    opens com.beuwa.redwine.sensor.app;
    opens com.beuwa.redwine.sensor.producers;
}
