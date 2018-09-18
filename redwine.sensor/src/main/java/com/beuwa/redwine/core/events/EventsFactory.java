package com.beuwa.redwine.core.events;

import javax.json.*;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.Instant;

public class EventsFactory {
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public BusinessEvent build(String message) {
        BusinessEvent event = null;

        try(JsonReader jsonReader = Json.createReader( new StringReader(message) )) {
            JsonObject jsonObject = jsonReader.readObject();


            String table = null;
            if(jsonObject.containsKey("table")) {
                table = jsonObject.getString("table");
            }
            if(table != null) {
                switch (table) {
                    case "wallet":
                        event = createWalletEvent(jsonObject);
                        break;
                    case "quote":
                        event = createQuoteEvent(jsonObject);
                        break;
                    case "trade":
                        event = createTradeEvent(jsonObject);
                        break;
                    case "instrument":
                        event = createInstrumentEvent(jsonObject);
                        break;
                    case "liquidation":
                        event = createLiquidationEvent(jsonObject);
                        break;
                    case "order":
                        event = createOrderEvent(jsonObject);
                        break;
                    case "position":
                        event = createPositionEvent(jsonObject);
                        break;
                    default:
                }
            }
        }

        return event;
    }

    private BusinessEvent createWalletEvent(JsonObject document) {
        JsonArray jsonArray = document.getJsonArray("data");
        JsonObject data = jsonArray.getJsonObject( jsonArray.size() -1 );
        long amount = data.getInt("amount");
        return new WalletEvent.WalletEventBuilder()
                .amount( amount )
                .build();
    }

    private BusinessEvent createQuoteEvent(JsonObject document) {
        JsonArray jsonArray = document.getJsonArray("data");
        JsonObject data = jsonArray.getJsonObject( jsonArray.size() -1 );

        long bidSize = data.getInt("bidSize");
        JsonNumber bidPriceNumber = data.getJsonNumber("bidPrice");
        long bidPrice = bidPriceNumber.bigDecimalValue().multiply(ONE_HUNDRED).longValue();

        long askSize = data.getInt("askSize");
        JsonNumber askPriceNumber = data.getJsonNumber("askPrice");
        long askPrice = askPriceNumber.bigDecimalValue().multiply(ONE_HUNDRED).longValue();

        return new QuoteEvent.QuoteEventBuilder()
                .bidSize( bidSize )
                .bidPrice( bidPrice )
                .askSize( askSize )
                .askPrice( askPrice )
                .build();
    }

    private BusinessEvent createTradeEvent(JsonObject document) {
        JsonArray jsonArray = document.getJsonArray("data");
        JsonObject data = jsonArray.getJsonObject( jsonArray.size() -1 );

        String side = data.getString("side");
        JsonNumber priceNumber = data.getJsonNumber("price");
        long price = priceNumber.bigDecimalValue().multiply(ONE_HUNDRED).longValue();
        long grossValue = data.getJsonNumber("grossValue").longValue();
        long foreignNotional = data.getJsonNumber("foreignNotional").longValue();

        return new TradeEvent.TradeEventBuilder()
                .side( side )
                .price( price )
                .grossValue( grossValue )
                .foreignNotional( foreignNotional )
                .build();
    }

    private BusinessEvent createInstrumentEvent(JsonObject document) {
        JsonArray jsonArray = document.getJsonArray("data");
        JsonObject data = jsonArray.getJsonObject( jsonArray.size() -1 );

        InstrumentEvent.InstrumentEventBuilder builder = new InstrumentEvent.InstrumentEventBuilder();

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

        return builder.build();
    }

    private BusinessEvent createLiquidationEvent(JsonObject jsonObject) {
        return new LiquidationEvent.LiquidationEventBuilder()
                .message( jsonObject.toString() )
                .build();
    }

    private BusinessEvent createOrderEvent(JsonObject jsonObject) {
        return new OrderEvent.OrderEventBuilder()
                .message( jsonObject.toString() )
                .build();
    }

    private BusinessEvent createPositionEvent(JsonObject jsonObject) {
        return new PositionEvent.PositionEventBuilder()
                .message( jsonObject.toString() )
                .build();
    }
}
