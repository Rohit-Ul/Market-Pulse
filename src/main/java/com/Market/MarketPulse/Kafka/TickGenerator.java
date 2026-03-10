package com.Market.MarketPulse.Kafka;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.Market.MarketPulse.Component.StockPriceGenerator;
import com.Market.MarketPulse.model.StockInfo;

@Service
public class TickGenerator {

	@Autowired
	StockPriceGenerator PriceGenerator;
	
	//@Autowired 
   //KafkaTemplate<StockInfo,String> kafkaTemplate;
	
	List<StockInfo> StockList = List.of(
			new StockInfo(
			        "ITC", 
			        new BigDecimal("445.75"),  // ✅ String = exact decimal
			        new BigDecimal("2.35"),
			        52345678,                  // ✅ Autoboxing
			        new BigDecimal("448.20"),
			        new BigDecimal("442.10")
			    )
//			,
//           
//			// ✅ Corrected versions:
//		    new StockInfo("HDFCBANK", 
//		    			new BigDecimal("1567.50"), 
//		    		    new BigDecimal("18.20"), 
//		    		    28456789, 
//		    		    new BigDecimal("1582.00"), 
//		    		    new BigDecimal("1545.30"))
        );
	
//	public void GenerateTickPrice(String stock, StockInfo info) {
//		kafkaTemplate.send("test_topic","Hello Kafka");
//	}
	
	@Scheduled(fixedRate = 5000)
	public void PriceChange() {
		
		PriceGenerator.SimulateStocks(StockList);
	}
	
}
