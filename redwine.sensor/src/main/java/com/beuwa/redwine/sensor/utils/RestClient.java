package com.beuwa.redwine.sensor.utils;

import com.beuwa.redwine.core.config.PropertiesFacade;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

import static com.beuwa.redwine.sensor.utils.HttpStatusCode.*;

public class RestClient {
    private int EXPIRES_IN = 3600;

    @Inject
    Logger logger;

    @Inject
    Signer signer;

    @Inject
    private PropertiesFacade propertiesFacade;

    public HttpResponse<String> doRequest(int method, String path, String body, String contentType) {
        HttpClient httpClient = HttpClient
                .newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        long expires = Instant.now().getEpochSecond() + EXPIRES_IN;

        String signature = null;
        try {
            signature = signer.sign(HttpMethod.methods[method], path, expires, body);
        } catch (Exception e) {
            logger.error("Impossible to Sign request cause: {}", e.getMessage());
            throw new RuntimeException("Impossible to Sign request", e);
        }

        String url = String.format("%s%s", propertiesFacade.getHttpsEndpoint(), path);

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .setHeader("Content-Type", contentType)
                .setHeader("api-expires", String.format("%d", expires))
                .setHeader("api-key", propertiesFacade.getApiKey())
                .setHeader("api-signature", signature);

        switch (method) {
            case HttpMethod.GET:
                requestBuilder = requestBuilder.GET();
                break;
            case HttpMethod.POST:
                requestBuilder = requestBuilder.POST(HttpRequest.BodyPublishers.ofString(body));
                break;
            case HttpMethod.PUT:
                requestBuilder = requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(body));
                break;
            default:
                break;
        }

        HttpRequest request = requestBuilder.build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if(OK != response.statusCode()) {
                logger.warn("Response status code: {}", response.statusCode());
                logger.warn("Response headers: {}", response.headers());
                logger.warn("Response body: {}", response.body());
            }
        } catch (IOException e) {
            logger.error("IOException when making a request: {}", e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Interrupting the Thread. {}", e);
        }

        return response;
    }
}
