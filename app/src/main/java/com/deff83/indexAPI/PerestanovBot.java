package com.deff83.indexAPI;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.*;
import java.util.*;
import android.os.*;
import okhttp3.*;
import org.json.*;
import java.io.*;
import android.widget.*;
import android.app.*;
import android.content.*;
import android.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.security.*;





import android.content.res.*;
import android.support.v4.app.*;


import java.util.*;

import android.text.*;

import android.preference.*;


public class PerestanovBot  implements ISomeModel {
	private SharedPreferences pref;
	private List<MyZSave> listMyZSave = new ArrayList<>();
	
	SharedPreferences.Editor editor = null;
	private Context context;
	private List<MyZayvkiForPerestanov> listMyZPerestanov = null;
	public PerestanovBot(Context context){
		this.context = context;
		pref = context.getSharedPreferences("CAT", Context.MODE_PRIVATE);
		editor = pref.edit();
	}
	@Override
	public void doUpdate(int argum){
		
		
		switch(argum){
			case 0:
				if(listMyZPerestanov==null)break;
				listMyZPerestanov = new ArrayList<>();
				List<MyZayvkiForTable> listMyZayvkiForTable0 = Parametr.getParametr().getMyZayvkiForTable();
				Set<String> id_perest0 = pref.getStringSet("id_perest", new HashSet());
				for(int i=0; i<listMyZayvkiForTable0.size(); i++){
					MyZayvkiForTable myZayvkiForTable = listMyZayvkiForTable0.get(i);
					String idMyZ = myZayvkiForTable.getOfferId() + "";
					if (id_perest0.contains(idMyZ)) {
						String price_perest = pref.getString("price_perest" + idMyZ, "0.0");
						listMyZPerestanov.add(new MyZayvkiForPerestanov(myZayvkiForTable, price_perest));
					}
				}
			break;
			case 1:
				if(listMyZPerestanov==null)break;
				for(int i=0; i<listMyZPerestanov.size(); i++){
					
						MyZayvkiForPerestanov myZayvkiForPerestanov = listMyZPerestanov.get(i);
						MyZayvkiForTable myZayvki = myZayvkiForPerestanov.getMyZayvka();
						String pricePerest = myZayvkiForPerestanov.getPricePerest();
						Double pricePerestd = Double.valueOf(pricePerest);
						int idCoinz = myZayvki.getzCoin();
						if(idCoinz==Parametr.getParametr().getzCoinMain()){
							int offerid = myZayvki.getOfferId();
							String name = myZayvki.getName();
							int kind = myZayvki.getKind();//0 - продажа, 1 - покупка
							String price = myZayvki.getPrice();
							Double priced = Double.valueOf(price);
							int notes = myZayvki.getNotes();
							
							OrdersPriceList ordersPriceList = Parametr.getParametr().getOrdersPriceList();
							List<OrderPrice> listsell = ordersPriceList.getListbuy();//правая колонка
							List<OrderPrice> listbuy = ordersPriceList.getListsell();//левая колонка
							Double pricebuy = listsell.get(0).getPrice();
							Double pricesell = listbuy.get(0).getPrice();
							if(kind==0){
								if((priced > pricesell||(priced==pricesell && listbuy.get(0).getOfferid()!=0)) && pricePerestd < pricesell){
									
									Set<String> id_perest1 = pref.getStringSet("id_perest", new HashSet());
									id_perest1.remove(offerid+"");
									editor.putStringSet("id_perest", id_perest1);
									editor.commit();
									
									String type = "false";
									Double jk = pricesell-0.0001;
									String price_dialog = String.format(Locale.US, "%.4f",jk);
									AddOrder addorder = new AddOrder(type, idCoinz, notes, price_dialog);
									Parametr.getParametr().setOrderadd(addorder);
									Parametr.getParametr().setDelOrder(offerid);
									Parametr.getParametr().setPerestanov(true);
									Parametr.getParametr().setBooldelOrder(true);
									listMyZSave.add(new MyZSave(pricePerest,notes,price_dialog));
								}
							}
							if(kind==1){
								if((priced < pricebuy||(priced==pricebuy && listsell.get(0).getOfferid()!=0)) && pricePerestd > pricebuy){
									
									Set<String> id_perest1 = pref.getStringSet("id_perest", new HashSet());
									id_perest1.remove(offerid+"");
									editor.putStringSet("id_perest", id_perest1);
									editor.commit();
									
									String type = "true";
									Double jk = pricebuy+0.0001;
									String price_dialog = String.format(Locale.US, "%.4f",jk);
									AddOrder addorder = new AddOrder(type, idCoinz, notes, price_dialog);
									Parametr.getParametr().setOrderadd(addorder);
									Parametr.getParametr().setDelOrder(offerid);
									Parametr.getParametr().setPerestanov(true);
									Parametr.getParametr().setBooldelOrder(true);
									listMyZSave.add(new MyZSave(pricePerest,notes,price_dialog));
								}
							}
						}
					
				}
			break;
			case 2: 
				
				listMyZPerestanov = new ArrayList<>();
				List<MyZayvkiForTable> listMyZayvkiForTable = Parametr.getParametr().getMyZayvkiForTable();
				Set<String> id_perest = pref.getStringSet("id_perest", new HashSet());
				for(int i=0; i<listMyZayvkiForTable.size(); i++){
					MyZayvkiForTable myZayvkiForTable = listMyZayvkiForTable.get(i);
					String idMyZ = myZayvkiForTable.getOfferId() + "";
					if (id_perest.contains(idMyZ)) {
						String price_perest = pref.getString("price_perest" + idMyZ, "0.0");
						listMyZPerestanov.add(new MyZayvkiForPerestanov(myZayvkiForTable, price_perest));
					}
				}
				
				for(int i=0; i<listMyZSave.size(); i++){
					MyZSave myZSave = listMyZSave.get(i);
					String price = myZSave.getPrice();
					Double priced = Double.valueOf(price);
					String priceper = myZSave.getPricePer();
					Double priceperd = Double.valueOf(priceper);
					int notes =  myZSave.getNotes();
					int popitka = myZSave.getPopitka();
					if(popitka<2){
						myZSave.setPopitka(popitka+1);
						for(int j=0; j<listMyZayvkiForTable.size(); j++){
							MyZayvkiForTable myZayvkiForTablef = listMyZayvkiForTable.get(j);
							int idCoinztable = myZayvkiForTablef.getzCoin();
							int offeridtable = myZayvkiForTablef.getOfferId();
							String nametable = myZayvkiForTablef.getName();
							String kindtable = myZayvkiForTablef.getKindstring();
							String pricetable = myZayvkiForTablef.getPrice();
							Double pricetabled = Double.valueOf(pricetable);
							int notestable = myZayvkiForTablef.getNotes();
							if(notestable == notes && price.equals(pricetable)){
								Set<String> id_perestf = pref.getStringSet("id_perest", new HashSet());
								id_perestf.add(offeridtable+"");
								
								editor.putString("price_perest"+offeridtable, priceper+"");
								editor.putStringSet("id_perest", id_perestf);
								editor.commit();
								listMyZSave.remove(myZSave);
							}
						}
					}else{
						listMyZSave.remove(myZSave);
					}
					
				}
				
			break;
			case 3:
			break;
			case 4:
				
			
			break;
			case 5:
				
			break;
			case 6:
				
			break;
		}
	
	}
	public void addSavePerest(String pricePerest, int notes, String price_dialog){
		listMyZSave.add(new MyZSave(pricePerest,notes,price_dialog));
	}
}