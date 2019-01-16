package com.beuwa.redwine.sensor;

import com.beuwa.redwine.core.events.BootEvent;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

public class Main {
    public static void main(final String[] args) {
        SeContainer seContainer = SeContainerInitializer.newInstance()
                .initialize();

        seContainer.getBeanManager().fireEvent(new BootEvent());
        seContainer.close();
    }
}

