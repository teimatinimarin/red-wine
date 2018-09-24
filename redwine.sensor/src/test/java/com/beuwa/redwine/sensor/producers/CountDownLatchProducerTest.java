package com.beuwa.redwine.sensor.producers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CountDownLatchProducerTest {
    @InjectMocks
    CountDownLatchProducer countDownLatchProducer;

    @Test
    void createCountDownLatch() {
        CountDownLatch latch = countDownLatchProducer.createCountDownLatch();
        assertNotNull(latch);
    }
}