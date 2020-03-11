package com.pmt.mattress;

public class testmain {

	public static void main(String[] args) {
		for(Mattress m :Mattress.getAllMattresses()) {
			System.out.println(m.getID());
			System.out.println(m.getName2());
		}
	}

}
