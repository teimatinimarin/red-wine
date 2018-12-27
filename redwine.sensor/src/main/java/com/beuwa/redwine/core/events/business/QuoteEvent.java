package com.beuwa.redwine.core.events.business;

import com.beuwa.redwine.core.events.BusinessEvent;

/**
 * <code>
 * {
 * 	"table": "quote",
 * 	"action": "partial",
 * 	"keys": [],
 * 	"types": {
 * 		"timestamp": "timestamp",
 * 		"symbol": "symbol",
 * 		"bidSize": "long",
 * 		"bidPrice": "float",
 * 		"askPrice": "float",
 * 		"askSize": "long"
 *        },
 * 	"foreignKeys": {
 * 		"symbol": "instrument"
 *    },
 * 	"attributes": {
 * 		"timestamp": "sorted",
 * 		"symbol": "grouped"
 *    },
 * 	"filter": {
 * 		"symbol": "XBTUSD"
 *    },
 * 	"data": [{
 * 		"timestamp": "2018-09-05T19:28:18.718Z",
 * 		"symbol": "XBTUSD",
 * 		"bidSize": 150259,
 * 		"bidPrice": 6889.5,
 * 		"askPrice": 6890,
 * 		"askSize": 232311
 *    }]
 * }
 * </code>
 */

public class QuoteEvent implements BusinessEvent {
    private String message;
    private long bidSize;
    private long bidPrice;
    private long askSize;
    private long askPrice;

    private QuoteEvent(String message, long bidSize, long bidPrice, long askSize, long askPrice) {
        this.message = message;
        this.bidSize = bidSize;
        this.bidPrice = bidPrice;
        this.askSize = askSize;
        this.askPrice = askPrice;
    }

    public String getMessage() {
        return message;
    }

    public long getBidSize() {
        return bidSize;
    }

    public long getBidPrice() {
        return bidPrice;
    }

    public long getAskSize() {
        return askSize;
    }

    public long getAskPrice() {
        return askPrice;
    }

    public static class QuoteEventBuilder {
        private String message;
        private long bidSize;
        private long bidPrice;
        private long askSize;
        private long askPrice;

        public QuoteEventBuilder message(String message) {
            this.message = message;
            return this;
        }

        public QuoteEventBuilder bidSize(long bidSize) {
            this.bidSize = bidSize;
            return this;
        }

        public QuoteEventBuilder bidPrice(long bidPrice) {
            this.bidPrice = bidPrice;
            return this;
        }

        public QuoteEventBuilder askSize(long askSize) {
            this.askSize = askSize;
            return this;
        }

        public QuoteEventBuilder askPrice(long askPrice) {
            this.askPrice = askPrice;
            return this;
        }

        public QuoteEvent build() {
            return new QuoteEvent(
                    message,
                    bidSize,
                    bidPrice,
                    askSize,
                    askPrice
            );
        }
    }
}

