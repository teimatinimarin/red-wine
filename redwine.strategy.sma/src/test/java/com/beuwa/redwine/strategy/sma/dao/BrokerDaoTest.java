package com.beuwa.redwine.strategy.sma.dao;

import com.beuwa.redwine.core.config.PropertiesFacade;
import com.beuwa.redwine.core.services.RestClient;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpResponse;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrokerDaoTest {
    @InjectMocks
    private BrokerDao brokerDao;

    @Mock
    private PropertiesFacade propertiesFacade;

    @Mock
    private RestClient restClient;

    @Mock
    private Logger logger;

    @Test
    void setLeverage() throws Exception {
        HttpResponse<String> response = mock(HttpResponse.class);
        when(restClient.doRequest(anyInt(), anyString(), anyString(), anyString())).thenReturn(response);
        brokerDao.setLeverage();
    }
}
