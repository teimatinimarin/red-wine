package com.beuwa.redwine.sensor.config;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class PropertiesFactory {
    private PropertiesFactory() {
    }

    public static Properties createProperties(String secrets) {
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
                    .build();
        }
    }
}
