package com.beuwa.redwine.core.events.business;

import com.beuwa.redwine.core.events.BusinessEvent;

/**
 * <code>
 *{
 *	"table": "instrument",
 *	"action": "partial",
 *	"keys": ["symbol"],
 *	"types": {
 *		"symbol": "symbol",
 *		"rootSymbol": "symbol",
 *		"state": "symbol",
 *		"typ": "symbol",
 *		"listing": "timestamp",
 *		"front": "timestamp",
 *		"expiry": "timestamp",
 *		"settle": "timestamp",
 *		"relistInterval": "timespan",
 *		"inverseLeg": "symbol",
 *		"sellLeg": "symbol",
 *		"buyLeg": "symbol",
 *		"optionStrikePcnt": "float",
 *      "optionStrikeRound": "float",
 *		"optionStrikePrice": "float",
 *		"optionMultiplier": "float",
 *		"positionCurrency": "symbol",
 *		"underlying": "symbol",
 *		"quoteCurrency": "symbol",
 *		"underlyingSymbol": "symbol",
 *		"reference": "symbol",
 *		"referenceSymbol": "symbol",
 *		"calcInterval": "timespan",
 *		"publishInterval": "timespan",
 *		"publishTime": "timespan",
 *		"maxOrderQty": "long",
 *		"maxPrice": "float",
 *		"lotSize": "long",
 *		"tickSize": "float",
 *		"multiplier": "long",
 *		"settlCurrency": "symbol",
 *		"underlyingToPositionMultiplier": "long",
 *		"underlyingToSettleMultiplier": "long",
 *		"quoteToSettleMultiplier": "long",
 *		"isQuanto": "boolean",
 *		"isInverse": "boolean",
 *		"initMargin": "float",
 *		"maintMargin": "float",
 *		"riskLimit": "long",
 *		"riskStep": "long",
 *		"limit": "float",
 *		"capped": "boolean",
 *		"taxed": "boolean",
 *		"deleverage": "boolean",
 *		"makerFee": "float",
 *		"takerFee": "float",
 *		"settlementFee": "float",
 *		"insuranceFee": "float",
 *		"fundingBaseSymbol": "symbol",
 *		"fundingQuoteSymbol": "symbol",
 *		"fundingPremiumSymbol": "symbol",
 *		"fundingTimestamp": "timestamp",
 *		"fundingInterval": "timespan",
 *		"fundingRate": "float",
 *		"indicativeFundingRate": "float",
 *		"rebalanceTimestamp": "timestamp",
 *		"rebalanceInterval": "timespan",
 *		"openingTimestamp": "timestamp",
 *		"closingTimestamp": "timestamp",
 *		"sessionInterval": "timespan",
 *		"prevClosePrice": "float",
 *		"limitDownPrice": "float",
 *		"limitUpPrice": "float",
 *		"bankruptLimitDownPrice": "float",
 *		"bankruptLimitUpPrice": "float",
 *		"prevTotalVolume": "long",
 *		"totalVolume": "long",
 *		"volume": "long",
 *		"volume24h": "long",
 *		"prevTotalTurnover": "long",
 *		"totalTurnover": "long",
 *		"turnover": "long",
 *		"turnover24h": "long",
 *		"homeNotional24h": "float",
 *		"foreignNotional24h": "float",
 *		"prevPrice24h": "float",
 *		"vwap": "float",
 *		"highPrice": "float",
 *		"lowPrice": "float",
 *		"lastPrice": "float",
 *		"lastPriceProtected": "float",
 *		"lastTickDirection": "symbol",
 *		"lastChangePcnt": "float",
 *		"bidPrice": "float",
 *		"midPrice": "float",
 *		"askPrice": "float",
 *		"impactBidPrice": "float",
 *		"impactMidPrice": "float",
 *		"impactAskPrice": "float",
 *		"hasLiquidity": "boolean",
 *		"openInterest": "long",
 *		"openValue": "long",
 *		"fairMethod": "symbol",
 *		"fairBasisRate": "float",
 *		"fairBasis": "float",
 *		"fairPrice": "float",
 *		"markMethod": "symbol",
 *		"markPrice": "float",
 *		"indicativeTaxRate": "float",
 *		"indicativeSettlePrice": "float",
 *		"optionUnderlyingPrice": "float",
 *		"settledPrice": "float",
 *		"timestamp": "timestamp"
 *	},
 *	"foreignKeys": {
 *		"inverseLeg": "instrument",
 *		"sellLeg": "instrument",
 *		"buyLeg": "instrument"
 *	},
 *	"attributes": {
 *		"symbol": "unique"
 *	},
 *	"filter": {
 *		"symbol": "XBTUSD"
 *	},
 *	"data": [{
 *		"symbol": "XBTUSD",
 *		"rootSymbol": "XBT",
 *		"state": "Open",
 *		"typ": "FFWCSX",
 *		"listing": "2016-05-13T12:00:00.000Z",
 *		"front": "2016-05-13T12:00:00.000Z",
 *		"expiry": null,
 *		"settle": null,
 *		"relistInterval": null,
 *		"inverseLeg": "",
 *		"sellLeg": "",
 *		"buyLeg": "",
 *		"optionStrikePcnt": null,
 *		"optionStrikeRound": null,
 *		"optionStrikePrice": null,
 *		"optionMultiplier": null,
 *		"positionCurrency": "USD",
 *		"underlying": "XBT",
 *		"quoteCurrency": "USD",
 *		"underlyingSymbol": "XBT=",
 *		"reference": "BMEX",
 *		"referenceSymbol": ".BXBT",
 *		"calcInterval": null,
 *		"publishInterval": null,
 *		"publishTime": null,
 *		"maxOrderQty": 10000000,
 *		"maxPrice": 1000000,
 *		"lotSize": 1,
 *		"tickSize": 0.5,
 *		"multiplier": -100000000,
 *		"settlCurrency": "XBt",
 *		"underlyingToPositionMultiplier": null,
 *		"underlyingToSettleMultiplier": -100000000,
 *		"quoteToSettleMultiplier": null,
 *		"isQuanto": false,
 *		"isInverse": true,
 *		"initMargin": 0.01,
 *		"maintMargin": 0.005,
 * 		"riskLimit": 20000000000,
 *		"riskStep": 10000000000,
 *		"limit": null,
 *		"capped": false,
 *		"taxed": true,
 *		"deleverage": true,
 *		"makerFee": -0.00025,
 *		"takerFee": 0.00075,
 *		"settlementFee": 0,
 *		"insuranceFee": 0,
 *		"fundingBaseSymbol": ".XBTBON8H",
 *		"fundingQuoteSymbol": ".USDBON8H",
 *		"fundingPremiumSymbol": ".XBTUSDPI8H",
 *		"fundingTimestamp": "2018-09-05T20:00:00.000Z",
 *		"fundingInterval": "2000-01-01T08:00:00.000Z",
 *		"fundingRate": 0.0001,
 *		"indicativeFundingRate": 0.0001,
 *		"rebalanceTimestamp": null,
 *		"rebalanceInterval": null,
 *		"openingTimestamp": "2018-09-05T18:00:00.000Z",
 * 		"closingTimestamp": "2018-09-05T20:00:00.000Z",
 *		"sessionInterval": "2000-01-01T02:00:00.000Z",
 *		"prevClosePrice": 6992.26,
 *		"limitDownPrice": null,
 * 		"limitUpPrice": null,
 *		"bankruptLimitDownPrice": null,
 *		"bankruptLimitUpPrice": null,
 *		"prevTotalVolume": 785050476145,
 *		"totalVolume": 785142803481,
 *		"volume": 92327336,
 *		"volume24h": 3977688552,
 *		"prevTotalTurnover": 10593277451981390,
 *		"totalTurnover": 10594611842328474,
 *		"turnover": 1334390347084,
 *		"turnover24h": 56114471342879,
 *		"homeNotional24h": 561144.7134287939,
 *		"foreignNotional24h": 3977688552,
 *		"prevPrice24h": 7349.5,
 *		"vwap": 7088.6794,
 *		"highPrice": 7387,
 *		"lowPrice": 6819,
 *		"lastPrice": 6922,
 *		"lastPriceProtected": 6922,
 *		"lastTickDirection": "ZeroMinusTick",
 *		"lastChangePcnt": -0.0582,
 *		"bidPrice": 6922,
 * 		"midPrice": 6922.25,
 *		"askPrice": 6922.5,
 *		"impactBidPrice": 6921.8523,
 *		"impactMidPrice": 6922,
 *		"impactAskPrice": 6922.3314,
 *		"hasLiquidity": true,
 *		"openInterest": 732268038,
 *		"openValue": 10587131293404,
 *		"fairMethod": "FundingRate",
 *		"fairBasisRate": 0.1095,
 *		"fairBasis": 0.1,
 *		"fairPrice": 6916.39,
 *		"markMethod": "FairPrice",
 *		"markPrice": 6916.39,
 *		"indicativeTaxRate": 0,
 *		"indicativeSettlePrice": 6916.29,
 *		"optionUnderlyingPrice": null,
 *		"settledPrice": null,
 *		"timestamp": "2018-09-05T18:52:29.481Z"
 *	}]
 *}
 *</code>
 */

public class InstrumentEvent implements BusinessEvent {
    // https://www.bitmex.com/app/contract/XBTUSD

    private String message;

    private long epoch;

    // Total number of contracts in existence
    private long openInterest;

    // The value of contracts traded in the last 24h
    private long turnover24H;

    // The total number of contracts traded in the last 24H
    private long value24H;

    // Bid price
    private long bidPrice;

    // Ask
    private long askPrice;

    // This is the price used for PNL
    private long markPrice;

    private InstrumentEvent(String message, long epoch, long openInterest, long turnover24H, long value24H, long bidPrice, long askPrice, long markPrice) {
        this.message = message;
        this.epoch = epoch;
        this.openInterest = openInterest;
        this.turnover24H = turnover24H;
        this.value24H = value24H;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
        this.markPrice = markPrice;
    }

    public String getMessage() {
        return message;
    }

    public long getEpoch() {
        return epoch;
    }

    public long getOpenInterest() {
        return openInterest;
    }

    public long getTurnover24H() {
        return turnover24H;
    }

    public long getValue24H() {
        return value24H;
    }

    public long getBidPrice() {
        return bidPrice;
    }

    public long getAskPrice() {
        return askPrice;
    }

    public long getMarkPrice() {
        return markPrice;
    }

    public static class InstrumentEventBuilder {
        private String message;
        private long epoch;
        private long openInterest;
        private long turnover24H;
        private long value24H;
        private long bidPrice;
        private long askPrice;
        private long markPrice;

        public InstrumentEventBuilder message(String message) {
            this.message = message;
            return this;
        }

        public InstrumentEventBuilder epoch(long epoch) {
            this.epoch = epoch;
            return this;
        }

        public InstrumentEventBuilder openInterest(long openInterest) {
            this.openInterest = openInterest;
            return this;
        }

        public InstrumentEventBuilder turnover24H(long turnover24H) {
            this.turnover24H = turnover24H;
            return this;
        }

        public InstrumentEventBuilder value24H(long value24H) {
            this.value24H = value24H;
            return this;
        }

        public InstrumentEventBuilder bidPrice(long bidPrice) {
            this.bidPrice = bidPrice;
            return this;
        }

        public InstrumentEventBuilder askPrice(long askPrice) {
            this.askPrice = askPrice;
            return this;
        }

        public InstrumentEventBuilder markPrice(long markPrice) {
            this.markPrice = markPrice;
            return this;
        }

        public InstrumentEvent build() {
            return new InstrumentEvent(
                    message,
                    epoch,
                    openInterest,
                    turnover24H,
                    value24H,
                    bidPrice,
                    askPrice,
                    markPrice
            );
        }
    }
}
