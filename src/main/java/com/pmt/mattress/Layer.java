package com.pmt.mattress;
import java.util.List;

import com.pmt.mattress.mattress.Spring;
import com.pmt.mattress.rest.Foam;


public class Layer {
	private String name;
	private int id;
	private double thickness;
	private Foam foam;
	private Spring spring;
	private Double priceTwin; 
	private Double priceTxl;
	private Double priceFull;
	private Double priceQueen;
	private Double priceKing;
	private Double priceCking;

	
	public Layer() {
		
	}
	
	public static int getLayerID(String name) {
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		return dba.getLayerID(name);
	}
	
	public static Layer getLayer(int id) {
		Layer l = new Layer();
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		l = dba.getLayer(id);
		return l;
	}
	
	public static List<Layer> getAllLayers() {
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		return dba.getAllLayers();
	}
	
	public static void createLayer(Layer l) {
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		if(l.getFoam() == null) {
			dba.createLayerBySpring(l);
		} else {
			dba.createLayerByFoam(l);
		}
	}
	
	public static void deleteLayer(int id) {
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		dba.deleteLayer(id);
	}
	
	public static void updateName(int id, String newName) { // Update Layer name from Database
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		dba.updateLayerName(id,newName);
	}
	
	
	public void setFoam(Foam f) {
		this.foam = f;
	}
	public Foam getFoam() {
		return foam;
	}
	
	public void setSpring(Spring s) {
		this.spring = s;
	}
	public Spring getSpring() {
		return spring;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	public int getID() {
		return id;
	}
	
 	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setThickness(double thickness) {
		this.thickness = thickness;
	}
	public double getThickness() {
		return thickness;
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
	
	

}
