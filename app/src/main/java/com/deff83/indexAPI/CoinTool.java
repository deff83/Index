package com.deff83.indexAPI;

public class CoinTool {

	private String name;
	private String price;
	private int id;

	public CoinTool(String name, String price, int id) {

		this.name = name;
		this.price = price;
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	
	
}
