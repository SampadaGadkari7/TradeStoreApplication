package com.trade.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trade.model.Trade;

@Service
public class DataStore {

	HashMap<String, Trade> tradeMap = new HashMap<String, Trade>();
	
	List<Trade> tradeList =new ArrayList<Trade>(tradeMap.values());	
	

	public HashMap<String, Trade> getTradeMap() {
		return tradeMap;
	}

	public void setTradeMap(HashMap<String, Trade> tradeMap) {
		this.tradeMap = tradeMap;
	}

	public List<Trade> getTradeList() {
		return tradeList;
	}

	public void setTradeList(List<Trade> tradeList) {
		this.tradeList = tradeList;
	}

	public void setDataCache() {

		Trade obj1 = new Trade("T1", 1, "CP-1", "B1", LocalDate.of(2020, 05, 20), LocalDate.of(2021, 05, 02), "N");
		Trade obj2 = new Trade("T2", 1, "CP-2", "B1", LocalDate.of(2021, 06, 20), LocalDate.of(2021, 05, 02), "N");
		Trade obj3 = new Trade("T3", 3, "CP-3", "B2", LocalDate.of(2014, 05, 20), LocalDate.of(2021, 05, 02), "y");
		// Trade obj5 = new Trade("T1", 1, "CP-1", "B1",LocalDate.of(2020, 05, 20),
		// LocalDate.of(2021, 05, 02), "N");

		tradeMap.put("T1", obj1);
		tradeMap.put("T2", obj2);
		tradeMap.put("T3", obj3);

	}

	public void save(Trade trade) {
		tradeMap.put(trade.getTradeId(), trade);
		
	}

}
