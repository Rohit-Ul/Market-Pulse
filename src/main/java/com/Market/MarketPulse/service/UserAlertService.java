package com.Market.MarketPulse.service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Market.MarketPulse.dto.AlertRequest;
import com.Market.MarketPulse.dto.AlertResponse;
import com.Market.MarketPulse.dto.NotifyAlert;
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
		Req.setAlertType(alert.alertType());
		Req.setStatus("A");
		Req.setCreate_Time(ZonedDateTime.now(ZoneId.of("UTC")));
		
		repo.save(Req);
		
		AlertResponse response = new AlertResponse(alert.symbol(),alert.low(),alert.high(),alert.alertType());
		return response;
	}
	
	
	// List of Users 
	public List<NotifyAlert> CheckUserAlerts(String symbol ,BigDecimal curPrice) {
		
		List<UserAlerts> Alerts = repo.FindTriggeredAlerts(symbol, curPrice);
		
		List<NotifyAlert> Notify= new ArrayList<>();
		for(int i=0;i<Alerts.size();i++) {
			
			
			AlertResponse alert = new AlertResponse(
						Alerts.get(i).getSymbol(),
						Alerts.get(i).getLow(),
						Alerts.get(i).getHigh(),
						Alerts.get(i).getAlertType()
					);
			NotifyAlert Notice= new NotifyAlert(Alerts.get(i).getUserId(),alert);
			
			Notify.add(Notice);
		}
		Alerts.clear();
		System.out.println("Alerts Triggered ⏰⏰--"+Notify);
		return Notify;
	}
}
