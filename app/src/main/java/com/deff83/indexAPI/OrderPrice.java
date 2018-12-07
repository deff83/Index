package com.deff83.indexAPI;
import android.util.*;
import java.util.*;
public class OrderPrice {
	private Double price;
	private int notes, offerid, kind;
	
	public OrderPrice() {
		
	}
	
	public OrderPrice(Double price, int notes, int offerid, int kind) {
		super();
		this.price = price;
		this.notes = notes;
		this.offerid = offerid;
		this.kind = kind;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getNotes() {
		return notes;
	}

	public void setNotes(int notes) {
		this.notes = notes;
	}

	public int getOfferid() {
		return offerid;
	}

	public void setOfferid(int offerid) {
		this.offerid = offerid;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}
	
	

}
