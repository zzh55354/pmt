package com.pmt.mattress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Mattress {
	private String name;
	private int id;
	private double height;
	private Double priceTwin; 
	private Double priceTxl;
	private Double priceFull;
	private Double priceQueen;
	private Double priceKing;
	private Double priceCking;

	private ArrayList <Layer> Layers;
	private OuterCover outerCover = null;
	
	public Mattress() {

	}
	
	public static void createMattress(Mattress m) {
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		dba.createMattress(m);
	}
	
	public static void addLayer(int mId, int lId) {
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		dba.addLayer(mId,lId);
	}
	
	public static List<Mattress> getAllMattresses() {
		ArrayList<Mattress> mattressList = new ArrayList<Mattress>();
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		mattressList = dba.getAllMattresses();
		return mattressList;
	}
	
	public static Mattress getMattress(int id) {
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		return (dba.getMattress(id));
	}
	
	public static void deleteMattress(int id) {
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		dba.deleteMattress(id);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	public int getID() {
		return id;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	public double getHeight() {
		return height;
	}
	
	public void setPriceTwin(Double price) {
		priceTwin = price;
	}
	public double getPriceTwin() {
		return priceTwin;
	}
	
	public void setPriceTxl(Double price) {
		priceTxl = price;
	}
	public double getPriceTxl() {
		return priceTxl;
	}
	
	public void setPriceFull(Double price) {
		priceFull = price;
	}
	public double getPriceFull() {
		return priceFull;
	}
	
	public void setPriceQueen(Double price) {
		priceQueen = price;
	}
	public double getPriceQueen() {
		return priceQueen;
	}
	
	public void setPriceKing(Double price) {
		priceKing = price;
	}
	public double getPriceKing() {
		return priceKing;
	}
	
	public void setPriceCking(Double price) {
		priceCking = price;
	}
	public double getPriceCking() {
		return priceCking;
	}
	
	public void setOuterCover(OuterCover oc) {
		outerCover = oc; 
	}
	public OuterCover getOuterCover() {
		return outerCover;
	}
	
	public void setLayers(ArrayList<Layer> Layers) {
		this.Layers = Layers;
	}
	public ArrayList <Layer> getLayers() {
		return Layers;
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
	
	
	
}