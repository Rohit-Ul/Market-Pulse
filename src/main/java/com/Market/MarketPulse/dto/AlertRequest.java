package com.Market.MarketPulse.dto;

import java.math.BigDecimal;

public record AlertRequest(
		Integer userId,
		String symbol,
		BigDecimal low,
		BigDecimal high,
		String alertType
		) {

}
