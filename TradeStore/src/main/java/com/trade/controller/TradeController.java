/**
 * 
 */
package com.trade.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trade.Data.DataStore;
import com.trade.exception.InvalidTradeException;
import com.trade.model.Trade;
import com.trade.service.TradeService;

/**
 * @author Sampada
 * 
 */
@RestController
public class TradeController {

	@Autowired
	TradeService tradeService;
	
	@Autowired
	DataStore dataStore;
	
	

	@PostMapping("/trade")
	public ResponseEntity<String> tradeStore(@RequestBody Trade trade) {
		dataStore.setDataCache();
		if (tradeService.isValid(trade)) {
			tradeService.persist(trade);
		} else {			
			throw new InvalidTradeException(trade.getTradeId());
		}
		return ResponseEntity.status(HttpStatus.OK).build();

	}

	@GetMapping("/trade")
	public List<Trade> findAllTrades() {
		//dataStore.setDataCache();
		return tradeService.findAll();
	}

}
