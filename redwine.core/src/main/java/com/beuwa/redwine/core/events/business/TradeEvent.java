package com.beuwa.redwine.core.events.business;

import com.beuwa.redwine.core.events.BusinessEvent;

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
    private String message;

    // When did the event happened
    private long epoch;

    private TradeEvent(String message, long epoch) {
        this.message = message;
        this.epoch = epoch;
    }

    public String getMessage() {
        return message;
    }

    public long getEpoch() {
        return epoch;
    }

    public static class TradeEventBuilder {
        private String message;
        private long epoch;

        public TradeEventBuilder message(String message) {
            this.message = message;
            return this;
        }

        public TradeEventBuilder epoch(long epoch) {
            this.epoch = epoch;
            return this;
        }

        public TradeEvent build() {
            return new TradeEvent(
                    message,
                    epoch
            );
        }
    }
}
