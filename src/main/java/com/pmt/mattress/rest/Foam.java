package com.pmt.mattress.rest;

import java.util.List;
import com.pmt.mattress.DBAccess;

public class Foam {
	private String name;
	private int id;
	private double bfPrice;
	
	public static int getFoamID(String name) { // Get Foam ID from Database
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		return dba.getFoamID(name);
	}
	
	public static Foam getFoam(int id) { // Get Foam from Database
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		return dba.getFoam(id);
	}
	
	public static List<Foam> getAllFoams() { // Get all Foams from Database
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		return dba.getAllFoams();
	}
	
	public static void createFoam(Foam f) { // Create Foam, and save into Database
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		dba.createFoam(f);
	}
	
	public static void deleteFoam(int id) {	// Delete Foam from Database
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		dba.deleteFoam(id);
	}
	
	public static void updateName(int id, String newName) { // Update Foam name from Database
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		dba.updateFoamName(id,newName);
	}
	
	public static void updatePrice(int id, Double newPrice) { // Update Foam price from Database
		DBAccess dba = new DBAccess();
		dba.DBA_init();
		dba.updateFoamPrice(id,newPrice);
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
		
	public void setBfPrice(double bfPrice) {	
		this.bfPrice = bfPrice;
	}
	public Double getBfPrice() {
		return bfPrice;
	}
}
