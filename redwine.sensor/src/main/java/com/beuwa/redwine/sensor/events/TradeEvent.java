package com.beuwa.redwine.sensor.events;

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

public class TradeEvent implements BusinessEvent {
    // buy or sell?
    private String side;

    // Price in Dlls per XBT
    private long price;

    // Satoshi's volume
    private long grossValue;

    // Contracts bought
    private long foreignNotional;

    private TradeEvent(String side, long price, long grossValue, long foreignNotional) {
        this.side = side;
        this.price = price;
        this.grossValue = grossValue;
        this.foreignNotional = foreignNotional;
    }

    public String getSide() {
        return side;
    }

    public long getPrice() {
        return price;
    }

    public long getGrossValue() {
        return grossValue;
    }

    public long getForeignNotional() {
        return foreignNotional;
    }

    public static class TradeEventBuilder {
        private String side;
        private long price;
        private long grossValue;
        private long foreignNotional;

        public TradeEventBuilder side(String side) {
            this.side = side;
            return this;
        }

        public TradeEventBuilder price(long price) {
            this.price = price;
            return this;
        }

        public TradeEventBuilder grossValue(long grossValue) {
            this.grossValue = grossValue;
            return this;
        }

        public TradeEventBuilder foreignNotional(long foreignNotional) {
            this.foreignNotional = foreignNotional;
            return this;
        }

        public TradeEvent build() {
            return new TradeEvent(
                    side,
                    price,
                    grossValue,
                    foreignNotional
            );
        }
    }
}
