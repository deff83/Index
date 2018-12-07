package com.deff83.indexAPI;

public class MyZSave {

	private String priceper;
	private String price;
	private int notes, popitka = 0;

	public MyZSave(String priceper,int notes, String price) {

		this.priceper = priceper;
		this.price = price;

		this.notes = notes;
	}

	public String getPricePer() {
		return priceper;
	}
	public void setPricePer(String priceper) {
		this.priceper = priceper;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	public int getPopitka() {
		return popitka;
	}

	public void setPopitka(int popitka) {
		this.popitka = popitka;
	}


	public int getNotes() {
		return notes;
	}

	public void setNotes(int notes) {
		this.notes = notes;
	}

	
	
}
