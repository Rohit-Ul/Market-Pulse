package com.Market.MarketPulse.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.Market.MarketPulse.StockSimulateAlgo.StockPriceSimulate;
import com.Market.MarketPulse.model.StockInfo;

import jakarta.persistence.Id;


@Component
public class StockPriceGenerator {

	@Autowired
	@Qualifier("gbmAlgo")  
	StockPriceSimulate simulate;
	
	private static final Random rand= new Random();
	
	public void ChangePrice(List<StockInfo> stocks) {
		
		
		for(int i=0;i<stocks.size();i++) {
			
			BigDecimal price =stocks.get(i).getPrice() ;
			BigDecimal newPrice = simulate.FindNewPrice(price);
			stocks.get(i).setPrice(newPrice);
			
			stocks.get(i).setChange(simulate.FindChange(price,newPrice));
			
			stocks.get(i).setVolume(simulate.FinVolume(stocks.get(i).getVolume()));
			
			stocks.get(i).setHigh(simulate.FindHigh(price,newPrice));
			
			stocks.get(i).setLow(simulate.FindLow(price,newPrice));
			
			String timestamp =ZonedDateTime.now(ZoneId.of("UTC")).toString();
			stocks.get(i).setTimestamp(timestamp);
			
			System.out.println(stocks.get(i)+"/n");
			
		}
		System.out.println("/n");
		
	}
}
