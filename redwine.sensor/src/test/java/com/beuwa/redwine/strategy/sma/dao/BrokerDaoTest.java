package com.beuwa.redwine.strategy.sma.dao;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BrokerDaoTest {
    @InjectMocks
    private BrokerDao brokerDao;

    @Mock
    private Logger logger;

    @Test
    void open() throws Exception {
        brokerDao.open();
    }
}