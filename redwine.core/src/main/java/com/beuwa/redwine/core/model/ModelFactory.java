package com.beuwa.redwine.core.model;

import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.json.*;
import java.io.StringReader;

public class ModelFactory {
    @Inject
    Logger logger;

    public Orderbook buildOrderbook(String json) {
        Orderbook orderbook = null;

        try(JsonReader jsonReader = Json.createReader( new StringReader(json) )) {
            JsonObject jsonObject = jsonReader.readObject();

            String action = jsonObject.getString("action");
            orderbook = new Orderbook.OrderbookL2Builder()
                    .action(action)
                    .build();

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
                    long price = (long) (priceNumber.doubleValue() * 100);
                    builder.price(price);
                }

                OrderbookEntry entry = builder.build();
                orderbook.putEntry(entry);
            }
        }

        return orderbook;
    }

    private boolean valid(JsonObject data, String key) {
        var valid = false;

        if(data.containsKey(key) && !data.isNull(key)) {
            valid = true;
        }

        return valid;
    }
}
