package com.beuwa.redwine.strategy.rollercoaster.sampler;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

public class Sampler {

    public static void main(String[] args) {
        SeContainer seContainer = SeContainerInitializer.newInstance()
                .initialize();

        seContainer.getBeanManager().fireEvent(new SamplerEvent());
        seContainer.close();
    }
}
