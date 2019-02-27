package com.beuwa.redwine.core.model;

public class OrderbookEntry {
    private long id;
    private String symbol;
    private String side;
    private int size;
    private long price;

    private OrderbookEntry(long id, String symbol, String side, int size, long price) {
        this.id = id;
        this.symbol = symbol;
        this.side = side;
        this.size = size;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSide() {
        return side;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public long getPrice() {
        return price;
    }

    public static class OrderbookL2Builder {
        private long id;
        private String symbol;
        private String side;
        private int size;
        private long price;

        public OrderbookL2Builder id(long id) {
            this.id = id;
            return this;
        }

        public OrderbookL2Builder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public OrderbookL2Builder side(String side) {
            this.side = side;
            return this;
        }

        public OrderbookL2Builder size(int size) {
            this.size = size;
            return this;
        }

        public OrderbookL2Builder price(long price) {
            this.price = price;
            return this;
        }

        public OrderbookEntry build() {
            return new OrderbookEntry(
                    id,
                    symbol,
                    side,
                    size,
                    price
            );
        }
    }
}
