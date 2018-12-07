package com.deff83.indexAPI;
import android.util.*;
import java.util.*;
public class Deystvie {
	private AddOrder addOrder;
	private int delOrder;
	private int dey;
	private String priceperestanov = "-";
	public Deystvie(AddOrder addOrder, int delOrder, int dey){
		this.addOrder = addOrder;
		this.delOrder = delOrder;
		this.dey = dey;
		
	}
	public int getDelOrder() {
		return delOrder;
	}

	public void setDelOrder(int delOrder) {
		this.delOrder = delOrder;
	}
	public int getDey() {
		return dey;
	}

	public void setDey(int dey) {
		this.dey = dey;
	}
	public String getPriceperestanov() {
		return priceperestanov;
	}

	public void setPriceperestanov(String priceperestanov) {
		this.priceperestanov = priceperestanov;
	}
	public AddOrder getAddOrder() {
		return addOrder;
	}

	public void setAddOrder(AddOrder addOrder) {
		this.addOrder = addOrder;
	}
}
