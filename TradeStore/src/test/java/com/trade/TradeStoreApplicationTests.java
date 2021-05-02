package com.trade;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trade.controller.TradeController;
import com.trade.exception.InvalidTradeException;
import com.trade.model.Trade;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
class TradeStoreApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private TradeController tradeController;

	// Update trade if version is same or greater
	@Test
	public void testtradeStoreSuccessful() {
		ResponseEntity<String> responseEntity = tradeController.tradeStore(
				new Trade("T2", 1, "CP-1", "B2", LocalDate.of(2022, 05, 20), LocalDate.of(2021, 05, 02), "N"));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(), responseEntity);		
	}

	// Test - Store should not allow the trade which has less maturity date then
	// today date.
	@Test
	public void testTradeStoreWhenMaturityDatePast() {
		try {
			ResponseEntity<String> responseEntity = tradeController.tradeStore(
					new Trade("T1", 1, "CP-1", "B2", LocalDate.of(2015, 05, 21), LocalDate.of(2021, 05, 02), "N"));
		} catch (InvalidTradeException ie) {
			Assertions.assertEquals("Invalid Trade: T1", ie.getMessage());
		}
	}
	//Reject trade if version is smaller 
	@Test
	public void testtradeStoreSmallerVersion() {
		try {
			ResponseEntity<String> responseEntity = tradeController.tradeStore(
					new Trade("T2", 0, "CP-1", "B2", LocalDate.of(2022, 05, 20), LocalDate.of(2021, 05, 02), "N"));
		} catch (InvalidTradeException ie) {
			Assertions.assertEquals("Invalid Trade: T2", ie.getMessage());
		}
	}	
	

}
