package com.pmt.mattress;

import java.util.HashMap;
import java.util.Map.Entry;

public class OuterCover {
	private String name;
	private HashMap<MattressSize, Double> coverPrices = null;
	OuterCover(String name) {
		this.name = name;
		DBAccess dba = new DBAccess();
		coverPrices = dba.getOutercoverPrice(name);
	}
	
	public void showPrice() {
		for(Entry<MattressSize, Double> entry : coverPrices.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
	
	public HashMap<MattressSize, Double> getCoverPrice() {
		return coverPrices;
	}
	
	public String getName() {
		return name;
	}
}
