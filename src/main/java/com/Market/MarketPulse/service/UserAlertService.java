package com.Market.MarketPulse.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Market.MarketPulse.dto.AlertRequest;
import com.Market.MarketPulse.dto.AlertResponse;
import com.Market.MarketPulse.model.UserAlerts;
import com.Market.MarketPulse.repo.UserAlertRepo;

@Service
public class UserAlertService {
	
	@Autowired
	UserAlertRepo repo;
	
	public AlertResponse AddUserAlert(AlertRequest alert) {
		
		UserAlerts Req=new UserAlerts();
		
		Req.setUserId(alert.userId());
		Req.setSymbol(alert.symbol());
		Req.setLow(alert.low());
		Req.setHigh(alert.high());
		Req.setStatus("A");
		Req.setCreate_Time(ZonedDateTime.now(ZoneId.of("UTC")));
		
		repo.save(Req);
		
		AlertResponse response = new AlertResponse(alert.symbol(),alert.low(),alert.high());
		return response;
	}
}
