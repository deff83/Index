package com.deff83.indexAPI;
import android.content.*;
import java.util.*;
import android.preference.*;

public class Bot_1
{
	
	//private SharedPreferences shp;
	private SharedPreferences.Editor editor;
	private static Bot_1 bot1;
	private  Context context;
	private Opiration o;
	private Set<String> hmyz;
	private Set<String> hstoplos;
	//поля перестановки цены
	
	Integer z_coin, z_notes;
	String isbid, price_dialog_str_min, price_dialog_str_mind;
	//поля сработанной заявки//
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
	private  SharedPreferences shp;
	public void setBotcontext(Context context){
		this.context = context;
	 shp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication());
		//shp = context.getSharedPreferences("CAT", Context.MODE_PRIVATE);
		
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
	private synchronized void sravn(int coin, int flag_typ, double price){
		if (flag_typ == 0){
			o = Opiration.getOpiration();
			o.pricelist(coin, 1);
			ArrayList<Double> t = o.getlistpricex();
			Double pricebit = t.get(0);
			if (pricebit < price-0.0001){
				editor.putInt("minpriceopovfoin", 1);
				editor.commit();
			}
		}
		if (flag_typ == 1){
			o = Opiration.getOpiration();
			o.pricelist(coin, 1);
			ArrayList<Double> t = o.getlistpricey();
			Double pricebit = t.get(0);
			if (pricebit > price+0.0001){
				editor.putInt("maxpriceopovfoin", 1);
				editor.commit();
			}
		}
	} 
	Double pricebitper, pricebitpper;
	public synchronized void f(){//стоплос периодически вызывается
	try{
		//цены bch
		ArrayList<Double> tperbch, tpperbch;
		Opiration o = Opiration.getOpiration();
		o.pricelist(66, 1);
		tperbch = o.getlistpricex();
		Double pricebchper = tperbch.get(0);
		tpperbch = o.getlistpricey();
		Double pricebchpper = tpperbch.get(0);
		//узнать цену битка
		o = Opiration.getOpiration();
		o.pricelist(60, 1);
		ArrayList<Double> tper = o.getlistpricex();
		 pricebitper = tper.get(0);
		ArrayList<Double> tpper = o.getlistpricey();
		 pricebitpper = tpper.get(0);
		int gp = o.getmyz();
		//перестановка цены
		Set<String>  per = shp.getStringSet("z_perest_id", new HashSet<String>());
		Set<String> priceperset = shp.getStringSet("z_perest_price", new HashSet<String>());
		Set<String> u = shp.getStringSet("myz", new HashSet<String>());
		for (String g : u){
			String pricepoid = shp.getString("price_po_idoffer"+g,"0");
			if (priceperset.contains(pricepoid)){
				per.add(g);
				editor.putString("perstprice"+g, shp.getString("priceperestanovki" + pricepoid, "999"));
				editor.putString("pricezper"+g, pricepoid);
				editor.putInt("kindperest"+g,  shp.getInt("priceperestanovkikind" + pricepoid, 7));
				editor.putInt("noteperest"+g, shp.getInt("priceperestanovkinotes"+pricepoid, 0));//тут остановился
				editor.putInt("coinperest"+g, shp.getInt("priceperestanovkicoin"+pricepoid, 60));
				priceperset.remove(pricepoid);
				editor.putStringSet("z_perest_price", priceperset);
				editor.putStringSet("z_perest_id", per);
				editor.commit();
			}
		}
		
		//jhgvjhhh
		Set<String>  peri = shp.getStringSet("z_perest_id", new HashSet<String>());
		Set<String> priceperseti = shp.getStringSet("z_perest_price", new HashSet<String>());
		for(String zidper : peri){
			//цены по id заявки
			Integer idcoin = shp.getInt("coinperest"+zidper, 60);
			ArrayList<Double> tpercen, tppercen;
			//Opiration ocen = Opiration.getOpiration();
			o.pricelist(idcoin, 1);
			tpercen = o.getlistpricex();
			Double pricecen = tpercen.get(0);
			tppercen = o.getlistpricey();
			Double pricepcen = tppercen.get(0);
			//try{
				//String tyup = "9.2";
				//String tpop = "9.2";
			String tyup = shp.getString("perstprice"+zidper, "0.0");
			String tpop = shp.getString("pricezper"+zidper, "0.0");
			Double priceper = Double.parseDouble(tyup+"");
			Double zpriceper = Double.parseDouble(tpop+"");
			if (priceperseti.contains(tpop)==false){
				priceperseti.add(tpop);
				editor.putStringSet("z_perest_price", priceperseti);
				editor.putStringSet("z_perest_id", peri);
				editor.commit();
			}
			if (shp.getInt("kindperest"+zidper, 0)==0){
			if (pricepcen > priceper){
			 if(pricepcen< zpriceper){
				 int yp = shp.getInt("noteperest" + zidper, 0);
				 int zid = Integer.parseInt(zidper);
				 int kind_up = 0;
				 int z_con = shp.getInt("coinperest"+zidper, 60);
				 perestanov(z_con, kind_up,  zid, yp, tpop);
				 }
				if(pricepcen== zpriceper){
					if (gp == 0|| gp == 2){
					int yp = shp.getInt("noteperest" + zidper, 0);
					int zid = Integer.parseInt(zidper);
					int kind_up = 0;
					int z_con = shp.getInt("coinperest"+zidper, 60);
					perestanov(z_con, kind_up,  zid, yp, tpop);
					}
					}
			 }
			}
				if (shp.getInt("kindperest"+zidper, 0)==1){
					if(pricecen<priceper){
					if(pricecen> zpriceper ){
						int yp = shp.getInt("noteperest" + zidper, 0);
						int zid = Integer.parseInt(zidper);
						int kind_up = 1;
						int z_con = shp.getInt("coinperest"+zidper, 60);
						perestanov(z_con, kind_up,  zid, yp, tpop);
						}
					if(pricecen==zpriceper){
						if(gp == 0 || gp == 1){
							int yp = shp.getInt("noteperest" + zidper, 0);
							int zid = Integer.parseInt(zidper);
							int kind_up = 1;
							int z_con = shp.getInt("coinperest"+zidper, 60);
							perestanov(z_con, kind_up,  zid, yp, tpop);
						}
					}
					}
				}
			
			//}catch(Exception e){}
			
		}
		
		editor.putStringSet("z_perest_price", priceperseti);
		editor.commit();
		Integer zCoinwidj = shp.getInt("zCoin", 60);
		o.pricelist(zCoinwidj, 1); //узнаць цену чтобы установить на вижет
		ArrayList<Double> t = o.getlistpricex();
		Double pricebit = t.get(0);
		ArrayList<Double> tp = o.getlistpricey();
		Double pricebitp = tp.get(0);
		int g = o.getmyz();
		
		if( shp.getInt("sound_opov_price", 0) == 0){
		sravn(60,0, Double.parseDouble(shp.getString("edit_price_opmin", "0.0")));
		sravn(60,1, Double.parseDouble(shp.getString("edit_price_opmax", "999.9")));
		}
		
		//проверка на изменение
		editor.putInt("colorwidmin", 0);
		editor.putInt("colorwidmax", 0);
		if(Double.parseDouble(shp.getString("widjetmin", "0.0"))< pricebit){
			editor.putInt("colorwidmin", 2);
			
		}
		if(Double.parseDouble(shp.getString("widjetmin", "0.0"))> pricebit){
			editor.putInt("colorwidmin", 1);

		}
		if(Double.parseDouble(shp.getString("widjetmax", "999.0"))< pricebitp){
			editor.putInt("colorwidmax", 2);

		}
		if(Double.parseDouble(shp.getString("widjetmax", "999.0"))> pricebitp){
			editor.putInt("colorwidmax", 1);

		}
		editor.putInt("widjmyzvr", g);
		
		editor.putString("widjetmin", pricebit.toString());
		editor.putString("widjetmax", pricebitp.toString());
		
		editor.putString("pricebit", pricebitp.toString());
		editor.putString("pricebchinfo", pricebchpper.toString());
		editor.commit();
		
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
		}catch(Exception e){}
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
	public void perestanov(int z_coin, int kind_upz, int z_id, int z_notes, String priz){
		
		price_dialog_str_mind = "ttt";
		price_dialog_str_min = "---";
		Double jk;
		isbid = "";
		Opiration o = Opiration.getOpiration();

		ArrayList<Double> g = new ArrayList();
		if (kind_upz == 1){
			o.pricelist(z_coin, 1);

			g = o.getlistpricex();
			price_dialog_str_min = g.get(0).toString();
			jk = Double.parseDouble(price_dialog_str_min)+0.0001;
			price_dialog_str_min = String.format(Locale.US, "%.4f",jk);
			isbid = "true";
		}
		if(kind_upz == 0){
			o.pricelist(z_coin, 1);

			g = o.getlistpricey();
			price_dialog_str_min = g.get(0).toString();
			jk = Double.parseDouble(price_dialog_str_min)-0.0001;
			price_dialog_str_min = String.format(Locale.US, "%.4f",jk);
			isbid = "false";//продажа
		}
		int x = o.del(z_id);
		if (x==1){
			//editor.putString("strmin", price_dialog_str_mind);
			//editor.commit();
			Set<String> priceperset = shp.getStringSet("z_perest_price", new HashSet<String>());
			Set<String>  per = shp.getStringSet("z_perest_id", new HashSet<String>());
			o.add(z_coin, z_notes, isbid,Double.parseDouble(price_dialog_str_min));
			
			editor.putString("infor","ставка переставлена на " + price_dialog_str_min + " ,в количестве " + z_notes + " нот");
			editor.putInt("flagtextinform", 1);
			priceperset.add(price_dialog_str_min);
			editor.putStringSet("z_perest_price", priceperset);
			editor.putString("priceperestanovki"+price_dialog_str_min, shp.getString("perstprice"+z_id, "999"));
			editor.putInt("priceperestanovkikind"+price_dialog_str_min, kind_upz);
			editor.putInt("priceperestanovkinotes"+price_dialog_str_min, z_notes);
			editor.putInt("priceperestanovkicoin"+price_dialog_str_min, z_coin);
			editor.commit();
			
			
			if (priceperset.contains(priz+"")==true){
				priceperset.remove(priz+"");
				editor.putStringSet("z_perest_price", priceperset);
				
				editor.commit();
			
		}
		if (per.contains(z_id+"")==true){
			per.remove(z_id+"");
			editor.putStringSet("z_perest_id", per);
			editor.commit();
		}
		}
	}
}
