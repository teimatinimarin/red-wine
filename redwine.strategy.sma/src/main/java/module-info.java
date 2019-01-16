module redwine.strategy.sma {
    requires redwine.core;

    requires jdk.unsupported;

    // Websocket Client
    requires java.net.http;

    // Temporal for junit in intellij
    opens com.beuwa.redwine.strategy.sma.statistics;
    opens com.beuwa.redwine.strategy.sma.observers;
    opens com.beuwa.redwine.strategy.sma.dao;
    opens com.beuwa.redwine.strategy.sma.utils;
    opens com.beuwa.redwine.strategy.sma.facade;
    opens com.beuwa.redwine.strategy.sma.status;
    opens com.beuwa.redwine.strategy.sma.strategy;
}
