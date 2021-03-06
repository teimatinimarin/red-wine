package com.beuwa.redwine.core.model;

/**
 * <code>
 * {
 * 	"table": "trade",
 * 	"action": "partial",
 * 	"keys": [],
 * 	"types": {
 * 		"timestamp": "timestamp",
 * 		"symbol": "symbol",
 * 		"side": "symbol",
 * 		"size": "long",
 * 		"price": "float",
 * 		"tickDirection": "symbol",
 * 		"trdMatchID": "guid",
 * 		"grossValue": "long",
 * 		"homeNotional": "float",
 * 		"foreignNotional": "float"
 *        },
 * 	"foreignKeys": {
 * 		"symbol": "instrument",
 * 		"side": "side"
 *    },
 * 	"attributes": {
 * 		"timestamp": "sorted",
 * 		"symbol": "grouped"
 *    },
 * 	"filter": {
 * 		"symbol": "XBTUSD"
 *    },
 * 	"data": [{
 * 		"timestamp": "2018-09-05T19:28:19.016Z",
 * 		"symbol": "XBTUSD",
 * 		"side": "Buy",
 * 		"size": 25,
 * 		"price": 6890,
 * 		"tickDirection": "ZeroPlusTick",
 * 		"trdMatchID": "e3bed844-2fc5-54fc-1dad-49e5383d47a9",
 * 		"grossValue": 362850,
 * 		"homeNotional": 0.0036285,
 * 		"foreignNotional": 25
 *    }]
 * }
 * </code>
 */

public class Trade {
    private String message;

    // When did the event happened
    private long epoch;

    private double price;

    private long size;

    private String side;

    private double homeNotional;

    private Trade(
            String message,
            long epoch,
            double price,
            long size,
            String side,
            double homeNotional) {
        this.message = message;
        this.epoch = epoch;
        this.price = price;
        this.size = size;
        this.side = side;
        this.homeNotional = homeNotional;
    }

    public String getMessage() {
        return message;
    }

    public long getEpoch() {
        return epoch;
    }

    public double getPrice() {
        return price;
    }

    public long getSize() {
        return size;
    }

    public String getSide() {
        return side;
    }

    public double getHomeNotional() {
        return homeNotional;
    }

    public static class TradeEventBuilder {
        private String message;
        private long epoch;
        private double price;
        private long size;
        private String side;
        private double homenotional;

        public TradeEventBuilder message(String message) {
            this.message = message;
            return this;
        }

        public TradeEventBuilder epoch(long epoch) {
            this.epoch = epoch;
            return this;
        }

        public TradeEventBuilder price(double price) {
            this.price = price;
            return this;
        }

        public TradeEventBuilder size(long size) {
            this.size = size;
            return this;
        }

        public TradeEventBuilder side(String side) {
            this.side = side;
            return this;
        }

        public TradeEventBuilder homenotional(double homenotional) {
            this.homenotional = homenotional;
            return this;
        }

        public Trade build() {
            return new Trade(
                    message,
                    epoch,
                    price,
                    size,
                    side,
                    homenotional
            );
        }
    }
}
