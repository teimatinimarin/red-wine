package com.beuwa.redwine.core.services;

import com.beuwa.redwine.core.model.Orderbook;
import com.beuwa.redwine.core.model.OrderbookEntry;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OrderbookService {
    @Inject
    private Logger logger;

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

        print();
    }

    private void print() {
        logger.info("");
        logger.info("");
        OrderbookEntry[] bids = orderbook.getBids().values().toArray(OrderbookEntry[]::new);
        OrderbookEntry[] asks = orderbook.getAsks().values().toArray(OrderbookEntry[]::new);
        int bidsDeep = bids.length;
        int asksDeep = asks.length;
        for(int i = 0; i < asksDeep; i++) {
            OrderbookEntry ask = asks[i];
            logger.info(
                    "%.2f    %d  %,d",
                    ask.getPrice()/100D,
                    ask.getSize(),
                    ask.getSize()
            );
        }
        logger.info("=====>%d - %d<=====", bidsDeep, asksDeep);
        for(int i = 0; i < bidsDeep; i++) {
            OrderbookEntry bid = bids[i];
            logger.info(
                    "%.2f   %d  %,d",
                    bid.getPrice()/100D,
                    bid.getSize(),
                    bid.getSize()
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
}
