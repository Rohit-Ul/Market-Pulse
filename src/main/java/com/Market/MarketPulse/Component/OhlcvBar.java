package com.Market.MarketPulse.Component;

import java.math.BigDecimal;

import java.time.Instant;

import org.springframework.stereotype.Component;

import java.lang.String;

//POJO Object
public class OhlcvBar {
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

	public void setVolume(long volume) {
		this.volume = volume;
	}
	

	@Override
	public String toString() {
		return "OhlcvBar [symbol=" + symbol + ", timestamp=" + timestamp + ", open=" + open + ", high=" + high
				+ ", low=" + low + ", close=" + close + ", volume=" + volume + "]";
	}
	
}
