package com.beuwa.redwine.strategy.sma.dao;

import com.beuwa.redwine.core.config.PropertiesFacade;
import com.beuwa.redwine.sensor.utils.HttpMethod;
import com.beuwa.redwine.sensor.utils.RestClient;
import com.beuwa.redwine.strategy.sma.constants.Orders;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

import java.net.http.HttpResponse;
import java.util.UUID;

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

    public void createOrder(double orderQty, double triggerPrice, double takeProfitPrice, double stopLossPrice, String stopLossSide) {
        var ordLinkId = UUID.randomUUID().toString().substring(24, 36);
        String pathOrder = "/api/v1/order/bulk";
        String bodyOrderTemplate = "{\"orders\":[" +
                "{\"clOrdID\":\"%s\",\"clOrdLinkID\":\"%s\",\"symbol\":\"XBTUSD\",\"stopPx\":%.2f,\"orderQty\":%.8f,\"execInst\":\"LastPrice\",\"contingencyType\":\"OneTriggersTheOther\"}" +
                ",{\"clOrdID\":\"%s\",\"clOrdLinkID\":\"%s\",\"symbol\":\"XBTUSD\",\"price\":%.2f,\"execInst\":\"Close\",\"contingencyType\":\"OneCancelsTheOther\"}" +
                ",{\"clOrdID\":\"%s\",\"clOrdLinkID\":\"%s\",\"symbol\":\"XBTUSD\",\"side\":\"%s\",\"stopPx\":%.2f,\"execInst\":\"LastPrice,Close\",\"contingencyType\":\"OneCancelsTheOther\"}" +
                "]}";
        String bodyOrder = String.format(
                bodyOrderTemplate,
                ordLinkId+"-"+Orders.PARENT,
                ordLinkId,
                triggerPrice,
                orderQty,
                ordLinkId+"-"+Orders.TAKE_PROFIT,
                ordLinkId,
                takeProfitPrice,
                ordLinkId+"-"+ Orders.STOP_LOSS,
                ordLinkId,
                stopLossSide,
                stopLossPrice
                );

        logger.debug(bodyOrder);
        HttpResponse<String> responseOrder = restClient.doRequest(HttpMethod.POST, pathOrder, bodyOrder, "application/json");
        logger.debug("Response Code: {}", responseOrder.statusCode());
        logger.debug(responseOrder.body());
    }

    public void moveOrder(String clientOrderId, double triggerPrice, double takeProfitPrice, double stopLossPrice) {
        String pathOrder = "/api/v1/order/bulk";
        String bodyOrderTemplate = "{\"orders\":[" +
                "{\"origClOrdID\":\"%s\",\"stopPx\":%.2f}," +
                "{\"origClOrdID\":\"%s\",\"price\":%.2f}," +
                "{\"origClOrdID\":\"%s\",\"stopPx\":%.2f}" +
                "]}";
        String bodyOrder = String.format(
                bodyOrderTemplate,
                clientOrderId+"-"+Orders.PARENT,
                triggerPrice,
                clientOrderId+"-"+Orders.TAKE_PROFIT,
                takeProfitPrice,
                clientOrderId+"-"+Orders.STOP_LOSS,
                stopLossPrice
        );

        logger.debug(bodyOrder);
        HttpResponse<String> responseOrder = restClient.doRequest(HttpMethod.PUT, pathOrder, bodyOrder, "application/json");
        logger.debug("Response Code: {}", responseOrder.statusCode());
        logger.debug(responseOrder.body());
    }
}
