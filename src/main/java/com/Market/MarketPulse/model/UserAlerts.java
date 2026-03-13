package com.Market.MarketPulse.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserAlerts {
	@Id
	@GeneratedValue(strategy =GenerationType.UUID)
	UUID id;

	Integer userId;
	
	String symbol;
	
	BigDecimal High;
	
	BigDecimal Low;
	
	String alertType;
	
	String status;
	
	ZonedDateTime create_Time;

	public UserAlerts(UUID id, Integer userId, String symbol, BigDecimal high, BigDecimal low,String alertType, String status,
			ZonedDateTime create_Time) {
		this.id = id;
		this.userId = userId;
		this.symbol = symbol;
		this.High = high;
		this.Low = low;
		this.alertType= alertType;
		this.status = status;
		this.create_Time = create_Time;
	}
	
	public UserAlerts() {
		
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getHigh() {
		return High;
	}

	public void setHigh(BigDecimal high) {
		this.High = high;
	}

	public BigDecimal getLow() {
		return Low;
	}

	public void setLow(BigDecimal low) {
		this.Low = low;
	}
	
	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ZonedDateTime getCreate_Time() {
		return create_Time;
	}

	public void setCreate_Time(ZonedDateTime create_Time) {
		this.create_Time = create_Time;
	}
	
}
