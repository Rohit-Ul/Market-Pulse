package com.Market.MarketPulse.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Market.MarketPulse.model.UserAlerts;

@Repository
public interface UserAlertRepo extends JpaRepository<UserAlerts,UUID>{
	
	
}
