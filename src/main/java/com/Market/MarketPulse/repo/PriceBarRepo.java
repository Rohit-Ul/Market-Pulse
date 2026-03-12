package com.Market.MarketPulse.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Market.MarketPulse.model.OhlcvBar;

@Repository
public interface PriceBarRepo extends JpaRepository<OhlcvBar,Integer>{

	
}
