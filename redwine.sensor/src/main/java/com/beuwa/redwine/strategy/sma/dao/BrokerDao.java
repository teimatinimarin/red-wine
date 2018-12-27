package com.beuwa.redwine.strategy.sma.dao;

import com.beuwa.redwine.core.config.PropertiesFacade;
import com.beuwa.redwine.sensor.utils.HttpMethod;
import com.beuwa.redwine.sensor.utils.RestClient;
import com.beuwa.redwine.strategy.sma.facade.IntegrationFacade;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

import java.net.http.HttpResponse;

import static com.beuwa.redwine.strategy.sma.constants.Orders.*;

public class BrokerDao {
    @Inject
    Logger logger;

    @Inject
    RestClient restClient;

    @Inject
    private PropertiesFacade propertiesFacade;

    public void setLeverage() {
        var pathLeverage = "/api/v1/position/leverage";
        var bodyLeverage = String.format("symbol=XBTUSD&leverage=%s", propertiesFacade.getLeverage());

        logger.debug(bodyLeverage);
        HttpResponse<String> responseLeverage = restClient.doRequest(HttpMethod.POST, pathLeverage, bodyLeverage, "application/x-www-form-urlencoded");
        logger.debug("Response Code: {}", responseLeverage.statusCode());
        logger.debug(responseLeverage.body());
    }

    public void createEntryOrder(String familyOrderId, int orderQty, double triggerPrice, String side) {
        String pathOrder = "/api/v1/order/bulk";
        String bodyOrderTemplate = "{\"orders\":[" +
                "{\"clOrdID\":\"%s\",\"symbol\":\"XBTUSD\",\"side\":\"%s\",\"stopPx\":%.2f,\"orderQty\":%d,\"execInst\":\"LastPrice\"}" +
               "]}";
        String bodyOrder = String.format(
                bodyOrderTemplate,
                familyOrderId + "-" + ENTRY,
                side,
                triggerPrice,
                orderQty
                );

        logger.debug(bodyOrder);
        HttpResponse<String> responseOrder = restClient.doRequest(HttpMethod.POST, pathOrder, bodyOrder, "application/json");
        logger.debug("Response Code: {}", responseOrder.statusCode());
        logger.debug(responseOrder.body());
    }

    public void moveEntryOrder(String orderId, double triggerPrice) {
        String pathOrder = "/api/v1/order/bulk";
        String bodyOrderTemplate = "{\"orders\":[" +
                "{\"orderID\":\"%s\",\"stopPx\":%.2f}" +
                "]}";
        String bodyOrder = String.format(
                bodyOrderTemplate,
                orderId,
                triggerPrice
        );

        logger.debug(bodyOrder);
        HttpResponse<String> responseOrder = restClient.doRequest(HttpMethod.PUT, pathOrder, bodyOrder, "application/json");
        logger.debug("Response Code: {}", responseOrder.statusCode());
        logger.debug(responseOrder.body());
    }

    public void createExitOrders(String familyOrderId, double takeProfitPrice, double stopLossPrice, String entrySide) {
        String closeSide = entrySide.compareTo(IntegrationFacade.BUY)==0?IntegrationFacade.SELL:IntegrationFacade.BUY;
        String pathOrder = "/api/v1/order/bulk";
        String bodyOrderTemplate = "{\"orders\":[" +
                "{\"clOrdID\":\"%s\",\"symbol\":\"XBTUSD\",\"price\":%.2f,\"execInst\":\"Close\"}" + // TakeProfit
                ",{\"clOrdID\":\"%s\",\"symbol\":\"XBTUSD\",\"side\":\"%s\",\"stopPx\":%.2f,\"execInst\":\"LastPrice,Close\"}" + // StopLoss
                "]}";
        String bodyOrder = String.format(
                bodyOrderTemplate,
                familyOrderId + "-" + TAKE_PROFIT,
                takeProfitPrice,
                familyOrderId + "-" + STOP_LOSS,
                closeSide,
                stopLossPrice
        );

        logger.debug(bodyOrder);
        HttpResponse<String> responseOrder = restClient.doRequest(HttpMethod.POST, pathOrder, bodyOrder, "application/json");
        logger.debug("Response Code: {}", responseOrder.statusCode());
        logger.debug(responseOrder.body());
    }

    public void cancelAllOrders() {
        String pathOrder = "/api/v1/order/all";
        HttpResponse<String> responseOrder = restClient.doRequest(HttpMethod.DELETE, pathOrder, "", "application/x-www-form-urlencode");
        logger.debug("Response Code: {}", responseOrder.statusCode());
        logger.debug(responseOrder.body());
    }
}
