package com.cryptobot.cryptobot.service;

import org.springframework.stereotype.Service;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.StochasticOscillatorDIndicator;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.indicators.adx.PlusDIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.util.HashMap;

@Service
public class IndicatorsServiceImpl  implements  IndicatorsService{
    @Override
    public HashMap<String, Integer> calculateIndicators(TimeSeries timeSeries) {
        HashMap<String, Integer> indicators = new HashMap<>();


        ClosePriceIndicator closePrice = new ClosePriceIndicator(timeSeries);
        int LAST_INDEX = timeSeries.getEndIndex()-1;
        //RSI
        RSIIndicator rsiIndicator  = new RSIIndicator(closePrice, 14);
        int value =  rsiIndicator.getValue(LAST_INDEX).intValue();
        indicators.put( "rsi",value);

        System.out.println("f"+ value + " in " + timeSeries.getEndIndex());

        //ADX
        ADXIndicator adxIndicator = new ADXIndicator(timeSeries, 14);
        value = adxIndicator.getValue( LAST_INDEX).intValue();
        indicators.put( "adx",value);
        System.out.println("adx " + value);

        //PLUS DI
        PlusDIIndicator plusDIIndicator = new PlusDIIndicator(timeSeries, 14);
        value = plusDIIndicator.getValue(LAST_INDEX).intValue();
        indicators.put("plus_di", value);
        System.out.println("plus_di" + value);


        //FASTD
        StochasticOscillatorDIndicator stochasticOscillatorDIndicator = new StochasticOscillatorDIndicator(closePrice);
        value = stochasticOscillatorDIndicator.getValue(LAST_INDEX).intValue();
        indicators.put("fastd", value);
        System.out.println("fast " +value);




        return  indicators;

    }
}
