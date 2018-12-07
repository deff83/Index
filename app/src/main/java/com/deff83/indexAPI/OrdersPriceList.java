package com.deff83.indexAPI;
import android.util.*;
import java.util.*;

public class OrdersPriceList {
	private List<OrderPrice> listbuy;//левая колонка
	private List<OrderPrice> listsell;//левая колонка
	
	public OrdersPriceList(List<OrderPrice> listbuy, List<OrderPrice> listsell) {
		super();
		this.listbuy = listbuy;
		this.listsell = listsell;
	}

	public OrdersPriceList() {
		// TODO Auto-generated constructor stub
	}

	public List<OrderPrice> getListbuy() {
		return listbuy;
	}

	public void setListbuy(List<OrderPrice> listbuy) {
		this.listbuy = listbuy;
	}

	public List<OrderPrice> getListsell() {
		return listsell;
	}

	public void setListsell(List<OrderPrice> listsell) {
		this.listsell = listsell;
	}
	

}