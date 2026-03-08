	package com.Market.MarketPulse.StockSimulateAlgo;
	
	import java.math.BigDecimal;
	import java.math.RoundingMode;
	import java.util.Random;
	
	import org.springframework.stereotype.Component;
	
	@Component
	public class simpleAlgo implements StockPriceSimulate{
	
		private final Random random = new Random();
		private final int SCALE = 2;
		
	    BigDecimal multiplyAndRound(BigDecimal value1, BigDecimal value2) {
	        return value1.multiply(value2).setScale(2, RoundingMode.HALF_UP);
	    }
		
		
		public BigDecimal FindNewPrice(BigDecimal prevPrice) {
			
			BigDecimal factor = new BigDecimal("0.001");
	        
	        // WRONG - Creates huge precision
	        // BigDecimal result = stockPrice.multiply(factor);  // 2.894500000000000000...
	        
	        // CORRECT - Exactly 4 decimals
	        BigDecimal result = prevPrice.add(multiplyAndRound(prevPrice, factor));
			return result.setScale(2, RoundingMode.HALF_UP);
		}
	
		public BigDecimal FindChange(BigDecimal prevPrice,BigDecimal newPrice) {
			BigDecimal changeFactor = BigDecimal.valueOf(random.nextDouble() * 0.04 - 0.02);
			return changeFactor.setScale(2, RoundingMode.HALF_UP);
		}
	
		
		public Integer FinVolume(Integer prevVolume) {
			
			return 1000000 + random.nextInt(500);
		}
	
		
		public BigDecimal FindHigh(BigDecimal prevPrice,BigDecimal newPrice) {
			
			return FindNewPrice(prevPrice.add(BigDecimal.valueOf(5 + random.nextDouble() * 10))).setScale(2, RoundingMode.HALF_UP);
		}
	
	
		public BigDecimal FindLow(BigDecimal prevPrice,BigDecimal newPrice) {
			
			return FindNewPrice(prevPrice.subtract(BigDecimal.valueOf(3 + random.nextDouble() * 7))).setScale(2, RoundingMode.HALF_UP);
		}
	
	}
