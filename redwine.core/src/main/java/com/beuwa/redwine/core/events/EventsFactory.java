package com.beuwa.redwine.core.events;

import com.beuwa.redwine.core.events.business.*;

import javax.json.*;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.Instant;

public class EventsFactory {
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public BusinessEvent[] build(String message) {
        BusinessEvent[] events = null;

        try(JsonReader jsonReader = Json.createReader( new StringReader(message) )) {
            JsonObject jsonObject = jsonReader.readObject();


            String table = null;
            if(jsonObject.containsKey("table")) {
                table = jsonObject.getString("table");
            }
            if(table != null) {
                switch (table) {
                    case "wallet":
                        events = createWalletEvent(jsonObject);
                        break;
                    case "quote":
                        events = createQuoteEvent(jsonObject);
                        break;
                    case "trade":
                        events = createTradeEvent(jsonObject);
                        break;
                    case "instrument":
                        events = createInstrumentEvent(jsonObject);
                        break;
                    case "liquidation":
                        events = createLiquidationEvent(jsonObject);
                        break;
                    case "order":
                        events = createOrderEvent(jsonObject);
                        break;
                    case "position":
                        events = createPositionEvent(jsonObject);
                        break;
                    default:
                }
            }
        }

        return events;
    }

    private BusinessEvent[] createWalletEvent(JsonObject document) {
        JsonArray jsonArray = document.getJsonArray("data");
        JsonObject data = jsonArray.getJsonObject( jsonArray.size() -1 );
        long amount = data.getJsonNumber("amount").longValue();

        WalletEvent walletEvent = new WalletEvent.WalletEventBuilder()
                .message(document.toString())
                .amount(amount)
                .build();

        return new BusinessEvent[]{walletEvent};
    }

    private BusinessEvent[] createQuoteEvent(JsonObject document) {
        JsonArray jsonArray = document.getJsonArray("data");
        JsonObject data = jsonArray.getJsonObject( jsonArray.size() -1 );

        String timestamp = data.getString("timestamp");
        long epoch = Instant.parse(timestamp).toEpochMilli();

        long bidSize = data.getInt("bidSize");
        JsonNumber bidPriceNumber = data.getJsonNumber("bidPrice");
        long bidPrice = bidPriceNumber.bigDecimalValue().multiply(ONE_HUNDRED).longValue();

        long askSize = data.getInt("askSize");
        JsonNumber askPriceNumber = data.getJsonNumber("askPrice");
        long askPrice = askPriceNumber.bigDecimalValue().multiply(ONE_HUNDRED).longValue();

        QuoteEvent quoteEvent = new QuoteEvent.QuoteEventBuilder()
                .message(document.toString())
                .epoch(epoch)
                .bidSize( bidSize )
                .bidPrice( bidPrice )
                .askSize( askSize )
                .askPrice( askPrice )
                .build();

        return new BusinessEvent[]{quoteEvent};
    }

    private BusinessEvent[] createTradeEvent(JsonObject document) {
        JsonArray jsonArray = document.getJsonArray("data");
        JsonObject data = jsonArray.getJsonObject( jsonArray.size() -1 );

        String timestamp = data.getString("timestamp");
        long epoch = Instant.parse(timestamp).toEpochMilli();

        TradeEvent tradeEvent = new TradeEvent.TradeEventBuilder()
                .message(document.toString())
                .epoch(epoch)
                .build();

        return new BusinessEvent[]{tradeEvent};
    }

    private BusinessEvent[] createInstrumentEvent(JsonObject document) {
        JsonArray jsonArray = document.getJsonArray("data");
        JsonObject data = jsonArray.getJsonObject( jsonArray.size() -1 );

        InstrumentEvent.InstrumentEventBuilder builder = new InstrumentEvent.InstrumentEventBuilder();

        builder.message(document.toString());

        String timestamp = data.getString("timestamp");
        builder.epoch(Instant.parse(timestamp).toEpochMilli());

        JsonNumber openInterestNumber =  data.getJsonNumber("openInterest");
        if(openInterestNumber != null) {
            long openInterest = data.getJsonNumber("openInterest").longValue();
            builder.openInterest( openInterest );
        }

        JsonNumber turnover24hNumber = data.getJsonNumber("turnover24h");
        if(turnover24hNumber != null) {
            long turnover24h = turnover24hNumber.longValue();
            builder.turnover24H( turnover24h );
        }

        JsonNumber volume24hNumber = data.getJsonNumber("volume24h");
        if(volume24hNumber != null) {
            long volume24h = volume24hNumber.longValue();
            builder.value24H( volume24h );
        }

        JsonNumber bidPriceNumber = data.getJsonNumber("bidPrice");
        if(bidPriceNumber != null) {
            long bidPrice = bidPriceNumber.bigDecimalValue().multiply(ONE_HUNDRED).longValue();
            builder.bidPrice( bidPrice );
        }

        JsonNumber askPriceNumber = data.getJsonNumber("askPrice");
        if(askPriceNumber != null) {
            long askPrice = askPriceNumber.bigDecimalValue().multiply(ONE_HUNDRED).longValue();
            builder.askPrice( askPrice );
        }

        JsonNumber markPriceNumber = data.getJsonNumber("markPrice");
        if(markPriceNumber != null) {
            long markPrice = markPriceNumber.bigDecimalValue().multiply(ONE_HUNDRED).longValue();
            builder.markPrice( markPrice );
        }

        return new BusinessEvent[]{builder.build()};
    }

    private BusinessEvent[] createLiquidationEvent(JsonObject jsonObject) {
        LiquidationEvent liquidationEvent = new LiquidationEvent.LiquidationEventBuilder()
                .message( jsonObject.toString() )
                .build();

        return new BusinessEvent[]{liquidationEvent};
    }

    private BusinessEvent[] createOrderEvent(JsonObject document) {
        JsonArray jsonArray = document.getJsonArray("data");
        OrderEvent[] orderEvents = new OrderEvent[jsonArray.size()];
        for(int i = 0; i < jsonArray.size(); i++) {
            JsonObject data = jsonArray.getJsonObject(i);
            OrderEvent.OrderEventBuilder builder = new OrderEvent.OrderEventBuilder();

            builder.message(document.toString());

            String action = document.getString("action");
            builder.action(action);

            String orderId = data.getString("orderID");
            builder.orderId(orderId);

            String clientOrderId = data.getString("clOrdID");
            builder.clientOrderId(clientOrderId);

            if(valid(data, "side")) {
                String side = data.getString("side");
                builder.side(side);
            }

            if(valid(data, "orderQty")) {
                JsonNumber orderQtyNumber = data.getJsonNumber("orderQty");
                long orderQty = (long) (orderQtyNumber.doubleValue() * 100000000);
                builder.orderQty(orderQty);
            }

            if(valid(data, "price")) {
                JsonNumber priceNumber = data.getJsonNumber("price");
                long price = (long) (priceNumber.doubleValue() * 100);
                builder.price(price);
            }

            if(valid(data, "stopPx")) {
                JsonNumber stopPxNumber = data.getJsonNumber("stopPx");
                long stopPx = (long) (stopPxNumber.doubleValue() * 100);
                builder.stopPx(stopPx);
            }

            if(valid(data, "ordType")) {
                String orderType = data.getString("ordType");
                builder.orderType(orderType);
            }

            if(valid(data, "ordStatus")) {
                String orderStatus = data.getString("ordStatus");
                builder.orderStatus(orderStatus);
            }

            if(valid(data, "execInst")) {
                String executionInstruction = data.getString("execInst");
                builder.executionInstruction(executionInstruction);
            }

            if(valid(data, "triggered")) {
                String triggered = data.getString("triggered");
                builder.triggered(triggered);
            }

            orderEvents[i] = builder.build();
        }

        return orderEvents;
    }

    private BusinessEvent[] createPositionEvent(JsonObject document) {
        PositionEvent positionEvent = null;

        JsonArray jsonArray = document.getJsonArray("data");
        if(jsonArray.size() > 0) {
            JsonObject data = jsonArray.getJsonObject(0);

            PositionEvent.PositionEventBuilder builder = new PositionEvent.PositionEventBuilder();
            builder.message(document.toString());
            if (data.containsKey("isOpen")) {
                builder.message(document.toString());

                String action = document.getString("action");
                builder.action(action);

                builder.positionOpened(data.getBoolean("isOpen"));

                JsonNumber positionMarginNumber = data.getJsonNumber("posMargin");
                if (positionMarginNumber != null) {
                    long positionMargin = positionMarginNumber.longValue();
                    builder.positionMargin(positionMargin);
                }

                JsonNumber positionContractsNumber = data.getJsonNumber("currentQty");
                if (positionContractsNumber != null) {
                    long positionContracts = positionContractsNumber.longValue();
                    builder.positionContracts(positionContracts);
                }

                JsonNumber realisedPnlNumber = data.getJsonNumber("realisedPnl");
                if (realisedPnlNumber != null) {
                    long realisedPnl = realisedPnlNumber.longValue();
                    builder.realisedPnl(realisedPnl);
                }

                positionEvent = builder.build();
            }
        }

        return new BusinessEvent[]{positionEvent};
    }

    private boolean valid(JsonObject data, String key) {
        var valid = false;

        if(data.containsKey(key) && !data.isNull(key)) {
           valid = true;
        }

        return valid;
    }
}
