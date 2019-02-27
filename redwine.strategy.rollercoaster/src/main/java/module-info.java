module redwine.strategy.rollercoaster {
    requires redwine.core;
    requires java.sql;
    requires postgresql;

    requires jdk.unsupported;

    // JSON
    requires org.glassfish.java.json;

    // Websocket Client
    requires java.net.http;

    opens com.beuwa.redwine.strategy.rollercoaster.strategy;
    opens com.beuwa.redwine.strategy.rollercoaster.utils;
    opens com.beuwa.redwine.strategy.rollercoaster.statistics;
}
