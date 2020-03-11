package com.pmt.mattress;

public enum MattressSize {
	TWIN(39,75,2925,189), TWINXL(39,80,3120,238), FULL(54,75,4050,258), QUEEN(60,80,4800,280), KING(76,80,6080,312),CKING(72,84,6048,312);
	
	private int length;
	private int width;
	private int surface;
	private int circumference;
	
	private MattressSize(int l, int w, int s, int c) {
		length = l;
		width = w;
		surface = s;
		circumference = c;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getSurface() {
		return surface;
	}
	
	public int getCircumference() {
		return circumference;
	}
}
