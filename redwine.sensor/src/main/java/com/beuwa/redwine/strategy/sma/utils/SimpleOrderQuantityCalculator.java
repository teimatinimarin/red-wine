package com.beuwa.redwine.strategy.sma.utils;

import com.beuwa.redwine.core.config.PropertiesFacade;
import com.beuwa.redwine.strategy.sma.statistics.Statistics;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class SimpleOrderQuantityCalculator {
    @Inject
    Logger logger;

    @Inject
    private Statistics statistics;

    @Inject
    private PropertiesFacade propertiesFacade;

    public long satoshisToInvest() {
        var walletBalance = statistics.getWalletBalance();
        var realisedPNL = statistics.getRealisedPnl();
        var positionMargin = statistics.getPositionMargin();
        var available = (walletBalance + realisedPNL) - positionMargin;

        var maxInvestment = propertiesFacade.getMaxInvest();
        var percentageToInvest = (propertiesFacade.getPercentageToInvest()/100f);

        var satoshisToInvest = (long)(available * percentageToInvest);

        logger.info(
                "WalletBalance: {}, PositionMargin: {}, Available: {}, MaxInvestment: {}, PercentageToInvest: {}, SatoshiToInvest: {}",
                walletBalance,
                positionMargin,
                available,
                maxInvestment,
                percentageToInvest,
                satoshisToInvest
        );

        return Math.min(satoshisToInvest, maxInvestment);
    }

    public double bitcoinToInvest() {
        var satoshiToInvest = satoshisToInvest();
        var bitcoinToInvest = satoshiToInvest/100000000D;
        return bitcoinToInvest;
    }
}

