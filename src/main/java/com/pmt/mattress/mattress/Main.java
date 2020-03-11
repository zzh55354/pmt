package com.pmt.mattress;

public class Main {

	public static void main(String[] args) {
		Mattress m = new Mattress("SearBed");
		Layer topper = new Layer(2.5, "gel infused memory foam");
		Layer base = new Layer(7.5, "conventional white foam");
		OuterCover outCover = new OuterCover("SEAR BED OUTER COVER");
		
		m.addLayer(topper);
		m.addLayer(base);
		m.addOuterCover(outCover);
		m.putMattressPrice();
		m.showPrice();
		m.setID("NSSBM001");
		m.savePrice();
		
	}

}
