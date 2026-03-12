package com.Market.MarketPulse.dto;

import java.math.BigDecimal;

public record AlertResponse(
		String symbol,
		BigDecimal low,
		BigDecimal high
		) {
	
	
}
