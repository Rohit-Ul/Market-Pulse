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
			,
//           
			// ✅ Corrected versions:
		    new StockInfo("HDFCBANK", 
		    			new BigDecimal("1567.50"), 
		    		    new BigDecimal("18.20"), 
		    		    28456789, 
		    		    new BigDecimal("1582.00"), 
		    		    new BigDecimal("1545.30"))
		    ,
		 // 10 MORE Indian stocks for your 50-stock test
		    new StockInfo("RELIANCE", new BigDecimal("2850.25"), new BigDecimal("32.50"), 45678901, new BigDecimal("2875.00"), new BigDecimal("2820.75")),
		    new StockInfo("TCS", new BigDecimal("4125.80"), new BigDecimal("45.20"), 12345678, new BigDecimal("4150.00"), new BigDecimal("4100.50")),
		    new StockInfo("INFY", new BigDecimal("1820.40"), new BigDecimal("22.10"), 98765432, new BigDecimal("1845.00"), new BigDecimal("1805.25")),
		    new StockInfo("BHARTIARTL", new BigDecimal("1287.60"), new BigDecimal("15.80"), 23456789, new BigDecimal("1302.00"), new BigDecimal("1270.30")),
		    new StockInfo("ICICIBANK", new BigDecimal("1125.90"), new BigDecimal("13.45"), 34567890, new BigDecimal("1140.00"), new BigDecimal("1110.75")),
		    new StockInfo("KOTAKBANK", new BigDecimal("1987.35"), new BigDecimal("24.75"), 45678912, new BigDecimal("2010.00"), new BigDecimal("1965.50")),
		    new StockInfo("HINDUNILVR", new BigDecimal("2560.75"), new BigDecimal("28.90"), 56789012, new BigDecimal("2585.00"), new BigDecimal("2535.25")),
		    new StockInfo("SBIN", new BigDecimal("825.40"), new BigDecimal("9.85"), 67890123, new BigDecimal("840.00"), new BigDecimal("815.20")),
		    new StockInfo("ASIANPAINT", new BigDecimal("3124.60"), new BigDecimal("36.20"), 78901234, new BigDecimal("3150.00"), new BigDecimal("3095.30")),
		    new StockInfo("LT", new BigDecimal("3678.90"), new BigDecimal("42.10"), 89012345, new BigDecimal("3705.00"), new BigDecimal("3650.75")),
		    
		 // 20 MORE NSE stocks (not in your current 12)
		    new StockInfo("SUNPHARMA", new BigDecimal("1658.20"), new BigDecimal("19.50"), 34567890, new BigDecimal("1675.00"), new BigDecimal("1640.50")),
		    new StockInfo("NESTLEIND", new BigDecimal("2479.80"), new BigDecimal("28.20"), 23456789, new BigDecimal("2500.00"), new BigDecimal("2455.30")),
		    new StockInfo("NTPC", new BigDecimal("335.85"), new BigDecimal("4.10"), 45678901, new BigDecimal("340.00"), new BigDecimal("332.50")),
		    new StockInfo("HCLTECH", new BigDecimal("1486.50"), new BigDecimal("17.80"), 56789012, new BigDecimal("1505.00"), new BigDecimal("1470.20")),
		    new StockInfo("WIPRO", new BigDecimal("246.40"), new BigDecimal("2.95"), 67890123, new BigDecimal("250.00"), new BigDecimal("243.50")),
		    new StockInfo("DIVISLAB", new BigDecimal("6121.00"), new BigDecimal("72.50"), 78901234, new BigDecimal("6150.00"), new BigDecimal("6090.75")),
		    new StockInfo("CIPLA", new BigDecimal("1513.20"), new BigDecimal("18.40"), 89012345, new BigDecimal("1535.00"), new BigDecimal("1495.30")),
		    new StockInfo("POWERGRID", new BigDecimal("286.15"), new BigDecimal("3.45"), 12345678, new BigDecimal("290.00"), new BigDecimal("283.20")),
		    new StockInfo("TATAPOWER", new BigDecimal("388.20"), new BigDecimal("4.65"), 23456789, new BigDecimal("395.00"), new BigDecimal("385.00")),
		    new StockInfo("OBEROIRLTY", new BigDecimal("1591.40"), new BigDecimal("19.20"), 34567890, new BigDecimal("1610.00"), new BigDecimal("1575.50")),
		    new StockInfo("HINDZINC", new BigDecimal("515.25"), new BigDecimal("6.20"), 45678901, new BigDecimal("520.00"), new BigDecimal("510.00")),
		    new StockInfo("VEDANTA", new BigDecimal("484.20"), new BigDecimal("5.80"), 56789012, new BigDecimal("490.00"), new BigDecimal("480.00")),
		    new StockInfo("LTIM", new BigDecimal("5434.00"), new BigDecimal("65.20"), 67890123, new BigDecimal("5470.00"), new BigDecimal("5400.00")),
		    new StockInfo("TECHM", new BigDecimal("1466.60"), new BigDecimal("17.50"), 78901234, new BigDecimal("1485.00"), new BigDecimal("1450.00")),
		    new StockInfo("BRITANNIA", new BigDecimal("5812.50"), new BigDecimal("69.50"), 89012345, new BigDecimal("5850.00"), new BigDecimal("5775.00")),
		    new StockInfo("VARUNBEV", new BigDecimal("434.05"), new BigDecimal("5.20"), 90123456, new BigDecimal("440.00"), new BigDecimal("430.00")),
		    new StockInfo("GODREJCP", new BigDecimal("1129.30"), new BigDecimal("13.60"), 12345678, new BigDecimal("1145.00"), new BigDecimal("1115.00")),
		    new StockInfo("DABUR", new BigDecimal("482.80"), new BigDecimal("5.75"), 23456789, new BigDecimal("490.00"), new BigDecimal("478.00")),
		    new StockInfo("ADANIGREEN", new BigDecimal("1051.50"), new BigDecimal("12.60"), 34567890, new BigDecimal("1065.00"), new BigDecimal("1040.00")),
		    new StockInfo("JSWENERGY", new BigDecimal("541.70"), new BigDecimal("6.50"), 45678901, new BigDecimal("550.00"), new BigDecimal("537.00")),
		    
		    
		    new StockInfo("MARUTI", new BigDecimal("12845.20"), new BigDecimal("152.30"), 12345678, new BigDecimal("12950.00"), new BigDecimal("12780.50")),
		    new StockInfo("ULTRACEMCO", new BigDecimal("9876.50"), new BigDecimal("118.20"), 23456789, new BigDecimal("9920.00"), new BigDecimal("9840.30")),
		    new StockInfo("TITAN", new BigDecimal("3456.80"), new BigDecimal("41.20"), 34567890, new BigDecimal("3485.00"), new BigDecimal("3425.75")),
		    new StockInfo("AXISBANK", new BigDecimal("1123.45"), new BigDecimal("13.50"), 45678901, new BigDecimal("1140.00"), new BigDecimal("1108.20")),
		    new StockInfo("BAJFINANCE", new BigDecimal("6890.75"), new BigDecimal("82.10"), 56789012, new BigDecimal("6950.00"), new BigDecimal("6830.50")),
		    new StockInfo("ONGC", new BigDecimal("256.80"), new BigDecimal("3.10"), 67890123, new BigDecimal("262.00"), new BigDecimal("254.20")),
		    new StockInfo("TATASTEEL", new BigDecimal("158.25"), new BigDecimal("1.90"), 78901234, new BigDecimal("162.00"), new BigDecimal("156.00")),
		    new StockInfo("COALINDIA", new BigDecimal("425.60"), new BigDecimal("5.10"), 89012345, new BigDecimal("435.00"), new BigDecimal("420.50")),
		    new StockInfo("GRASIM", new BigDecimal("2456.90"), new BigDecimal("29.20"), 90123456, new BigDecimal("2480.00"), new BigDecimal("2430.75")),
		    new StockInfo("EICHERMOT", new BigDecimal("4567.30"), new BigDecimal("54.80"), 12345678, new BigDecimal("4620.00"), new BigDecimal("4535.20")),
		    new StockInfo("DRREDDY", new BigDecimal("6123.45"), new BigDecimal("73.20"), 23456789, new BigDecimal("6180.00"), new BigDecimal("6085.30")),
		    new StockInfo("BAJAJFINSV", new BigDecimal("1567.80"), new BigDecimal("18.90"), 34567890, new BigDecimal("1590.00"), new BigDecimal("1545.50")),
		    new StockInfo("HEROMOTOCO", new BigDecimal("4789.20"), new BigDecimal("57.40"), 45678901, new BigDecimal("4850.00"), new BigDecimal("4750.75")),
		    new StockInfo("SHRIRAMFIN", new BigDecimal("2458.60"), new BigDecimal("29.50"), 56789012, new BigDecimal("2485.00"), new BigDecimal("2430.20")),
		    new StockInfo("TATAMOTORS", new BigDecimal("856.40"), new BigDecimal("10.20"), 67890123, new BigDecimal("870.00"), new BigDecimal("845.30")),
		    new StockInfo("ADANIPORTS", new BigDecimal("1345.75"), new BigDecimal("16.10"), 78901234, new BigDecimal("1370.00"), new BigDecimal("1325.50")),
		    new StockInfo("INDUSINDBK", new BigDecimal("1423.80"), new BigDecimal("17.20"), 89012345, new BigDecimal("1450.00"), new BigDecimal("1400.25")),
		    new StockInfo("APOLLOHOSP", new BigDecimal("5987.50"), new BigDecimal("71.80"), 90123456, new BigDecimal("6050.00"), new BigDecimal("5950.30"))
        );
	
//	public void GenerateTickPrice(String stock, StockInfo info) {
//		kafkaTemplate.send("test_topic","Hello Kafka");
//	}
	
	@Scheduled(fixedRate = 5000)
	public void PriceChange() {
		
		PriceGenerator.SimulateStocks(StockList);
	}
	
}
