package com.Market.MarketPulse.model;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/*
 {
		  "symbol": "RELIANCE",
		  "price": 2894.50,
		  "change": 45.20,
		  "pchange": 1.58,
		  "volume": 1254300,
		  "high": 2910.00,
		  "low": 2850.00,
		  "timestamp": "2026-03-01T14:30:15Z"
}
		
 */

public class StockInfo {
	
	public StockInfo(String symbol, BigDecimal price, BigDecimal change, Integer volume, BigDecimal high,BigDecimal low) {
		this.id = 1;
		this.symbol = symbol;
		this.price = price;
		this.change = change;
		this.volume = volume;
		this.high = high;
		this.low = low;
		this.timestamp = ZonedDateTime.now(ZoneId.of("UTC"));
	}

	@Id
	Integer id;
	String symbol;
	BigDecimal price;
	BigDecimal change;
	Integer volume;
	BigDecimal high;
	BigDecimal low;
	ZonedDateTime timestamp;
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getChange() {
		return change;
	}
	public void setChange(BigDecimal change) {
		this.change = change;
	}
	public Integer getVolume() {
		return volume;
	}
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	public BigDecimal getHigh() {
		return high;
	}
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	public BigDecimal getLow() {
		return low;
	}
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	public ZonedDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return "StockInfo [symbol=" + symbol + ", price=" + price + ", change=" + change + ", volume=" + volume
				+ ", high=" + high + ", low=" + low + ", timestamp=" + timestamp + "]";
	}
}
