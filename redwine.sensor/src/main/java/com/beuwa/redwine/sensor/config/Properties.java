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

    private Properties(String endpoint, String apiKey, String apiSecret, String leverage, String trading, String notifyOpen, String notifyClose, String maxInvest) {
        this.endpoint = endpoint;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.leverage = leverage;
        this.trading = trading;
        this.notifyOpen = notifyOpen;
        this.notifyClose = notifyClose;
        this.maxInvest = maxInvest;
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

    public static class PropertiesBuilder {
        private String endpoint;
        private String apiKey;
        private String apiSecret;
        private String leverage;
        private String trading;
        private String notifyOpen;
        private String notifyClose;
        private String maxInvest;

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

        public Properties build() {
            return new Properties(
                    this.endpoint,
                    this.apiKey,
                    this.apiSecret,
                    this.leverage,
                    this.trading,
                    this.notifyOpen,
                    this.notifyClose,
                    this.maxInvest
            );
        }
    }
}
