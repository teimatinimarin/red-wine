package com.beuwa.redwine.core.model;

import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.json.*;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.Instant;

public class ModelFactory {
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    @Inject
    Logger logger;

    public Orderbook buildOrderbook(String json) {
        Orderbook orderbook = null;

        try(JsonReader jsonReader = Json.createReader( new StringReader(json) )) {
            JsonObject jsonObject = jsonReader.readObject();

            String action = jsonObject.getString("action");
            orderbook = new Orderbook(action);

            JsonArray jsonArray = jsonObject.getJsonArray("data");
            for(int i = 0; i < jsonArray.size(); i++) {
                JsonObject data = jsonArray.getJsonObject(i);
                OrderbookEntry.OrderbookL2Builder builder = new OrderbookEntry.OrderbookL2Builder();

                int id = data.getInt("id");
                builder.id(id);

                String symbol = data.getString("symbol");
                builder.symbol(symbol);

                String side = data.getString("side");
                builder.side(side);

                if(valid(data, "size")) {
                    int size = data.getInt("size");
                    builder.size(size);
                }

                if(valid(data, "price")) {
                    JsonNumber priceNumber = data.getJsonNumber("price");
                    long price = priceNumber.bigDecimalValue().multiply(ONE_HUNDRED).longValue();
                    builder.price(price);
                }

                OrderbookEntry entry = builder.build();
                orderbook.putEntry(entry);
            }
        }

        return orderbook;
    }

    public Trades buildTrade(String json) {
        Trades trades = null;

        try(JsonReader jsonReader = Json.createReader( new StringReader(json) )) {
            JsonObject jsonObject = jsonReader.readObject();

            String action = jsonObject.getString("action");
            trades = new Trades(action);

            JsonArray jsonArray = jsonObject.getJsonArray("data");
            for(int i = 0; i < jsonArray.size(); i++) {
                JsonObject data = jsonArray.getJsonObject(i);
                Trade.TradeEventBuilder builder = new Trade.TradeEventBuilder();

                String timestamp = data.getString("timestamp");
                long epoch = Instant.parse(timestamp).toEpochMilli();
                builder.epoch(epoch);

                JsonNumber priceNumber = data.getJsonNumber("price");
                if(priceNumber != null) {
                    long price = priceNumber.bigDecimalValue().multiply(ONE_HUNDRED).longValue();
                    builder.price( price );
                }

                long size = data.getInt("size");
                builder.size(size);

                String side = data.getString("side");
                builder.side(side);

                long homeNotional = data.getInt("homeNotional");
                builder.homenotional(homeNotional);

                Trade trade = builder.build();
                trades.putEntry(trade);
            }
        }

        return trades;
    }

    private boolean valid(JsonObject data, String key) {
        var valid = false;

        if(data.containsKey(key) && !data.isNull(key)) {
            valid = true;
        }

        return valid;
    }
}
