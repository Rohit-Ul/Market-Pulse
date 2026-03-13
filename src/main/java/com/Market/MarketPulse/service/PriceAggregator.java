package com.Market.MarketPulse.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.Market.MarketPulse.model.OhlcvBar;
import com.Market.MarketPulse.model.StockInfo;
import com.Market.MarketPulse.repo.PriceBarRepo;

@Service
public class PriceAggregator {
	
	@Autowired
	PriceBarRepo priceBarRepo;
	
	@Autowired 
	UserAlertService alertService;
	
	private final Map<String, List<StockInfo>> tickBuffer =new ConcurrentHashMap<>();

	// Called By Your StockPriceGenerator for every 5sec as Tick Generates New Value
	public void addTick(List<StockInfo> stocks) {
		
		for(int i=0;i<stocks.size();i++) {
			
			tickBuffer.computeIfAbsent(stocks.get(i).getSymbol(), k->new ArrayList<>()).add(stocks.get(i));
		}
	}
	
	// afte 1 Minute it will be pushed to DB 
	@Scheduled(fixedDelay = 60000)
	public void aggregateAndStore() {
		tickBuffer.forEach((symbol,symbolList) -> {
			if(!symbolList.isEmpty()) {
				
				//System.out.println(symbol + " buffer Fetched . Size now: " + symbolList.size());
				OhlcvBar ohlcv = ComputeOHLCV(symbol,symbolList);
				
				priceBarRepo.save(ohlcv);
				System.out.println("Symbol :"+ohlcv.getSymbol()+" | Closing Price :"+ohlcv.getClose());
				
				alertService.CheckUserAlerts(ohlcv.getSymbol(),ohlcv.getClose());
				
				symbolList.clear(); // Reset for next minute
				//System.out.println(symbol + " buffer cleared. Size now: " + symbolList.size());
                
			}
		});
		
	}
	
	public OhlcvBar ComputeOHLCV(String symbol,List<StockInfo> symbolList ) {
		
		symbolList.sort(Comparator.comparing(StockInfo::getTimestamp));
		
		BigDecimal Open = symbolList.get(0).getPrice();
		
		Instant alignedMinute = symbolList.get(0).getTimestamp().toInstant().truncatedTo(ChronoUnit.MINUTES);
		
		symbolList.forEach(tick -> System.out.println("  " + tick.getTimestamp() + " = " + tick.getPrice()));
		
		BigDecimal High = symbolList.stream().map(StockInfo::getPrice).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
		
		BigDecimal Low = symbolList.stream().map(StockInfo::getPrice).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
		
		BigDecimal Close = symbolList.get(symbolList.size()-1).getPrice();
		
		long Volume = symbolList.stream().mapToLong(StockInfo::getVolume).sum();
		
		return new OhlcvBar(symbol,alignedMinute,Open, High, Low,Close, Volume);
	}
	
	
}
