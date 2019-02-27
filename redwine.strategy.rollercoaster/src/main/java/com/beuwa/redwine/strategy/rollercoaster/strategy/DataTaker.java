package com.beuwa.redwine.strategy.rollercoaster.strategy;

import com.beuwa.redwine.core.events.business.TradeEvent;
import com.beuwa.redwine.core.services.DatabaseService;
import com.beuwa.redwine.strategy.rollercoaster.sampler.SamplerEvent;
import com.beuwa.redwine.strategy.rollercoaster.statistics.TickSummary;
import com.beuwa.redwine.strategy.rollercoaster.statistics.WindowMap;
import com.beuwa.redwine.strategy.rollercoaster.utils.DbUtils;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataTaker {
    @Inject
    private Logger logger;

    @Inject
    DatabaseService databaseService;

    @Inject
    private WindowMap buyWindow;

    @Inject
    private WindowMap sellWindow;

    @Inject
    private DbUtils dbUtils;

    private static final long ONE_DAY_WINDOW = 60000 * 60 * 24;
    private static final long FIVE_SECS = 5000;
    private static final long SMALL_WINDOW = 20000;
    private static final long ONE_MIN_WINDOW = 60000;
    private static final long THIRTY_MIN_WINDOW = ONE_MIN_WINDOW * 30;


    private boolean open = false;
    private double openPrice;
    private long lastSignalEpoch;
    private String direction;

    private String openTs;

    private int aboveAvg = 0;

    public void init(@Observes SamplerEvent samplerEvent) throws Exception {
        //writeCsv();
        calculate();
    }

    public void calculate() throws Exception {
        buyWindow.init(ONE_DAY_WINDOW);
        sellWindow.init(ONE_DAY_WINDOW);
        logger.info("Init DataTaker...");

        String txtFileName = "data/redwine.csv";
        try (Stream<String> stream = Files.lines(Paths.get(txtFileName))) {
            stream
                    .skip(1)
                    .peek(line -> calculateMinMax(line))
                    .count();
            logger.info("Above Avg %d", aboveAvg);
        }
    }

    double maxWhileOpen;
    double minWhileOpen;
    private void calculateMinMax(String line) {
        String[] values = line.split(",");
        //side,grp,ts,epoch,price,size,homenotional
        String side = values[0];
        long grp = Long.parseLong(values[1]);
        String ts = values[2];
        long epoch = Long.parseLong(values[3]);
        double price = Double.parseDouble(values[4]);
        long size = Long.parseLong(values[5]);
        double homenotional = Double.parseDouble(values[6]);

        TradeEvent trade = new TradeEvent.TradeEventBuilder()
                .epoch(epoch)
                .price(price)
                .size(size)
                .homenotional(homenotional)
                .build();

        if ("Buy".compareTo(side) == 0) {
            buyWindow.put(epoch, trade);
        }

        if ("Sell".compareTo(side) == 0) {
            sellWindow.put(epoch, trade);
        }

        if (buyWindow.isInitialized() || sellWindow.isInitialized()) {
            List<TickSummary> buyWindow1M = buyWindow.retrieveWindow(epoch, ONE_MIN_WINDOW);
            List<TickSummary> buyWindow5S = buyWindow.retrieveWindow(epoch, FIVE_SECS);
            long sizeBuyWindow = sumSize(buyWindow1M);
            long sizeBuyWindown5s = sumSize(buyWindow5S);
            long sizeBuyTotal = sumSize(buyWindow.values());
            long sizeBuyOneDayAverage = sizeBuyTotal / (ONE_DAY_WINDOW / ONE_MIN_WINDOW);

            List<TickSummary> sellWindow1M = sellWindow.retrieveWindow(epoch, ONE_MIN_WINDOW);
            List<TickSummary> sellWindow5S = sellWindow.retrieveWindow(epoch, FIVE_SECS);
            long sizeSellWindow = sumSize(sellWindow1M);
            long sizeSellWindown5s = sumSize(sellWindow5S);
            long sizeSellTotal = sumSize(sellWindow.values());
            long sizeSellOneDayAverage = sizeSellTotal / (ONE_DAY_WINDOW / ONE_MIN_WINDOW);

            if (sizeBuyWindow >= sizeBuyOneDayAverage || sizeSellWindow >= sizeSellOneDayAverage) {


                if (!open) {
                    logger.info("");
                    if (sizeBuyWindow >= sizeBuyOneDayAverage) {
                        direction = "UP";
                    } else if (sizeSellWindow >= sizeSellOneDayAverage) {
                        direction = "DOWN";
                    }
                    aboveAvg++;
                    //open = true;
                    openPrice = price;
                    openTs = ts;

                    double windowForce;
                    double windowForce5s;
                    if ("UP".compareTo(direction) == 0) {
                        windowForce = (double) sizeBuyWindow / (double) sizeSellWindow;
                        windowForce5s = (double) sizeBuyWindown5s / (double) sizeSellWindown5s;
                    } else {
                        windowForce = (double) sizeSellWindow / (double) sizeBuyWindow;
                        windowForce5s = (double) sizeSellWindown5s / (double) sizeBuyWindown5s;
                    }

                    logger.info("Force %.2f, Momentum %.2f", windowForce, windowForce5s);

                    //logger.debug("====Buy===");
                    //printTickSumarries(buyWindow5S);
                    //logger.debug("count %d", countTrades(buyWindow5S));

                    //logger.debug("===Sell===");
                    //printTickSumarries(sellWindow5S);
                    //logger.debug("count %d", countTrades(sellWindow5S));

                    logger.info("Buy: 24Hrs %,d, 24Avg %,d, WinAvg %,d, SmallWinAvg %,d, Price %,.2f",
                                sizeBuyTotal, sizeBuyOneDayAverage, sizeBuyWindow, sizeBuyWindown5s, price);
                    logger.info("Sell: 24Hrs %,d, 24Avg %,d, WinAvg %,d, SmallWinAvg %,d, Price %,.2f",
                                sizeSellTotal, sizeSellOneDayAverage, sizeSellWindow, sizeSellWindown5s, price);
                }
                lastSignalEpoch = epoch;
            }


            if(open) {
                if(price > maxWhileOpen) {
                    maxWhileOpen = price;
                }
                if(price < minWhileOpen) {
                    minWhileOpen = price;
                }
            }


            //if (open && (epoch - lastSignalEpoch) >= 60000) {
            long target = Math.round( openPrice * 3 / 1000 );
            long stoploss = Math.round( openPrice * 8 / 1000 );
            if (open) {
                if ("UP".compareTo(direction) == 0) {
                    if(price >= (openPrice + target)) {
                        logger.info("MaxWhileOpen %.2f, MinWhileOpen %.2f", maxWhileOpen, minWhileOpen);
                        logger.info("Closed Buy with Profit %s to %s, Open, %,.2f\",  Close, %,.2f Diff, %,.2f", openTs, ts, openPrice, price, (price - openPrice));
                        open = false;
                    } else if(price <= (openPrice - stoploss)) {
                        logger.info("MaxWhileOpen %.2f, MinWhileOpen %.2f", maxWhileOpen, minWhileOpen);
                        logger.info("Closed Buy with Loss %s to %s, Open, %,.2f\",  Close, %,.2f Diff, %,.2f", openTs, ts, openPrice, price, (price - openPrice));
                        open = false;
                    }
                } else {
                    if(price <= (openPrice - target))  {
                        logger.info("MaxWhileOpen %.2f, MinWhileOpen %.2f", maxWhileOpen, minWhileOpen);
                        logger.info("Closed Sell with Profit %s to %s, Open, %,.2f\"  Close, %,.2f, Diff, %,.2f", openTs, ts, openPrice, price, (openPrice - price));
                        open = false;
                    } else if(price >= (openPrice + stoploss)) {
                        logger.info("MaxWhileOpen %.2f, MinWhileOpen %.2f", maxWhileOpen, minWhileOpen);
                        logger.info("Closed Sell with Loss %s to %s, Open, %,.2f\"  Close, %,.2f, Diff, %,.2f", openTs, ts, openPrice, price, (openPrice - price));
                        open = false;
                    }
                }
            }
        }
    }

    private long sumSize(Collection<TickSummary> tickSummaries) {
        return tickSummaries.stream()
                .mapToLong(value -> value.getVolume())
                .sum();
    }

    private long sumHomenotional(Collection<TickSummary> tickSummaries) {
        return tickSummaries.stream()
                .mapToLong(value -> value.getVolume())
                .sum();
    }

    public void writeCsv() throws SQLException {
        logger.info(
                "%s,%s,%s,%s,%s,%s,%s",
                "side",
                "grp",
                "ts",
                "epoch",
                "price",
                "size",
                "homenotional"
        );

        Connection connection = databaseService.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String side = resultSet.getString("side");
                    long grp = resultSet.getLong("grp");
                    String ts = resultSet.getString("ts");
                    long epoch = resultSet.getLong("epoch");
                    long size = resultSet.getLong("size");
                    double price = resultSet.getDouble("price");
                    double homenotional = resultSet.getDouble("homenotional");

                    logger.info(
                            "%s,%d,%s,%d,%.2f,%d,%.8f",
                            side,
                            grp,
                            ts,
                            epoch,
                            price,
                            size,
                            homenotional
                    );
                }
            }
        }
    }

    private void printTickSumarries(List<TickSummary> tickSumaries) {
        tickSumaries.stream()
                .forEach(tickSumarry -> {
                    printTickSummary(tickSumarry);
                });
    }

    private void printTickSummary(TickSummary tickSummary) {
        logger.debug("Tick at %d has %d trades.", tickSummary.getEpoch(), tickSummary.getCount());
        tickSummary.getEvents().stream()
                .forEach(trade -> {
                    logger.debug("   %,.2f", trade.getPrice());
                });
    }

    private DoubleSummaryStatistics generateStats(List<TickSummary> tickSummaries) {
        return tickSummaries.stream()
                .flatMap(tickSummary -> {
                    return tickSummary.getEvents().stream();
                })
                .collect(Collectors.toList())
                .stream()
                .mapToDouble(tradeEvent -> {
                    return tradeEvent.getPrice();
                })
                .summaryStatistics();
    }

    private long countTrades(List<TickSummary> tickSummaries) {
        return tickSummaries.stream()
                .flatMap(tickSummary -> {
                    return tickSummary.getEvents().stream();
                })
                .collect(Collectors.toList())
                .stream()
                .count();
    }

    private String QUERY = "" +
            "WITH trade_explosion AS (\n" +
            "\tSELECT\n" +
            "\t\trow_number() over(ORDER BY epoch, idx) as rn,\n" +
            "\t\ttrade.epoch,\n" +
            "\t\ttrade_data.idx,\n" +
            "\t\ttrade_data.data->>'side' AS side,\n" +
            "\t\t(trade_data.data->>'size')::int AS size,\n" +
            "\t\t(trade_data.data->>'price')::numeric(12,2) AS price,\n" +
            "\t\t(trade_data.data->>'homeNotional')::numeric(16,8) AS homenotional\n" +
            "\tFROM\n" +
            "\t\tredwine.trades AS trade,\n" +
            "\t\tjsonb_array_elements(message->'data') WITH ORDINALITY AS trade_data(data, idx)\n" +
            "\tORDER BY epoch, idx\n" +
            "),\n" +
            "trade_buy_price_changed AS (\n" +
            "\tSELECT\n" +
            "\t\trn,\n" +
            "\t\tepoch,\n" +
            "\t\tidx,\n" +
            "\t\tside,\n" +
            "\t\tsize,\n" +
            "\t\tprice,\n" +
            "\t\thomenotional,\n" +
            "\t\t(CASE WHEN price = lag(price, 1) over(ORDER BY rn) THEN 0 ELSE 1 END) AS changed\n" +
            "\tFROM trade_explosion\n" +
            "\tWHERE side = 'Buy'\n" +
            "),\n" +
            "trade_sell_price_changed AS (\n" +
            "\tSELECT\n" +
            "\t\trn,\n" +
            "\t\tepoch,\n" +
            "\t\tidx,\n" +
            "\t\tside,\n" +
            "\t\tsize,\n" +
            "\t\tprice,\n" +
            "\t\thomenotional,\n" +
            "\t\t(CASE WHEN price = lag(price, 1) over(ORDER BY rn) THEN 0 ELSE 1 END) AS changed\n" +
            "\tFROM trade_explosion\n" +
            "\tWHERE side = 'Sell'\n" +
            "),\n" +
            "trade_buy_price_grp AS (\n" +
            "\tSELECT\n" +
            "\t\t*,\n" +
            "\t\tsum(changed) over (ORDER BY rn) as grp\n" +
            "\tFROM trade_buy_price_changed\n" +
            "),\n" +
            "trade_sell_price_grp AS (\n" +
            "\tSELECT\n" +
            "\t\t*,\n" +
            "\t\tsum(changed) over (ORDER BY rn) as grp\n" +
            "\tFROM trade_sell_price_changed\n" +
            "), trade_buy_grouped_by_price_grp AS (                                                         \n" +
            "\tSELECT\n" +
            "\t\tgrp,\n" +
            "\t\tTO_CHAR(TO_TIMESTAMP(min(epoch)::double precision/1000) AT TIME ZONE 'UTC', 'DD/MM/YYYY HH24:MI:SS.MS') as ts,\n" +
            "\t\tmin(epoch) AS epoch,\n" +
            "\t\tprice,\n" +
            "\t\tsum(size) as size,\n" +
            "\t\tsum(homenotional) AS homenotional\n" +
            "\tFROM trade_buy_price_grp\n" +
            "\tGROUP BY grp, price\n" +
            "\tORDER BY grp\n" +
            "), trade_sell_grouped_by_price_grp AS (\n" +
            "\tSELECT\n" +
            "\t\tgrp,\n" +
            "\t\tTO_CHAR(TO_TIMESTAMP(min(" +
            "epoch)::double precision/1000) AT TIME ZONE 'UTC', 'DD/MM/YYYY HH24:MI:SS.MS') as ts,\n" +
            "\t\tmin(epoch) AS epoch,\n" +
            "\t\tprice,\n" +
            "\t\tsum(size) as size,\n" +
            "\t\tsum(homenotional) AS homenotional\n" +
            "\tFROM trade_sell_price_grp\n" +
            "\tGROUP BY grp, price\n" +
            "\tORDER BY grp\n" +
            ")\n" +
            "SELECT\n" +
            "\t'Buy' as side,\n" +
            "\t*\n" +
            "FROM trade_buy_grouped_by_price_grp\n" +
            "union all\n" +
            "SELECT\n" +
            "\t'Sell' as side,\n" +
            "\t*\n" +
            "FROM trade_sell_grouped_by_price_grp\n" +
            "ORDER BY epoch, grp;";
}
