package com.cryptobot.cryptobot.service.strategy;

import com.cryptobot.cryptobot.DTO.BittrexCandle;
import com.cryptobot.cryptobot.DTO.BittrexCandleResponse;
import com.cryptobot.cryptobot.DTO.Candle;
import com.cryptobot.cryptobot.DTO.PoloniexCandle;
import com.cryptobot.cryptobot.exceptions.TradeException;
import com.cryptobot.cryptobot.service.exchange.ExchangeService;
import com.cryptobot.cryptobot.service.indicators.IndicatorsService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Felipe Díaz on 1/05/2018.
 */

@Slf4j
@Component
public class SimpleStrategyImpl implements  Strategy{

    @Autowired
    ExchangeService exchangeService;

    @Autowired
    private IndicatorsService indicatorsService;

    @Override
    public boolean buySignal(HashMap<String, Double> indicators) {


        return ((indicators.get("rsi") < 35 &&
            indicators.get("fastd") < 35 &&
            indicators.get("adx") > 30 &&
            indicators.get("plus_di") > 0.5) ||
                (indicators.get("adx") > 65 && indicators.get("plus_di") > 0.5 ));
    }

    @Override
    public boolean sellSignal(HashMap<String, Double> indicators) {
        return ((indicators.get("adx") > 10 && indicators.get("minus_di") >0) ||
                (indicators.get("adx") > 70 && indicators.get("minus_di") > 0.5));
    }




    public boolean[] applyStrategy(CurrencyPair pair, int interval)  throws TradeException {
        try{

            TimeSeries timeSeries =  exchangeService.getTickerHistory(pair, interval);
            HashMap<String, Double> indicators =  indicatorsService.calculateIndicators(timeSeries);

            boolean buySignal = this.buySignal(indicators);
            log.debug("buy signal " + buySignal);

            boolean sellSignal  =  this.sellSignal(indicators);
            boolean a[] = {buySignal, sellSignal};
            return  a;
        }catch (Exception e){
            throw new TradeException(e.toString(), e);
        }

    }
}
