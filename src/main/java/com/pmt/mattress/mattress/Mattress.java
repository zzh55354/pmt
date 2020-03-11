package com.pmt.mattress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Mattress {
	
	private String name;
	private String ID = "";
	private double height = 0.0;
	private ArrayList <Layer> Layers = new ArrayList<Layer>();
	private OuterCover outerCover = null;
	private HashMap <MattressSize, Double> MattressPrice = new HashMap<MattressSize, Double>();
	public Mattress(String name) {
		this.name = name;
		MattressPriceInit();
	}
	
	public void MattressPriceInit() {
		MattressPrice.put(MattressSize.TWIN, 0.0);
		MattressPrice.put(MattressSize.TWINXL, 0.0);
		MattressPrice.put(MattressSize.FULL, 0.0);
		MattressPrice.put(MattressSize.QUEEN, 0.0);
		MattressPrice.put(MattressSize.KING, 0.0);
		MattressPrice.put(MattressSize.CKING, 0.0);
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	public String getName() {
		return name;
	}
		
	public void addLayer(Layer l ) {
		this.Layers.add(l);
		height += l.getThickness();
	}
	
	public void removeLayer(Layer l) {
		boolean removeStatus = Layers.remove(l);
		if (removeStatus) {
			 System.out.println(l.getThickness() +"\" " + l.getName() + " has been removed");
		}else {
			 System.out.println(l.getThickness() +"\" " + l.getName() + " cannot to be removed");
		}
	}
	
	public double getHeight() {
		return height;
	}
	
	public void printLayers() {
		for(int i = 0; i < Layers.size(); i++) {
			System.out.println(Layers.get(i).toString());
		}
	}
	
	public void addOuterCover(OuterCover OC) {
		outerCover = OC;
	}
	
	
	
	public void putMattressPrice() {
		for(int counter = 0; counter < Layers.size(); counter++) {
			MattressPrice.put(MattressSize.TWIN, MattressPrice.get(MattressSize.TWIN)+Layers.get(counter).getPrice().get(MattressSize.TWIN));
			MattressPrice.put(MattressSize.TWINXL, MattressPrice.get(MattressSize.TWINXL)+Layers.get(counter).getPrice().get(MattressSize.TWINXL));
			MattressPrice.put(MattressSize.FULL, MattressPrice.get(MattressSize.FULL)+Layers.get(counter).getPrice().get(MattressSize.FULL));
			MattressPrice.put(MattressSize.QUEEN, MattressPrice.get(MattressSize.QUEEN)+Layers.get(counter).getPrice().get(MattressSize.QUEEN));
			MattressPrice.put(MattressSize.KING, MattressPrice.get(MattressSize.KING)+Layers.get(counter).getPrice().get(MattressSize.KING));
			MattressPrice.put(MattressSize.CKING, MattressPrice.get(MattressSize.CKING)+Layers.get(counter).getPrice().get(MattressSize.CKING));
		}
		MattressPrice.put(MattressSize.TWIN, MattressPrice.get(MattressSize.TWIN)+outerCover.getCoverPrice().get(MattressSize.TWIN));
		MattressPrice.put(MattressSize.TWINXL, MattressPrice.get(MattressSize.TWINXL)+outerCover.getCoverPrice().get(MattressSize.TWINXL));
		MattressPrice.put(MattressSize.FULL, MattressPrice.get(MattressSize.FULL)+outerCover.getCoverPrice().get(MattressSize.FULL));
		MattressPrice.put(MattressSize.QUEEN, MattressPrice.get(MattressSize.QUEEN)+outerCover.getCoverPrice().get(MattressSize.QUEEN));
		MattressPrice.put(MattressSize.KING, MattressPrice.get(MattressSize.KING)+outerCover.getCoverPrice().get(MattressSize.KING));
		MattressPrice.put(MattressSize.CKING, MattressPrice.get(MattressSize.CKING)+outerCover.getCoverPrice().get(MattressSize.CKING));
	}
	public void showPrice() {
		for(Entry<MattressSize, Double> entry : MattressPrice.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
	
	public void savePrice() {
		DBAccess dba = new DBAccess();
		dba.saveMattressPrice(MattressPrice,ID);
	}

}