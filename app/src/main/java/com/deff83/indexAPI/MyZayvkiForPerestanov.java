package com.deff83.indexAPI;
import android.util.*;
import java.util.*;
public class MyZayvkiForPerestanov {
	private MyZayvkiForTable myZayvka;
	private String pricePerest;
	public MyZayvkiForPerestanov(MyZayvkiForTable myZayvka, String pricePerest) {
		// TODO Auto-generated constructor stub
		this.myZayvka = myZayvka;
		this.pricePerest = pricePerest;
		
	}
	public void setMyZayvka(MyZayvkiForTable myZayvka){
		this.myZayvka = myZayvka;
	}
	public MyZayvkiForTable getMyZayvka(){
		return myZayvka;
	}
	public void setPricePerest(String pricePerest){
		this.pricePerest = pricePerest;
	}
	public String getPricePerest(){
		return pricePerest;
	}
}