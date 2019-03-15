package com.beuwa.redwine.core.services;

import com.beuwa.redwine.core.model.Orderbook;
import com.beuwa.redwine.core.model.OrderbookEntry;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;

@Singleton
public class OrderbookService {
    @Inject
    private Logger logger;

    private boolean inited;
    private Orderbook orderbook;

    public void process(Orderbook orderbook) {
        String action = orderbook.getAction();
        switch (action) {
            case "partial":
                initOrderbook(orderbook);
                break;
            case "update":
                updateOrderbook(orderbook);
                break;
            case "delete":
                deleteOrderbook(orderbook);
                break;
            case "insert":
                insertOrderbook(orderbook);
                break;
            default:
                logger.info("WHATTTTT!! action +> %s", action);
        }
    }

    public void print(int deep) {
        OrderbookEntry[] bids = orderbook.getBids().values().toArray(OrderbookEntry[]::new);
        OrderbookEntry[] asks = orderbook.getAsks().values().toArray(OrderbookEntry[]::new);

        print(
                Arrays.copyOfRange(bids, 0, deep),
                Arrays.copyOfRange(asks, asks.length - deep, asks.length)
        );
    }

    private void print(OrderbookEntry[] bids, OrderbookEntry[] asks) {
        logger.info("");
        logger.info("");
        int bidsDeep = bids.length;
        int asksDeep = asks.length;
        double bidPrice = 0, askPrice =0;
        int bidSize = 0, askSize = 0;
        int bidVolume = 0, askVolume = 0;
        for(int bidI = 0, askI = asksDeep; bidI < bidsDeep || askI > 0; bidI++, askI--) {

            if(bidI + 1 <= bidsDeep) {
                OrderbookEntry bid = bids[bidI];
                bidPrice = bid.getPrice() / 100D;
                bidSize = bid.getSize();
                bidVolume += bid.getSize();
            }
            if(asksDeep - askI >= 0) {
                OrderbookEntry ask = asks[askI - 1];
                askPrice = ask.getPrice()/100D;
                askSize =  ask.getSize();
                askVolume += ask.getSize();
            }

            logger.info(
                    "%2d/%2d\t%.2f\t%,11d\t%,11d\t=>|<=\t%2d/%2d\t%.2f\t%,11d\t%,11d",
                    bidI+1,
                    bidsDeep,
                    bidPrice,
                    bidSize,
                    bidVolume,
                    askI,
                    asksDeep,
                    askPrice,
                    askSize,
                    askVolume
            );
        }
    }

    private void printAsksKeys() {
        this.orderbook
                .getAsks()
                .keySet()
                .stream()
                .forEach(key -> {
                    logger.info("==> %d", key);
                });
    }

    private void initOrderbook(Orderbook orderbook) {
        this.inited = true;
        this.orderbook = orderbook;
    }

    private void insertOrderbook(Orderbook orderbook) {
        orderbook.getBids()
                .values()
                .stream()
                .forEach(orderbookEntry -> {
                    this.orderbook.getBids().put(orderbookEntry.getId(), orderbookEntry);
                });
        orderbook.getAsks()
                .values()
                .stream()
                .forEach(orderbookEntry -> {
                    this.orderbook.getAsks().put(orderbookEntry.getId(), orderbookEntry);
                });
    }

    private void updateOrderbook(Orderbook orderbook) {
        orderbook.getBids()
                .values()
                .stream()
                .forEach(orderbookEntry -> {
                    OrderbookEntry actualEntry = this.orderbook.getBids().get(orderbookEntry.getId());
                    actualEntry.setSize( orderbookEntry.getSize() );
                });
        orderbook.getAsks()
                .values()
                .stream()
                .forEach(orderbookEntry -> {
                    OrderbookEntry actualEntry = this.orderbook.getAsks().get(orderbookEntry.getId());
                    actualEntry.setSize( orderbookEntry.getSize() );
                });
    }

    private void deleteOrderbook(Orderbook orderbook) {
        orderbook.getBids()
                .values()
                .stream()
                .forEach(orderbookEntry -> {
                    this.orderbook.getBids().remove(orderbookEntry.getId());
                });
        orderbook.getAsks()
                .values()
                .stream()
                .forEach(orderbookEntry -> {
                    this.orderbook.getAsks().remove(orderbookEntry.getId());
                });
    }

    public boolean isInited() {
        return inited;
    }
}
