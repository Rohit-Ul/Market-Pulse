package com.Market.MarketPulse.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.Market.MarketPulse.dto.NotifyAlert;

@Controller
public class AlertController {
	
	@MessageMapping("alerts")
	@SendTo("/topic/alerts")
	public NotifyAlert broadcast(NotifyAlert alert) {
		
		return alert;
	}
}
