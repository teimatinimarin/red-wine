package com.beuwa.redwine.strategy.rollercoaster.statistics;

import com.beuwa.redwine.core.events.business.TradeEvent;
import com.beuwa.redwine.core.model.Trade;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class WindowMap extends TreeMap<Long, TickSummary> {
    @Inject
    private Logger logger;

    private boolean initialized = false;

    private long window;

    public void init(long window) {
        this.window = window;
    }

    public TickSummary put(Long epoch, Trade trade) {
        TickSummary tickSummary = super.get(epoch);
        if(tickSummary == null) {
            tickSummary = new TickSummary();
        }
        tickSummary.put(epoch, trade);

        super.put(epoch, tickSummary);

        // Purge old prices from values
        while (firstKey().longValue() < epoch.longValue() - window) {
            remove(firstKey());

            if(!initialized) {
                initialized = true;
                logger.info("WindowMap: initialization COMPLETED!");
            }
        }

        if (!initialized) {
            logger.debug("WindowMap: initialization IN PROGRESS.");
        }

        return tickSummary;
    }

    public List<TickSummary> retrieveWindow(long current, long window) {
        List<TickSummary> tickSummaries = descendingMap().entrySet().stream()
                .filter(entry -> entry.getValue().getEpoch() >= current - window)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        return tickSummaries;
    }

    public boolean isInitialized() {
        return initialized;
    }
}
