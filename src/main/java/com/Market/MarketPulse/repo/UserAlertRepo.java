package com.Market.MarketPulse.repo;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Market.MarketPulse.dto.AlertRequest;
import com.Market.MarketPulse.dto.AlertResponse;
import com.Market.MarketPulse.model.UserAlerts;

@Repository
public interface UserAlertRepo extends JpaRepository<UserAlerts,UUID>{

	@Query("""
		       SELECT p
		       FROM UserAlerts p
		       WHERE p.symbol = :symbol
		       AND (p.Low  >= :curPrice OR p.High  <= :curPrice)
		      
		       """)
	List<UserAlerts> FindTriggeredAlerts(String symbol,BigDecimal curPrice);
	
	
}
