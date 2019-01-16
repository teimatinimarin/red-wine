module redwine.core {
    requires java.annotation;
    requires java.sql;

    // WebBeans
    requires transitive cdi.api;
    requires transitive javax.inject;

    // JSON
    requires org.glassfish.java.json;

    // Websocket Client
    requires java.net.http;

    // Logging
    requires transitive org.apache.logging.log4j;

    // AWS
    requires transitive aws.core;
    requires transitive secretsmanager;
    requires transitive sns;
    requires transitive regions;

    exports com.beuwa.redwine.core.config;
    exports com.beuwa.redwine.core.config.beans;
    exports com.beuwa.redwine.core.events;
    exports com.beuwa.redwine.core.events.business;
    exports com.beuwa.redwine.core.services;
    exports com.beuwa.redwine.core.utils; // TODO: Internal Utils?

    opens com.beuwa.redwine.core.config;
    opens com.beuwa.redwine.core.config.beans;
    opens com.beuwa.redwine.core.events;
    opens com.beuwa.redwine.core.events.business;
    opens com.beuwa.redwine.core.services;
    opens com.beuwa.redwine.core.utils;
}
