package com.Market.MarketPulse.StockSimulateAlgo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class gbmAlgo implements StockPriceSimulate{

    private static final double MU_MIN = 0.05;    // 5% annual drift min
    private static final double MU_MAX = 0.20;    // 20% annual drift max
    private static final double SIGMA_MIN = 0.10; // 10% volatility min
    private static final double SIGMA_MAX = 0.50; // 50% volatility max
    
    private static final double SECONDS_PER_YEAR = 252 * 6.5 * 3600;
    private static final double DT = 1.0 / SECONDS_PER_YEAR;
    
    private BigDecimal currentPrice;
    
	
    BigDecimal multiplyAndRound(BigDecimal value1, BigDecimal value2) {
        return value1.multiply(value2).setScale(2, RoundingMode.HALF_UP);
    }
    
	@Override
	public BigDecimal FindNewPrice(BigDecimal prevPrice) {
		
		currentPrice=prevPrice;
		// 1. PICK RANDOM μ and σ every second
        double mu = MU_MIN + Math.random() * (MU_MAX - MU_MIN);
        double sigma = SIGMA_MIN + Math.random() * (SIGMA_MAX - SIGMA_MIN);
        
        // 2. Standard normal random shock
        double Z = randomNormal();
        
     // 3. GBM formula with random parameters
        double driftTerm = (mu - 0.5 * sigma * sigma) * DT;
        double diffusionTerm = sigma * Math.sqrt(DT) * Z;
        BigDecimal newPrice = currentPrice.multiply(BigDecimal.valueOf(Math.exp(driftTerm + diffusionTerm))).setScale(2, RoundingMode.HALF_UP)   ;
        
     // Update price
        currentPrice = newPrice.max(BigDecimal.valueOf(0.1));
        
        
     // 4. NSE format output
//        BigDecimal change = newPrice.subtract(currentPrice); // Note: this will be ~0
//        
//        BigDecimal pchange = change
//        					  .multiply(BigDecimal.valueOf(100))
//        					  .divide(currentPrice,2,RoundingMode.HALF_UP);
//        
//        long volume = 800000 + (long)(Math.random() * 800000);
//        
//        BigDecimal high = currentPrice.max(newPrice)
//        				  .add(BigDecimal.valueOf(Math.random() * 5));
//        
//        BigDecimal low = currentPrice.min(newPrice)
//        				  .subtract(BigDecimal.valueOf(Math.random()*5));
		return newPrice;
	}
	
	private double randomNormal() {
        double u1 = Math.random();
        double u2 = Math.random();
        return Math.sqrt(-2.0 * Math.log(u1)) * Math.cos(2.0 * Math.PI * u2);
    }

	@Override
	public BigDecimal FindChange(BigDecimal prevPrice,BigDecimal newPrice) {
		
		
		BigDecimal change = newPrice.subtract(prevPrice); // Note: this will be ~0
		
		BigDecimal pchange = change
						      .multiply(BigDecimal.valueOf(100))
				              .divide(prevPrice,4,RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
		
		return pchange;
	}

	@Override
	public Integer FinVolume(Integer prevVolume) {
		
		long volume = 800000 + (long)(Math.random() * 800000);
		
		return (int) volume;
	}

	@Override
	public BigDecimal FindHigh(BigDecimal prevPrice,BigDecimal newPrice) {
		
		BigDecimal high = prevPrice.max(newPrice)
				  		  .add(BigDecimal.valueOf(Math.random() * 5)).setScale(2, RoundingMode.HALF_UP);
		return high;
	}

	@Override
	public BigDecimal FindLow(BigDecimal prevPrice,BigDecimal newPrice) {
		
		BigDecimal low = prevPrice.min(newPrice)
						.subtract(BigDecimal.valueOf(Math.random()*5)).setScale(2, RoundingMode.HALF_UP);
			
		return low;
	}

}
