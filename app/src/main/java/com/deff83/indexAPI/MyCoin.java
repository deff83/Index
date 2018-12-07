package com.deff83.indexAPI;

public class MyCoin {

	private String name;
	private Double price;
	private int id, notes;

	public MyCoin(int id, String name,int notes, Double price) {

		this.name = name;
		this.price = price;
		this.id = id;
		this.notes = notes;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getNotes() {
		return notes;
	}

	public void setNotes(int notes) {
		this.notes = notes;
	}

	
	
}
