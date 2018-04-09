package com.deff83.indexAPI;
import android.content.*;
import java.util.*;

public class Bot_1
{
	private SharedPreferences shp;
	private SharedPreferences.Editor editor;
	private static Bot_1 bot1;
	private  Context context;
	private Opiration o;
	private Set<String> hmyz;
	private Set<String> hstoplos;
	//поля сработанной заявки
	private String prise_srab;
	private String notes_srab;
	private Integer coin_srab;
	//поля количество коинов в портфеле
	private Integer col_z;
	private ArrayList<Integer> count_coin;
	private ArrayList<Integer> zCoin_count;
	//поля бота
	private Integer zCoin;
	private Integer idzayvka;
	private Double pricezayvka;
	private Integer noteszayvka = 1;
	private Integer typ;
	private Integer count_coins;
	public void setBotcontext(Context context){
		this.context = context;
		shp = context.getSharedPreferences("CAT", Context.MODE_PRIVATE);
		editor = shp.edit();
	}
	private Bot_1(){
		
	}
	public synchronized static Bot_1 getbot1(){
		
		if (bot1 == null){
			bot1 = new Bot_1();
			
		}
		return bot1;
	}
	public synchronized void f(){//стоплос периодически вызывается
		hstoplos = shp.getStringSet("z_stoplos", new HashSet<String>());
		hmyz = shp.getStringSet("myz",  new HashSet<String>());
		String idzsrab = newz_stoplos();
		int xc = izm_count_coin();
		
		System.out.println(xc);
		if (idzsrab.equals("0")==false){
			hstoplos.remove(idzsrab);
			
			
			editor.remove("stoplosprice"+idzsrab);
			editor.remove("stoplosnotes"+idzsrab);
			editor.remove("stoplosust"+idzsrab);
			editor.remove("stoploscoin"+idzsrab);
			editor.commit();
		}
		
	}
	private String newz_stoplos(){		//проверка в списке стоплоса если в мои заявки нет этого ордера
		for (String hstoplo : hstoplos){
			if(hmyz.contains(hstoplo)==false){
				
				return hstoplo;
			}
			
		}
		
		return "0";
	}
	private Integer izm_count_coin (){		//возращает индекс изменненой количества коина
		col_z = shp.getInt("portlength", 0);
		try{
		if (count_coin != null){
			for (int i=0; i<col_z; i++){
				int idx = shp.getInt("port_id" + i, 0);
				int notx = shp.getInt("port_notes" + i , 0);
				if (idx == zCoin_count.get(i) && notx != count_coin.get(i)){
					zapisilistcountcoin();
					return i;
				}
			}
		
		}
		}catch(Exception e){}
		zapisilistcountcoin();
		return -1;
	}
	private void zapisilistcountcoin(){
		count_coin = new ArrayList();
		zCoin_count = new ArrayList();
		for (int i=0; i<col_z; i++){
			count_coin.add(shp.getInt("port_notes" + i, 0));
			zCoin_count.add(shp.getInt("port_id" + i , 0));
		}
	}
	//функция бота шортить на верхах
	public synchronized void bot_short(){//периодически вызывается
		o = Opiration.getOpiration();
		hmyz = shp.getStringSet("myz",  new HashSet<String>());
		if (shp.getInt("bot", 0)==1){
			zCoin = shp.getInt("botzcoin", 0);
			idzayvka = shp.getInt("idzaybot", 0);
			typ = shp.getInt("typbot", 0);
			if (idzayvka == 0){
				Double min_cena = minprice(typ, zCoin);
				String isbid = "";
				System.out.println(min_cena);
				if (typ == 0){
					isbid = "true";
				} 
				if (typ == 1){
					isbid = "false";
				}
				editor.putInt("col_after", shp.getInt("port_count_id" + zCoin, 0));
				editor.putStringSet("idzayvki_after", hmyz);
				editor.commit();
				int addo = o.add(zCoin, noteszayvka, isbid, min_cena);
				if (addo == 1){
					idzayvka = 1;
					editor.putInt("idzaybot", 1);
					
					
					editor.putString("pricebot", min_cena.toString());
					editor.commit();
				}
				
			} 
			if (idzayvka == 1){
				Integer col_after = shp.getInt("col_after", 0);
				Integer col_posle = shp.getInt("port_count_id" + zCoin, 0);
				if (typ == 0){
					if (col_posle > col_after){//проверка на изменение коинов
						editor.putInt("typbot", 1);
						editor.putInt("idzaybot", 0);
						editor.commit();
					}
				} 
				if (typ == 1){
					if (col_posle < col_after){
						editor.putInt("typbot", 0);
						editor.putInt("idzaybot", 0);
						editor.commit();
					}
				}
				if (shp.getInt("idzaybot", 0) == 1){//если все еще равно 1
				Set<String> yu = shp.getStringSet("idzayvki_after", null);
				//Set<String> yu= hmyz;
				String idnot = "";
				System.out.println("tuta9");
				for (String hmy  : hmyz){
					//System.out.println(y);
					if (yu.contains(hmy) == false){
						if (shp.getInt("id_po_offerid"+hmy, 0) == zCoin && shp.getString("price_po_idoffer"+hmy, "0").equals(shp.getString("pricebot", "1"))){
							editor.putInt("idzaybot", 2);
							editor.putString("id_z_bot_find", hmy);
							editor.commit();
						}
					}
				}
				}
				
			}
			if (idzayvka == 2){
				System.out.println("tttttttyyyoooooo");
				Integer col_after = shp.getInt("col_after", 0);
				
				Integer col_posle = shp.getInt("port_count_id" + zCoin, 0);
				System.out.println(col_after+"tttt" + col_posle);
				if (typ == 0){
					if (col_posle > col_after){//проверка на изменение коинов
						editor.putInt("typbot", 1);
						editor.putInt("idzaybot", 0);
						
						editor.commit();
					}
				} 
				if (typ == 1){
					if (col_posle < col_after){
						editor.putInt("typbot", 0);
						editor.putInt("idzaybot", 0);
						editor.commit();
					}
				}
					Double min_cenax = minprice(typ, zCoin);//проверка поставлен ли перед заявка
					if (typ == 0){
						if (min_cenax > (Double.parseDouble(shp.getString("pricebot", "9999.0"))+0.0001)*1.00){
							int xx = Integer.parseInt(shp.getString("id_z_bot_find", "0"));
							o.del(xx);
							editor.putInt("idzaybot", 0);
							editor.commit();
						}
					} 
					if (typ == 1){
						if (min_cenax < (Double.parseDouble(shp.getString("pricebot", "0.0"))-0.0001)*1){
							int xx = Integer.parseInt(shp.getString("id_z_bot_find", "0"));
							o.del(xx);
							editor.putInt("idzaybot", 0);
							editor.commit();
						}
					}
				
			}
			
		} else{
			idzayvka = 0;
			editor.putInt("idzaybot", 0);
			editor.putInt("typbot", 0);
			editor.commit();
		}
	}

	private Double minprice(int x, int z_coin){//минимальная цена
		String price_dialog_str_min = "0.0";
		Double jk;
		
		ArrayList<Double> g = new ArrayList();
		if (x == 0){
			o.pricelist(z_coin, 1);

			g = o.getlistpricex();
			price_dialog_str_min = g.get(0).toString();
			jk = Double.parseDouble(price_dialog_str_min)+0.0001;
			price_dialog_str_min = String.format(Locale.US, "%.4f",jk);
			//isbid = "true";
		}
		if(x == 1){
			o.pricelist(z_coin, 1);

			g = o.getlistpricey();
			price_dialog_str_min = g.get(0).toString();
			jk = Double.parseDouble(price_dialog_str_min)-0.0001;
			price_dialog_str_min = String.format(Locale.US, "%.4f",jk);
			//isbid = "false";
		}
		return Double.parseDouble(price_dialog_str_min);
	}
	
}
