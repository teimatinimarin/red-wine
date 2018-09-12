package com.beuwa.redwine.sensor.config;

public class Properties {
    private String endpoint;
    private String apiKey;
    private String apiSecret;
    private String leverage;
    private String trading;
    private String notifyOpen;
    private String notifyClose;
    private String maxInvest;
    private boolean eventInstrumentEnable;
    private boolean eventLiquidationEnable;
    private boolean eventOrderEnable;
    private boolean eventPositionEnable;
    private boolean eventQuoteEnable;
    private boolean eventTradeEnable;
    private boolean eventWalletEnable;

    private Properties(String endpoint, String apiKey, String apiSecret, String leverage, String trading,
                       String notifyOpen, String notifyClose, String maxInvest, boolean eventInstrumentEnable,
                       boolean eventLiquidationEnable, boolean eventOrderEnable, boolean eventPositionEnable,
                       boolean eventQuoteEnable, boolean eventTradeEnable, boolean eventWalletEnable) {
        this.endpoint = endpoint;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.leverage = leverage;
        this.trading = trading;
        this.notifyOpen = notifyOpen;
        this.notifyClose = notifyClose;
        this.maxInvest = maxInvest;
        this.eventInstrumentEnable = eventInstrumentEnable;
        this.eventLiquidationEnable = eventLiquidationEnable;
        this.eventOrderEnable = eventOrderEnable;
        this.eventPositionEnable = eventPositionEnable;
        this.eventQuoteEnable = eventQuoteEnable;
        this.eventTradeEnable = eventTradeEnable;
        this.eventWalletEnable = eventWalletEnable;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public String getLeverage() {
        return leverage;
    }

    public String getTrading() {
        return trading;
    }

    public String getNotifyOpen() {
        return notifyOpen;
    }

    public String getNotifyClose() {
        return notifyClose;
    }

    public String getMaxInvest() {
        return maxInvest;
    }

    public boolean isEventInstrumentEnable() {
        return eventInstrumentEnable;
    }

    public boolean isEventLiquidationEnable() {
        return eventLiquidationEnable;
    }

    public boolean isEventOrderEnable() {
        return eventOrderEnable;
    }

    public boolean isEventPositionEnable() {
        return eventPositionEnable;
    }

    public boolean isEventQuoteEnable() {
        return eventQuoteEnable;
    }

    public boolean isEventTradeEnable() {
        return eventTradeEnable;
    }

    public boolean isEventWalletEnable() {
        return eventWalletEnable;
    }

    public static class PropertiesBuilder {
        private String endpoint;
        private String apiKey;
        private String apiSecret;
        private String leverage;
        private String trading;
        private String notifyOpen;
        private String notifyClose;
        private String maxInvest;
        private boolean eventInstrumentEnable;
        private boolean eventLiquidationEnable;
        private boolean eventOrderEnable;
        private boolean eventPositionEnable;
        private boolean eventQuoteEnable;
        private boolean eventTradeEnable;
        private boolean eventWalletEnable;


        public PropertiesBuilder endpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public PropertiesBuilder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public PropertiesBuilder apiSecret(String apiSecret) {
            this.apiSecret = apiSecret;
            return this;
        }

        public PropertiesBuilder leverage(String leverage) {
            this.leverage = leverage;
            return this;
        }

        public PropertiesBuilder trading(String trading) {
            this.trading = trading;
            return this;
        }

        public PropertiesBuilder notifyOpen(String notifyOpen) {
            this.notifyOpen = notifyOpen;
            return this;
        }

        public PropertiesBuilder notifyClose(String notifyClose) {
            this.notifyClose = notifyClose;
            return this;
        }

        public PropertiesBuilder maxInvest(String maxInvest) {
            this.maxInvest = maxInvest;
            return this;
        }

        public PropertiesBuilder eventInstrumentEnable(boolean eventInstrumentEnable) {
            this.eventInstrumentEnable = eventInstrumentEnable;
            return this;
        }

        public PropertiesBuilder eventLiquidationEnable(boolean eventLiquidationEnable) {
            this.eventLiquidationEnable = eventLiquidationEnable;
            return this;
        }

        public PropertiesBuilder eventOrderEnable(boolean eventOrderEnable) {
            this.eventOrderEnable = eventOrderEnable;
            return this;
        }

        public PropertiesBuilder eventPositionEnable(boolean eventPositionEnable) {
            this.eventPositionEnable = eventPositionEnable;
            return this;
        }

        public PropertiesBuilder eventQuoteEnable(boolean eventQuoteEnable) {
            this.eventQuoteEnable = eventQuoteEnable;
            return this;
        }

        public PropertiesBuilder eventTradeEnable(boolean eventTradeEnable) {
            this.eventTradeEnable = eventTradeEnable;
            return this;
        }

        public PropertiesBuilder eventWalletEnable(boolean eventWalletEnable) {
            this.eventWalletEnable = eventWalletEnable;
            return this;
        }

        public Properties build() {
            return new Properties(
                    this.endpoint,
                    this.apiKey,
                    this.apiSecret,
                    this.leverage,
                    this.trading,
                    this.notifyOpen,
                    this.notifyClose,
                    this.maxInvest,
                    this.eventInstrumentEnable,
                    this.eventLiquidationEnable,
                    this.eventOrderEnable,
                    this.eventPositionEnable,
                    this.eventQuoteEnable,
                    this.eventTradeEnable,
                    this.eventWalletEnable
            );
        }
    }
}
