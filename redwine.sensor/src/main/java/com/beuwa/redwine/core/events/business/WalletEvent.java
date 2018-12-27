package com.beuwa.redwine.core.events.business;

import com.beuwa.redwine.core.events.BusinessEvent;

/**
 * <code>
 * {
 * 	"table": "wallet",
 * 	"action": "partial",
 * 	"keys": ["account", "currency"],
 * 	"types": {
 * 		"account": "long",
 * 		"currency": "symbol",
 * 		"prevDeposited": "long",
 * 		"prevWithdrawn": "long",
 * 		"prevTransferIn": "long",
 * 		"prevTransferOut": "long",
 * 		"prevAmount": "long",
 * 		"prevTimestamp": "timestamp",
 * 		"deltaDeposited": "long",
 * 		"deltaWithdrawn": "long",
 * 		"deltaTransferIn": "long",
 * 		"deltaTransferOut": "long",
 * 		"deltaAmount": "long",
 * 		"deposited": "long",
 * 		"withdrawn": "long",
 * 		"transferIn": "long",
 * 		"transferOut": "long",
 * 		"amount": "long",
 * 		"pendingCredit": "long",
 * 		"pendingDebit": "long",
 * 		"confirmedDebit": "long",
 * 		"timestamp": "timestamp",
 * 		"addr": "symbol",
 * 		"script": "symbol",
 * 		"withdrawalLock": "symbols"
 *        },
 * 	"foreignKeys": {},
 * 	"attributes": {
 * 		"account": "sorted",
 * 		"currency": "grouped"
 *    },
 * 	"filter": {
 * 		"account": 183242
 *    },
 * 	"data": [{
 * 		"account": 183242,
 * 		"currency": "XBt",
 * 		"prevDeposited": 0,
 * 		"prevWithdrawn": 0,
 * 		"prevTransferIn": 0,
 * 		"prevTransferOut": 0,
 * 		"prevAmount": 0,
 * 		"prevTimestamp": "2018-08-21T12:00:00.000Z",
 * 		"deltaDeposited": 0,
 * 		"deltaWithdrawn": 0,
 * 		"deltaTransferIn": 0,
 * 		"deltaTransferOut": 0,
 * 		"deltaAmount": 0,
 * 		"deposited": 0,
 * 		"withdrawn": 0,
 * 		"transferIn": 0,
 * 		"transferOut": 0,
 * 		"amount": 0,
 * 		"pendingCredit": 0,
 * 		"pendingDebit": 0,
 * 		"confirmedDebit": 0,
 * 		"timestamp": "2018-08-22T02:00:01.690Z",
 * 		"addr": "3BMEXEpPekU1i1Ju47MRFrBr2p5uYhCA1F",
 * 		"script": "534104220936c3245597b1513a9a7fe96d96facf1a840ee21432a1b73c2cf42c1810284dd730f21ded9d818b84402863a2b5cd1afe3a3d13719d524482592fb23c88a34104445a80ce7d8c91c45247ccdb5d9bf8524a9541f0daf1b152953fb2ca782f5922e78bac68609ecf8f275674fbedca9d3b22891a8791c8cb99854d883fc072912a410472225d3abc8665cf01f703a270ee65be5421c6a495ce34830061eb0690ec27dfd1194e27b6b0b659418d9f91baec18923078aac18dc19699aae82583561fefe54104a24db5c0e8ed34da1fd3b6f9f797244981b928a8750c8f11f9252041daad7b2d95309074fed791af77dc85abdd8bb2774ed8d53379d28cd49f251b9c08cab7fc54ae",
 * 		"withdrawalLock": []
 *    }]
 * }
 * </code>
 */

public class WalletEvent implements BusinessEvent {
    private String message;
    private long amount;

    private WalletEvent(String message, long amount) {
        this.message = message;
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public long getAmount() {
        return amount;
    }

    public static class WalletEventBuilder {
        private String message;
        private long amount;

        public WalletEventBuilder message(String message) {
            this.message = message;
            return this;
        }
        public WalletEventBuilder amount(long amount) {
            this.amount = amount;
            return this;
        }

        public WalletEvent build() {
            return new WalletEvent(
                    message,
                    amount
            );
        }
    }
}
