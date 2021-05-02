package com.trade.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.Data.DataStore;
import com.trade.model.Trade;

/**
 * @author Sampada
 *
 */
@Service
public class TradeService {

	private static final Logger log = LoggerFactory.getLogger(TradeService.class);

	@Autowired
	DataStore tradeDao;

	public boolean isValid(Trade trade) {
		
		log.info("Validation starts");

		if (validateMaturityDate(trade)) {

			HashMap<String, Trade> exsitingTrade = tradeDao.getTradeMap();

			Trade old = exsitingTrade.get(trade.getTradeId());

			if (exsitingTrade.containsKey(trade.getTradeId())) {
				return validateVersion(trade, old);
			} else {
				return true;
			}
		}
		log.info("Validation Ends");
		
		return false;
	}

	/* Validate version if lower reject */
	private boolean validateVersion(Trade trade, Trade oldTrade) {		
		
		if (trade.getVersion() >= oldTrade.getVersion()) {			
			return true;
		}
		log.info("Validation : Version Id is smaller");
		return false;
	}

	/*
	 * Store should not allow the trade which has less maturity date then today date
	 */
	private boolean validateMaturityDate(Trade trade) {
		return trade.getMaturityDate().isBefore(LocalDate.now()) ? false : true;
	}

	
	public void persist(Trade trade) {	
		//trade.setCreatedDate(LocalDate.now());
		tradeDao.save(trade);
	}

	/* return existing trade List */
	public List<Trade> findAll() {
		log.info("Trade : getList call");
		return tradeDao.getTradeList();

	}

	
	
	public void updateExpiryFlagOfTrade() {		
		DataStore dataStore = new DataStore();
		dataStore.setDataCache();
		List<Trade> tradeList = dataStore.getTradeList();
		
		for(Trade trade : tradeList) {
			if(validateMaturityDate(trade)) {
				trade.setExpiredFlag("Y");
			}
		}
	}
	 
	 

}
