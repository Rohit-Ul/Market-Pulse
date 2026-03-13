package com.Market.MarketPulse.dto;

import java.util.List;

public record NotifyAlert(
		Integer userId,
		AlertResponse Response
		) {

}
