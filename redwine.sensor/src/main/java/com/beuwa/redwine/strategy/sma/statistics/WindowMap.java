package com.beuwa.redwine.strategy.sma.statistics;

import com.beuwa.redwine.core.config.PropertiesFacade;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
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

    private boolean gathered = false;
    private boolean warmed = false;

    private long smaCurrent;
    private long smaMax;
    private long smaMin;

    private long smaPeriod;
    private long warmupPeriod;

    // I don't extend TreeMap, cause I don't wanna expose all the methods TreeMap has
    private NavigableMap<Long, Long> values = new TreeMap<>();
    private NavigableMap<Long, Long> smas = new TreeMap<>();

    @PostConstruct
    public void init() {
        smaPeriod = propertiesFacade.getRedwineSmaPeriod();
        warmupPeriod = propertiesFacade.getRedwineWarmupPeriod();
    }

    public boolean put(Long epoch, Long price) {
        boolean calculated = false;

        // Add new price
        values.put(epoch, price);

        // Purge old prices from values
        while (values.firstKey().longValue() < epoch.longValue() - smaPeriod) {
            values.remove(values.firstKey());

            if(!gathered) {
                gathered = true;
                logger.info("Gathering data to start SMA calculation COMPLETED!");
            }
        }

        if(gathered) {
            // Calculate statics and send event
            smaCurrent = (long) values.values().stream().mapToLong(l -> l.longValue()).average().getAsDouble();
            smas.put(epoch, smaCurrent);

            logger.debug("key: {}, epoch: {}, period: {}, result: {}", values.firstKey().longValue(),  epoch.longValue(), warmupPeriod, (values.firstKey().longValue() < epoch.longValue() - warmupPeriod));
            while (smas.firstKey().longValue() < epoch.longValue() - warmupPeriod) {
                smas.remove(smas.firstKey());

                if (!warmed) {
                    warmed = true;
                    logger.info("Warming up COMPLETED!");
                }
            }

            LongSummaryStatistics statics = smas.values().stream().collect( Collectors.summarizingLong(value -> value) );
            smaMax = statics.getMax();
            smaMin = statics.getMin();
        }

        if (warmed) {
            calculated = true;
        } else if (!gathered) {
            logger.info("Gathering data to start SMA calculation...");
        } else {
            logger.info("Warming up...");
        }

        return calculated;
    }

    public String getLastTickTimestamp() {
        Instant instant = Instant.ofEpochMilli ( values.lastKey() );
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedTime(FormatStyle.FULL)
                .withLocale(Locale.US)
                .withZone(ZoneOffset.UTC);

        return formatter.format(instant);
    }

    public long getSmaCurrent() {
        return smaCurrent;
    }

    public long getPriceCurrent() {
        return values.lastEntry().getValue();
    }

    public long getSmaMax() {
        return smaMax;
    }

    public long getSmaMin() {
        return smaMin;
    }

    public long size() {
        return smas.size();
    }
}
