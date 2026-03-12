package com.Market.MarketPulse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Market.MarketPulse.dto.AlertRequest;
import com.Market.MarketPulse.dto.AlertResponse;
import com.Market.MarketPulse.model.UserAlerts;
import com.Market.MarketPulse.service.UserAlertService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserAlertService Alertservice;
	
	@PostMapping("/addAlert")
	public ResponseEntity<AlertResponse> AddUserAlerts(@RequestBody AlertRequest alert) {
		AlertResponse Resposnse=Alertservice.AddUserAlert(alert);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(Resposnse);
	}
}
