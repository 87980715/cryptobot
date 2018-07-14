package com.cryptobot.cryptobot.Strategy;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by Felipe Díaz on 1/05/2018.
 */
public interface Strategy {

    public boolean sellSignal(HashMap<String , Integer> indicators );
    public boolean buySignal(HashMap<String , Integer> indicators );
}
