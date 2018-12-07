package com.deff83.indexAPI;
import java.text.*;

import java.util.*;
public class ChatMessage{
	private String mess;
	private String nickname;
	private String short_nickname;
	private String date;
	private String urlimg;
	private String attestats;
	private String myFormatDate;
	private String wmid;
	private String id;
	
	public ChatMessage(String id, String mess,String nickname,String datein,String urlimg,String attestats, String wmid){
		this.id = id;
		this.mess = mess;
		this.nickname = nickname;
		int limit = 25;
		this.short_nickname = nickname.length() > limit ? nickname.substring(0, limit) : nickname;
		this.date = datein;
		this.urlimg = urlimg;
		this.attestats = attestats;
		this.myFormatDate = date(datein);
		this.wmid = wmid;
	}
	public String getMess() {
		return mess;
	}
	public void setMess(String mess) {
		this.mess = mess;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWmid() {
		return wmid;
	}
	public void setWmid(String wmid) {
		this.wmid = wmid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getShort_nickname() {
		return short_nickname;
	}
	public void setShort_nickname(String short_nickname) {
		this.short_nickname = short_nickname;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUrlimg() {
		return urlimg;
	}
	public void setUrlimg(String urlimg) {
		this.urlimg = urlimg;
	}
	public String getAttestats() {
		return attestats;
	}
	public void setAttestats(String attestats) {
		this.attestats = attestats;
	}
	public String getMyFormatDate() {
		return myFormatDate;
	}
	public void setMyFormatDate(String myFormatDate) {
		this.myFormatDate = myFormatDate;
	}
	
	private String date(String datein){
		String output = "";
		 // = "Thu Jun 18 20:56:02 EDT 2009";
		try{
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date dateo = parser.parse(datein);
		Date dateopl3 = new Date(dateo.getTime()+10800000);
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm, ddMMMyyyy");
		output = formatter.format(dateopl3);
		}catch(Exception e){
			output = e.toString();
		}
		return output;
	}
}