package com.Market.MarketPulse.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.Market.MarketPulse.StockSimulateAlgo.StockPriceSimulate;
import com.Market.MarketPulse.model.StockInfo;
import com.Market.MarketPulse.service.PriceAggregator;

import jakarta.persistence.Id;


@Component
public class StockPriceGenerator {

	@Autowired
	@Qualifier("gbmAlgo")  
	StockPriceSimulate simulate;

	@Autowired 
	PriceAggregator priceAggregator;
	
	private static final Random rand= new Random();
	
	public void SimulateStocks(List<StockInfo> stocks) {
		
		List<StockInfo> stockdata = new ArrayList<>();
		
		for(int i=0;i<stocks.size();i++) {
			
			BigDecimal price =stocks.get(i).getPrice() ;
			

			
			String symbol =stocks.get(i).getSymbol();
			BigDecimal newPrice = simulate.FindNewPrice(price);
			BigDecimal change=simulate.FindChange(price,newPrice);
			Integer volume=simulate.FinVolume(stocks.get(i).getVolume());
			BigDecimal high=simulate.FindHigh(price,newPrice);
			BigDecimal low=simulate.FindLow(price,newPrice);
			ZonedDateTime timestamp=ZonedDateTime.now(ZoneId.of("UTC"));
			
			StockInfo stock= new StockInfo(symbol,newPrice,change,volume,high,low);
			
			System.out.println(stocks.get(i)+"/n");
			stockdata.add(stock);
		}
		
		priceAggregator.addTick(stockdata);
		stockdata.clear();
		System.out.println("/n");
		
		
		
//		stocks.get(i).setPrice(newPrice);
//		
//		stocks.get(i).setChange(simulate.FindChange(price,newPrice));
//		
//		stocks.get(i).setVolume(simulate.FinVolume(stocks.get(i).getVolume()));
//		
//		stocks.get(i).setHigh(simulate.FindHigh(price,newPrice));
//		
//		stocks.get(i).setLow(simulate.FindLow(price,newPrice));
//		
//		ZonedDateTime timestamp =ZonedDateTime.now(ZoneId.of("UTC"));
//		
//		stocks.get(i).setTimestamp(timestamp);
		
	}

	
}
