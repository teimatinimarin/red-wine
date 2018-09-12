package com.beuwa.redwine.sensor.events;

/**
 * <code
 * {
 * 	"table": "position",
 * 	"action": "partial",
 * 	"keys": ["account", "symbol", "currency"],
 * 	"types": {
 * 		"account": "long",
 * 		"symbol": "symbol",
 * 		"currency": "symbol",
 * 		"underlying": "symbol",
 * 		"quoteCurrency": "symbol",
 * 		"commission": "float",
 * 		"initMarginReq": "float",
 * 		"maintMarginReq": "float",
 * 		"riskLimit": "long",
 * 		"leverage": "float",
 * 		"crossMargin": "boolean",
 * 		"deleveragePercentile": "float",
 * 		"rebalancedPnl": "long",
 * 		"prevRealisedPnl": "long",
 * 		"prevUnrealisedPnl": "long",
 * 		"prevClosePrice": "float",
 * 		"openingTimestamp": "timestamp",
 * 		"openingQty": "long",
 * 		"openingCost": "long",
 * 		"openingComm": "long",
 * 		"openOrderBuyQty": "long",
 * 		"openOrderBuyCost": "long",
 * 		"openOrderBuyPremium": "long",
 * 		"openOrderSellQty": "long",
 * 		"openOrderSellCost": "long",
 * 		"openOrderSellPremium": "long",
 * 		"execBuyQty": "long",
 * 		"execBuyCost": "long",
 * 		"execSellQty": "long",
 * 		"execSellCost": "long",
 * 		"execQty": "long",
 * 		"execCost": "long",
 * 		"execComm": "long",
 * 		"currentTimestamp": "timestamp",
 * 		"currentQty": "long",
 * 		"currentCost": "long",
 * 		"currentComm": "long",
 * 		"realisedCost": "long",
 * 		"unrealisedCost": "long",
 * 		"grossOpenCost": "long",
 * 		"grossOpenPremium": "long",
 * 		"grossExecCost": "long",
 * 		"isOpen": "boolean",
 * 		"markPrice": "float",
 * 		"markValue": "long",
 * 		"riskValue": "long",
 * 		"homeNotional": "float",
 * 		"foreignNotional": "float",
 * 		"posState": "symbol",
 * 		"posCost": "long",
 * 		"posCost2": "long",
 * 		"posCross": "long",
 * 		"posInit": "long",
 * 		"posComm": "long",
 * 		"posLoss": "long",
 * 		"posMargin": "long",
 * 		"posMaint": "long",
 * 		"posAllowance": "long",
 * 		"taxableMargin": "long",
 * 		"initMargin": "long",
 * 		"maintMargin": "long",
 * 		"sessionMargin": "long",
 * 		"targetExcessMargin": "long",
 * 		"varMargin": "long",
 * 		"realisedGrossPnl": "long",
 * 		"realisedTax": "long",
 * 		"realisedPnl": "long",
 * 		"unrealisedGrossPnl": "long",
 * 		"longBankrupt": "long",
 * 		"shortBankrupt": "long",
 * 		"taxBase": "long",
 * 		"indicativeTaxRate": "float",
 * 		"indicativeTax": "long",
 * 		"unrealisedTax": "long",
 * 		"unrealisedPnl": "long",
 * 		"unrealisedPnlPcnt": "float",
 * 		"unrealisedRoePcnt": "float",
 * 		"simpleQty": "float",
 * 		"simpleCost": "float",
 * 		"simpleValue": "float",
 * 		"simplePnl": "float",
 * 		"simplePnlPcnt": "float",
 * 		"avgCostPrice": "float",
 * 		"avgEntryPrice": "float",
 * 		"breakEvenPrice": "float",
 * 		"marginCallPrice": "float",
 * 		"liquidationPrice": "float",
 * 		"bankruptPrice": "float",
 * 		"timestamp": "timestamp",
 * 		"lastPrice": "float",
 * 		"lastValue": "long"
 *        },
 * 	"foreignKeys": {
 * 		"symbol": "instrument"
 *    },
 * 	"attributes": {
 * 		"account": "sorted",
 * 		"symbol": "grouped",
 * 		"currency": "grouped",
 * 		"underlying": "grouped",
 * 		"quoteCurrency": "grouped"
 *    },
 * 	"filter": {
 * 		"account": 183242
 *    },
 * 	"data": []
 * }
 * </code>
 */

public class PositionEvent implements BusinessEvent {
    private String message;

    private PositionEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static class PositionEventBuilder {
        private String message;

        public PositionEventBuilder message(String message) {
            this.message = message;
            return this;
        }

        public PositionEvent build() {
            return new PositionEvent(message);
        }
    }
}

