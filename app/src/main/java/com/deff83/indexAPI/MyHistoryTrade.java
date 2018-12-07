package com.deff83.indexAPI;
import android.util.*;
import java.util.*;

public class MyHistoryTrade {
	private Integer id,isbid,notes;
	private String stamp;
	private String name;
	private Double price;
	private String isbidstr;

	public MyHistoryTrade(int id, int isbid, int notes, String stamp, String name, Double price) {

		this.id = id;
		this.isbid = isbid;
		this.notes = notes;
		this.stamp = stamp;
		this.name = name;
		this.price = price;
		if (isbid == 1) this.isbidstr = "покупка";
		if (isbid == 0) this.isbidstr = "продажа";
	}
	/////////////////////////////////////////////////////// id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/////////////////////////////////////////////////////// isbid
	public int getIsbid() {
		return isbid;
	}
	public void setIsbid(int isbid) {
		this.isbid = isbid;
	}
	//////////////////////////////////////////////////////// notes
	public int getNotes() {
		return notes;
	}
	public void setNotes(int notes) {
		this.notes = notes;
	}
	//////////////////////////////////////////////////////// stamp
	public String getStamp() {
		return stamp;
	}
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	//////////////////////////////////////////////////////// name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	//////////////////////////////////////////////////////// isbidstr
	public String getIsbidstr() {
		return isbidstr;
	}
	public void setIsbidstr(String isbidstr) {
		this.isbidstr = isbidstr;
	}
	//////////////////////////////////////////////////////// price
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}
