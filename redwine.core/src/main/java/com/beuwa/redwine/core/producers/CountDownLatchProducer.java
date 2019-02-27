package com.beuwa.redwine.core.producers;

import javax.enterprise.inject.Produces;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchProducer {
    @Produces
    CountDownLatch createCountDownLatch() {
        return new CountDownLatch(1);
    }
}
