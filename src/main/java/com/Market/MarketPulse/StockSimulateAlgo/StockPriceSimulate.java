package com.Market.MarketPulse.StockSimulateAlgo;

import java.math.BigDecimal;



public interface StockPriceSimulate {
	
	public BigDecimal FindNewPrice(BigDecimal prevPrice);
	public BigDecimal FindChange(BigDecimal prevPrice,BigDecimal newPrice);
	public Integer FinVolume(Integer prevVolume);
	public BigDecimal FindHigh(BigDecimal prevPrice,BigDecimal newPrice);
	public BigDecimal FindLow(BigDecimal prevPrice,BigDecimal newPrice);
	
	
}
