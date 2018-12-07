package com.deff83.indexAPI;
import android.util.*;
import java.util.*;
public class MyZayvkiForTable {
	private String name, kindstring;
	private Integer zCoin, offerId, kind, notes;
	private String price;
	public MyZayvkiForTable(int zCoin, int offerId, String name, int kind, String price,int notes) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.zCoin = zCoin;
		this.offerId = offerId;
		this.kind = kind;
		this.notes = notes;
		this.price = price;
		if(kind==0) {
				this.kindstring = "продажа";
		}
		if(kind==1) {
			this.kindstring = "покупка";
		}
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getKindstring(){
		return kindstring;
	}
	public void setKindstring(String kindstring){
		this.kindstring = kindstring;
	}
	public String getPrice(){
		return price;
	}
	public void setPrice(String price){
		this.price = price;
	}
	public int getzCoin(){
		return zCoin;
	}
	public void setzCoin(int zCoin){
		this.zCoin = zCoin;
	}
	public int getOfferId(){
		return offerId;
	}
	public void setOfferId(int offerId){
		this.offerId = offerId;
	}
	public int getKind(){
		return kind;
	}
	public void setKind(int kind){
		this.kind = kind;
	}
	public int getNotes(){
		return notes;
	}
	public void setNotes(int notes){
		this.notes = notes;
	}
}