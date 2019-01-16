module redwine.strategy.writter {
    requires redwine.core;

    requires java.sql;

    exports com.beuwa.redwine.writter.observers;
    opens com.beuwa.redwine.writter.observers;
}
