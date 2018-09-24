package com.beuwa.redwine.sensor;

import com.beuwa.redwine.core.events.BootEvent;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

public class Main {
    public static void main(final String[] args) {
        // newInstance() looks up a META-INF service that implements the
        // SeContainerInitializer interface and loads that.
        // There can only be one.
        SeContainer seContainer = SeContainerInitializer.newInstance()
                // This JAR (bean-archive.jar) contains an empty beans.xml in the resources,
                // so the CDI container will find beans from it.
                // But if you have JARs that are not marked as bean archives,
                // you could add some of their packages to the container and
                // treat all those (CDI compatible POJOs) as beans:
                //.addPackages(Foo.class.getPackage())
                .initialize();


        // Fire synchronous event that triggers the code in App class.
        seContainer.getBeanManager().fireEvent(new BootEvent());

        seContainer.close();
    }
}

