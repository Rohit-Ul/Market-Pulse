package com.Market.MarketPulse.StockSimulateAlgo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class gmpAlgo implements StockPriceSimulate{

	private final Random random = new Random();
	private final int SCALE = 2;
	
    BigDecimal multiplyAndRound(BigDecimal value1, BigDecimal value2) {
        return value1.multiply(value2).setScale(2, RoundingMode.HALF_UP);
    }
    
	@Override
	public BigDecimal FindNewPrice(BigDecimal prevPrice) {
		// TODO Auto-generated method stub
		
		BigDecimal factor = new BigDecimal("0.001");
        
        // WRONG - Creates huge precision
        // BigDecimal result = stockPrice.multiply(factor);  // 2.894500000000000000...
        
        // CORRECT - Exactly 4 decimals
        BigDecimal result = multiplyAndRound(prevPrice, factor);
		return result;
	}

	@Override
	public BigDecimal FindChange(BigDecimal prevPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer FinVolume(Integer prevVolume) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal FindHigh(BigDecimal prevPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal FindLow(BigDecimal prevPrice) {
		// TODO Auto-generated method stub
		return null;
	}

}
