package com.beuwa.redwine.core.config;

import com.beuwa.redwine.core.config.beans.Properties;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PropertiesTest {
    @Test
    void createPropertiesInstance() {
        Properties properties = new Properties.PropertiesBuilder()
                .endpoint("endpoint")
                .apiKey("api_key")
                .apiSecret("api_secret")
                .leverage("leverage")
                .redwineTrading("trading")
                .redwineTracking("tracking")
                .notifyOpen("notify_open")
                .notifyClose("notify_close")
                .maxInvest("max_invest")
                .build();

        assertEquals("endpoint", properties.getEndpoint());
        assertEquals("api_key", properties.getApiKey());
        assertEquals("api_secret", properties.getApiSecret());
        assertEquals("leverage", properties.getLeverage());
        assertEquals("trading", properties.getRedwineTrading());
        assertEquals("tracking", properties.getRedwineTracking());
        assertEquals("notify_open", properties.getNotifyOpen());
        assertEquals("notify_close", properties.getNotifyClose());
        assertEquals("max_invest", properties.getMaxInvest());
    }
}