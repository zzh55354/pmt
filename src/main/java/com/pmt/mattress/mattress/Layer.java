package com.pmt.mattress;

import java.util.HashMap;
import java.util.Map.Entry;

public class Layer {
	private String name;
	private double thickness;
	private double bfPrice;
	private HashMap <MattressSize, Double> layerPrices = new HashMap<MattressSize, Double>();
	
	public Layer(double thickness, String name) {
		this.thickness = thickness;
		this.name = name;
		DBAccess dba = new DBAccess();
		bfPrice = dba.getFoamPrice(name);
		putPrice();
	}
	
	public String getName() {
		return name;
	}
	
	public Double getBfPrice() {
		return bfPrice;
	}
	
	public double getThickness() {
		return thickness;
	}
		
	public String toString() {
		return thickness + "\" " + name;
	}
	
	public void putPrice() {
		layerPrices.put(MattressSize.TWIN, (MattressSize.TWIN.getSurface()*thickness/(12*12)*bfPrice));
		layerPrices.put(MattressSize.TWINXL, (MattressSize.TWINXL.getSurface()*thickness/(12*12)*bfPrice));
		layerPrices.put(MattressSize.FULL, (MattressSize.FULL.getSurface()*thickness/(12*12)*bfPrice));
		layerPrices.put(MattressSize.QUEEN, (MattressSize.QUEEN.getSurface()*thickness/(12*12)*bfPrice));
		layerPrices.put(MattressSize.KING, (MattressSize.KING.getSurface()*thickness/(12*12)*bfPrice));
		layerPrices.put(MattressSize.CKING, (MattressSize.CKING.getSurface()*thickness/(12*12)*bfPrice));
	}
	
	public void showPrice() {
		for(Entry<MattressSize, Double> entry : layerPrices.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
	
	public HashMap <MattressSize, Double> getPrice() {
		return layerPrices;
	}
	
	
	
}
