package com.Market.MarketPulse.model;

import java.math.BigDecimal;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.lang.String;

@Entity
@Table(name = "ohlcv_bars", indexes = {@Index(columnList = "symbol, timestamp")})
public class OhlcvBar {
	@Override
	public String toString() {
		return "OhlcvBar [Id=" + Id + ", symbol=" + symbol + ", timestamp=" + timestamp + ", open=" + open + ", high="
				+ high + ", low=" + low + ", close=" + close + ", volume=" + volume + "]";
	}

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	Integer Id;
	String symbol;
	Instant timestamp;
	BigDecimal open,high,low,close;
	long volume;
	
	public OhlcvBar(String symbol, Instant timestamp, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, long volume) {
		
		this.symbol = symbol;
		this.timestamp = timestamp;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
	}
	
	
	public long getId() {
		return Id;
	}
	public void setId(Integer id) {
		this.Id = id;
	}
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
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

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long pvolume) {
		this.volume = volume;
	}


}
