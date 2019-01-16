package com.beuwa.redwine.core.config;

import com.beuwa.redwine.core.config.beans.Properties;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class PropertiesFactory {
    public Properties createProperties(String secrets) {
        try(JsonReader jsonReader = Json.createReader( new StringReader(secrets) )) {
            JsonObject jsonObject = jsonReader.readObject();

            return new Properties.PropertiesBuilder()
                    .endpoint(jsonObject.getString("endpoint"))
                    .apiKey(jsonObject.getString("api_key"))
                    .apiSecret(jsonObject.getString("api_secret"))
                    .redwineTrading(jsonObject.getString("redwine.trading"))
                    .redwineTracking(jsonObject.getString("redwine.tracking"))
                    .redwineSns(jsonObject.getString("redwine.sns"))
                    .notifyOpen(jsonObject.getString("open_email"))
                    .notifyClose(jsonObject.getString("close_email"))
                    .maxInvest(jsonObject.getString("trade.contracts.max"))
                    .leverage(jsonObject.getString("trade.contracts.leverage"))
                    .percentageToInvest(jsonObject.getString("trade.invest.percentage"))
                    .redwineSmaPeriod(jsonObject.getJsonNumber("redwine.sma.period").longValue())
                    .redwineWarmupPeriod(jsonObject.getJsonNumber("redwine.warmup.period").longValue())
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
