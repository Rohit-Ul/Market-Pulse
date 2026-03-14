package com.Market.MarketPulse.dto;

import com.Market.MarketPulse.model.OhlcvBar;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OhlcvWebSocketDto {
    public long timestamp;
    public String symbol;
    public double o, h, l, c;
    public long v;
    
    public OhlcvWebSocketDto(OhlcvBar bar) {
        this.timestamp = bar.getTimestamp().toEpochMilli();
        this.symbol = bar.getSymbol();
        this.o = bar.getOpen().doubleValue();
        this.h = bar.getHigh().doubleValue();
        this.l = bar.getLow().doubleValue();
        this.c = bar.getClose().doubleValue();
        this.v = bar.getVolume();
    }
}
