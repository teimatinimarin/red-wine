package com.beuwa.redwine.strategy.sma.statistics;

import com.beuwa.redwine.core.config.PropertiesFacade;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;

public class WindowMap {
    @Inject
    private Logger logger;

    @Inject
    private PropertiesFacade propertiesFacade;

    private boolean warmed = false;

    private long sma;
    private long max;
    private long min;

    private static final long TIME = 300000; // 5 Mins

    private NavigableMap<Long, Long> values = new TreeMap<>();

    public boolean put(Long epoch, Long price) {
        boolean calculated = false;

        if(price > 0L) {
            // Add new price
            values.put(epoch, price);

            // Purge old prices
            while (values.firstKey().longValue() < epoch.longValue() - TIME) {
                values.remove(values.firstKey());
                warmed = true;
            }

            if (warmed) {
                // Calculate statics and send event
                LongSummaryStatistics statics = values.values().stream().collect(Collectors.summarizingLong(value -> value));
                sma = (long) statics.getAverage();
                max = statics.getMax();
                min = statics.getMin();

                calculated = true;
            } else {
                logger.info("Warming up...");
            }
        }

        return calculated;
    }

    public String getDateString() {
        Instant instant = Instant.ofEpochMilli ( values.lastKey() );
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.US)
                .withZone(ZoneOffset.UTC);

        return formatter.format(instant);
    }

    public long getSma() {
        return sma;
    }

    public long getCurrent() {
        return values.lastEntry().getValue();
    }

    public long getMax() {
        return max;
    }

    public long getMin() {
        return min;
    }

    public long size() {
        return values.size();
    }
}
