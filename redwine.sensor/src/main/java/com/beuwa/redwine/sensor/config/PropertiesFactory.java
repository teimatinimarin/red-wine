package com.beuwa.redwine.sensor.config;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class PropertiesFactory {
    public  Properties createProperties(String secrets) {
        try(JsonReader jsonReader = Json.createReader( new StringReader(secrets) )) {
            JsonObject jsonObject = jsonReader.readObject();

            return new Properties.PropertiesBuilder()
                    .endpoint(jsonObject.getString("endpoint"))
                    .apiKey(jsonObject.getString("api_key"))
                    .apiSecret(jsonObject.getString("api_secret"))
                    .trading(jsonObject.getString("trading"))
                    .leverage(jsonObject.getString("leverage"))
                    .notifyOpen(jsonObject.getString("open_email"))
                    .notifyClose(jsonObject.getString("close_email"))
                    .maxInvest(jsonObject.getString("max_invest"))
                    .eventInstrumentEnable(Boolean.valueOf(jsonObject.getString("event.instrument.enable")))
                    .eventLiquidationEnable(Boolean.valueOf(jsonObject.getString("event.liquidation.enable")))
                    .eventOrderEnable(Boolean.valueOf(jsonObject.getString("event.order.enable")))
                    .eventPositionEnable(Boolean.valueOf(jsonObject.getString("event.position.enable")))
                    .eventQuoteEnable(Boolean.valueOf(jsonObject.getString("event.quote.enable")))
                    .eventTradeEnable(Boolean.valueOf(jsonObject.getString("event.trade.enable")))
                    .eventWalletEnable(Boolean.valueOf(jsonObject.getString("event.wallet.enable")))
                    .build();
        }
    }
}
