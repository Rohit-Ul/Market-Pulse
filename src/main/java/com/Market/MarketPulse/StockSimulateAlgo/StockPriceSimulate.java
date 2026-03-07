package com.Market.MarketPulse.StockSimulateAlgo;

import java.math.BigDecimal;



public interface StockPriceSimulate {
	
	public BigDecimal FindNewPrice(BigDecimal prevPrice);
	public BigDecimal FindChange(BigDecimal prevPrice);
	public Integer FinVolume(Integer prevVolume);
	public BigDecimal FindHigh(BigDecimal prevPrice);
	public BigDecimal FindLow(BigDecimal prevPrice);
	
	
}
