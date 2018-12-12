package com.beuwa.redwine.strategy.sma.utils;

import com.beuwa.redwine.core.config.PropertiesFacade;
import com.beuwa.redwine.strategy.sma.statistics.Statistics;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class OrderQuantityCalculator {
    @Inject
    Logger logger;

    @Inject
    private Statistics statistics;

    @Inject
    private PropertiesFacade propertiesFacade;

    public int contractsToInvest() {
        var walletBalance = statistics.getWalletBalance();
        var realisedPNL = statistics.getRealisedPnl();
        var positionMargin = statistics.getPositionMargin();
        var available = (walletBalance + realisedPNL) - positionMargin;

        var maxInvestment = propertiesFacade.getMaxInvest();
        var percentageToInvest = (propertiesFacade.getPercentageToInvest()/100f);

        var satoshisToInvest = Math.min((long)(available * percentageToInvest), maxInvestment);
        var priceCurrent = statistics.getPriceCurrent();
        var contracts = (int)((satoshisToInvest*priceCurrent)/100000000D);

        logger.info(
                "WalletBalance: {}, PositionMargin: {}, Available: {}, MaxInvestment: {}, PercentageToInvest: {}, SatoshiToInvest: {}, Contracts: {}",
                walletBalance,
                positionMargin,
                available,
                maxInvestment,
                percentageToInvest,
                satoshisToInvest,
                contracts
        );

        return contracts;
    }
}

