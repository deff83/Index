package com.deff83.indexAPI;
import android.content.*;
import android.app.*;
import java.security.*;
import android.util.*;
import android.util.Base64;
import java.io.*;
import okhttp3.*;
import org.json.*;
import android.widget.*;
import java.util.*;
import android.preference.*;
import java.text.*;



public class Opiration  {
	private OkHttpClient clientz =  new OkHttpClient();
	private String login, wmid, password, culture;
	private static Opiration opiration = new Opiration();
	private static SharedPreferences pref;
	private Parametr param = Parametr.getParametr();
	private int schet = 0;
	private Opiration(){
	}
	public static Opiration getOpiration(){
		
		
		return opiration;
		
	}
	public void inicialisation (String login,String wmid,String password,String culture){
		
		this.login = login;
		this.password = password;
		this.culture = culture;
		this.wmid = wmid;
		System.out.println("initialization for wmid:"+this.wmid+" login:"+this.login+" password:"+this.password+" culture:"+this.culture);
		
	}
	public synchronized OrdersPriceList getOfferList(int zCoin, int Size) {
		//if(SettingPar.getSettingPar().isOfferList()==false)return null;
		boolean exit = true;
		OrdersPriceList orderpricelist = null;
		
		
		schet = 0;
		
		while(exit) {
			
			schet++;
			
			
		
			
			if(param.isBoolAddOrder()||param.isBooldelOrder()||param.isChoiseclick()||param.getListDeystvie().size()!=0) {return null;};
			try {
				Thread.sleep(Parametr.getParametr().getChastota()*1000);
			} catch (InterruptedException interapt) {
				// TODO Auto-generated catch block
				//interapt.printStackTrace();
				System.out.println("Interapt - Opiration");
			}
			exit = false;
		List<OrderPrice> listbuy = new ArrayList<>();//левая колонка
		List<OrderPrice> listsell = new ArrayList<>();
		
		param.setI(schet);
		param.upDate(0);
		String priceList = "";
		//запрос прайс листа
		String signature_baz = base64_shifr(1, 0, zCoin,"","");
		//OkHttpClient client3 = new OkHttpClient();
		String json3 = "{'ApiContext':{'Login':'"+login+"','Wmid':'"+wmid+"','Culture':'"+culture+"','Signature':'" + signature_baz + "'},'Trading':{'ID':"+zCoin+"}}";
		RequestBody body3 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json3);
		Request request3 = new Request.Builder()
		.url("https://api.indx.ru/api/v2/trade/OfferList")
		.post(body3)
		.build();
		Response response3 = null;
		try{

			response3 = clientz.newCall(request3).execute();
			//System.out.println(response2);
			//ответ тела post запроса
			priceList = response3.body().string(); 
		}catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(priceList);
		try{
			
			JSONObject jsonObject;
			jsonObject = new JSONObject(priceList);
			Integer code = jsonObject.getInt("code");
			if(code!=0){
			System.out.println(code);
			return null;
			}
			JSONArray jsonArray = jsonObject.getJSONArray("value");
			String cfv = String.valueOf( jsonArray.length());
			Double z = 0.0;
			Integer z_col, z_offerid;
			int j = Size;
			for(int i= 0; i < Size; i++){
				JSONObject json_54 = jsonArray.getJSONObject(i);
				int kind = json_54.getInt("kind");
				if(kind==0) {
					j=i-1;
					break;};
				z = json_54.getDouble("price");
				z_col = json_54.getInt("notes");
				z_offerid = json_54.getInt("offerid");
				OrderPrice orderpricebuy = new OrderPrice(z, z_col, z_offerid, 0);
				listbuy.add(orderpricebuy);
			}
			
			int k = 0;
			while (k != 1){
				
				JSONObject json_btc = jsonArray.getJSONObject(j);
				int kind = json_btc.getInt("kind");
				if (kind == 0){
					k=1;
				}
				
				
				j = j + 1;
				
			}
			j= j -1;
			Double y = 0.0;
			Integer y_col, y_offerid;
			String ystr = "hex";
			for(int i= 0; i < Size; i++){
				if((j+i)+1>jsonArray.length())break;
					JSONObject json_45 = jsonArray.getJSONObject(j+i);
				
					y = json_45.getDouble("price");
					y_col = json_45.getInt("notes");
					y_offerid = json_45.getInt("offerid");
					OrderPrice orderpricesell = new OrderPrice(y, y_col, y_offerid, 1);
					listsell.add(orderpricesell);
			}
			
			
			ystr = y.toString();
		

		
		}catch (JSONException e){
			exit = true;
			System.out.println("Ошибка парсера JSON в Opiration");
			System.out.println(priceList);
		}
		


		
		
		
		
		
		orderpricelist = new OrdersPriceList(listbuy,listsell);
		}
		return orderpricelist;
		
	}
	public List<MyZayvkiForTable> myOrders() {
		//if(SettingPar.getSettingPar().isMyOffer()==false)return null;
		boolean exit = true;
		List<MyZayvkiForTable> myOrderList = new ArrayList<>();
		schet = 0;
		
		while(exit) {
			schet++;
			param.setI(schet);
			param.upDate(0);
			//if(Sostin.getSostin().isPropusk()) {
			//	Sostin.getSostin().setPropusk(false);
			//	return null;
			//}
			
			if(param.isBoolAddOrder()||param.isBooldelOrder()||param.isChoiseclick()||param.getListDeystvie().size()!=0) {return null;};
			try {
				Thread.sleep(Parametr.getParametr().getChastota()*1000);
			} catch (InterruptedException interapt) {
				// TODO Auto-generated catch block
				//interapt.printStackTrace();
				System.out.println("Interapt - Opiration");
			}
			//запрос мои заявки
			String signature_baz = base64_shifr(3, 0, 0,"","");
			//OkHttpClient client3 = new OkHttpClient();
			String json4 = "{'ApiContext':{'Login':'"+login+"','Wmid':'"+wmid+"','Culture':'"+culture+"','Signature':'" + signature_baz + "'}}";
			RequestBody body4 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json4);
			Request request4 = new Request.Builder()
				.url("https://api.indx.ru/api/v2/trade/OfferMy")
				.post(body4)
				.build();
			Response response4 = null;
	 		try{

				response4 = clientz.newCall(request4).execute();
				
				final String coin_price4 = response4.body().string(); 
				System.out.println(coin_price4);
				try{
					JSONObject jsonObject;
					jsonObject = new JSONObject(coin_price4);
					JSONArray jsonArray2 = jsonObject.getJSONArray("value");
					String cfv = String.valueOf( jsonArray2.length());
					
					for (int i=0; i < jsonArray2.length(); i++){
						JSONObject json_my_offer = jsonArray2.getJSONObject(i);
						Integer toolid = json_my_offer.getInt("toolid");											
						Integer offerid = json_my_offer.getInt("offerid");						
						String name = json_my_offer.getString("name");						
						Integer kind = json_my_offer.getInt("kind");
						String price2 = json_my_offer.getString("price");
						Double r = Double.parseDouble(price2);
						String price = String.format(Locale.US, "%.4f",  r);						
						Integer notes = json_my_offer.getInt("notes");
	//stamp дата kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk
						MyZayvkiForTable myOrder = new MyZayvkiForTable(toolid, offerid, name, kind, price,notes);
						myOrderList.add(myOrder);
					}
					param.setMyZayvkiForTable(myOrderList);
					return myOrderList;
				}
				catch (JSONException e){}
			}catch (IOException e) {
				//intent.putExtra("l", "ошибка");
			}
			
			
		}
		return null;
	}
	//запрос на цены коинов тулбара
	public List<CoinTool> getCoinTools(){
		//if(SettingPar.getSettingPar().isTool()==false)return null;
		List<CoinTool> CoinTools = new ArrayList<>();
		
		boolean exit = true;
		schet = 0;
		
		while(exit) {
			schet++;
			param.setI(schet);
			param.upDate(0);
			
			//if(Sostin.getSostin().isPropusk()) {
			//	Sostin.getSostin().setPropusk(false);
			//	return null;
			//}
			
			if(param.isBoolAddOrder()||param.isBooldelOrder()||param.isChoiseclick()||param.getListDeystvie().size()!=0) {return null;};
			try {
				Thread.sleep(Parametr.getParametr().getChastota()*1000);
			} catch (InterruptedException interapt) {
				// TODO Auto-generated catch block
				//interapt.printStackTrace();
				System.out.println("Interapt - Opiration");
			}
			
				String signature_baz = base64_shifr(4, 0, 0,"","");
				//OkHttpClient client2 = new OkHttpClient();
				String json2 = "{'ApiContext':{'Login':'"+login+"','Wmid':'"+wmid+"','Culture':'"+culture+"','Signature':'" + signature_baz + "'}}";
				RequestBody body2 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json2);
				Request request2 = new Request.Builder()
					.url("https://api.indx.ru/api/v2/trade/Tools")
					.post(body2)
					.build();
				Response response2 = null;
				
		 		try{

					response2 = clientz.newCall(request2).execute();
					//System.out.println(response2);
					//ответ тела post запроса
					String coin_price2 = response2.body().string(); 
					try{
						JSONObject jsonObject;
						jsonObject = new JSONObject(coin_price2);
						Integer code = jsonObject.getInt("code");
						if(code!=0){
						System.out.println(code);
						return null;
						}
						JSONArray jsonarray = jsonObject.getJSONArray("value");
						
						for (int i = 0; i < jsonarray.length(); i++) {
							JSONObject friend = jsonarray.getJSONObject(i);
							String name = friend.getString("name");
							Integer id_coin = friend.getInt("id");
							
							String price = friend.getString("price");
							CoinTool cointool = new CoinTool(name, price, id_coin);
							
							CoinTools.add(cointool);
						}
						param.setListCoinTool(CoinTools);
						return CoinTools;
						} 
						catch (JSONException e){
							//ошибка обработка JSON обьекта
							System.out.println("Ошибка парсера JSONCoins в Opiration");
							
							
						}
					
					
						
				//	intent.putExtra("intent_service_name", "coin");
						//intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
						//отправка широковещательного сообщения
					
					
				}
				catch (IOException e) {
				}
		
		}
		return null;
		
	}
	//баланс и мои коины
	public List<MyCoin> balance() {
		//if(SettingPar.getSettingPar().isBalance()==false)return null;
		boolean exit = true;
		schet = 0;
		//Platform.runLater(()->{infoschetAPI.setText("0");});
		while(exit) {
			schet++;
			param.setI(schet);
			param.upDate(0);
			
			//if(Sostin.getSostin().isPropusk()) {
			//	Sostin.getSostin().setPropusk(false);
			//	return null;
			//}
			if(param.isBoolAddOrder()||param.isBooldelOrder()||param.isChoiseclick()||param.getListDeystvie().size()!=0) {return null;};
			
			try {
				Thread.sleep(Parametr.getParametr().getChastota()*1000);
			} catch (InterruptedException interapt) {
				// TODO Auto-generated catch block
				//interapt.printStackTrace();
				System.out.println("Interapt - Opiration");
			}
			
				String signature_baz = base64_shifr(3, 0, 0,"","");
				
				String json2 = "{'ApiContext':{'Login':'"+login+"','Wmid':'"+wmid+"','Culture':'"+culture+"','Signature':'" + signature_baz + "'}}";
				RequestBody body2 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json2);
				Request request2 = new Request.Builder()
					.url("https://api.indx.ru/api/v2/trade/Balance")
					.post(body2)
					.build();
				Response response2 = null;
				
		 		try{

					response2 = clientz.newCall(request2).execute();
					
					//ответ тела post запроса
					String coin_price2 = response2.body().string(); 
					System.out.println(coin_price2);
					try{
						
						JSONObject jsonObject;
						jsonObject = new JSONObject(coin_price2);
						Integer code = jsonObject.getInt("code");
						if(code!=0){
						System.out.println(code);
						return null;
						}
						JSONObject value = jsonObject.getJSONObject("value");
						JSONObject balance = value.getJSONObject("balance");
						Double price = balance.getDouble("price");
						param.setBalance(price+"");
						Double svobprice = balance.getDouble("wmz");
						param.setSvobprice(svobprice+"");
						JSONArray jsonarray = value.getJSONArray("portfolio");
						int portfol_length = jsonarray.length();
						
						 List<MyCoin> listmyCoins = new ArrayList<>();
						 
						
						for (int j=0; j<portfol_length; j++){
							JSONObject port = jsonarray.getJSONObject(j);
							int id_port = port.getInt("id");
							String name_port = port.getString("name");
							int notes_port = port.getInt("notes");
							Double port_price = port.getDouble("price");
							String type_port = port.getString("type");
							int kind_port = port.getInt("kind");
							int by = port.getInt("by");
							
							
							MyCoin myCoin = new MyCoin(id_port, name_port,notes_port, port_price);
							if(notes_port!=0) listmyCoins.add(myCoin);
							
						}
						param.setListMyCoins(listmyCoins);
					
						jsonObject = null;
						return listmyCoins;
						}
						catch (JSONException e){
							System.out.println("ошибка парсера Balance");
						}
					
					
		 		}
				catch (IOException e) {
				}
		
		}
		return null;
		
	}
	//добавление заявки
	public String addOrder(String isbid, int zCoinadd, String price_dialog, int notes_dialog) {
		
		boolean exit = true;
		schet = 0;
		
		while(exit) {
			schet++;
			param.setI(schet);
			param.upDate(0);
			try {
				Thread.sleep(Parametr.getParametr().getChastota()*1000);
			} catch (InterruptedException interapt) {
				// TODO Auto-generated catch block
				//interapt.printStackTrace();
				System.out.println("Interapt - Opiration");
			}
			
			String signature6 = base64_shifr(1, 0,zCoinadd,"","");
			
			
			
			String isanonymous = "true";
			 
			
			String json6 = "{'ApiContext':{'Login':'"+login+"','Wmid':'"+wmid+"','Culture':'"+culture+"','Signature':'"+signature6+"'},'Offer':{'ID':"+zCoinadd+",'Count':"+notes_dialog+",'IsAnonymous':"+isanonymous+",'IsBid':"+isbid+",'Price':"+price_dialog+"}}";
			//	String json6 = "{'ApiContext':{'Login':'" + login + "','Wmid':'" + wmid + "','Culture':'" + culture + "','Signature':'" + signature + "'},'OfferId':{'ID':"+60+",'Count':"+1+",'IsAnonymous':true,'IsBid':true,'Price':"+ 6.0000 +"}}";
			RequestBody body6 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json6);
			Request request6 = new Request.Builder()
				.url("https://api.indx.ru/api/v2/trade/OfferAdd")
				.post(body6)
				.build();
			Response response6 = null;
			try{
				response6 = clientz.newCall(request6).execute();
				 String coin_price6 = response6.body().string(); 
				try {
				JSONObject jsonres6 = new JSONObject(coin_price6);
				
				JSONObject jsonresval6 = jsonres6.getJSONObject("value");
				String jsoncode6 = jsonresval6.getString("Code");
				String reezult = "";
				if (jsoncode6.equals("0")){
					reezult = "успешно поставлена";
				
				}else{
					reezult = "ОШИБКА постановки заявки";
					System.out.println(coin_price6+"^^^^"+json6);
					}
				System.out.println(reezult);
				return reezult;
				}catch(JSONException e){
					System.out.println("ошибка парсера AddOrders : "+coin_price6);
					
				}
			}catch (IOException e) {
				
			}
		}
			return null;
	}
	public String delOrder(int offerid) {
		
			//удаление заявки

		boolean exit = true;
		schet = 0;
		
		while(exit) {
			schet++;
			param.setI(schet);
			param.upDate(0);
			
			try {
				Thread.sleep(Parametr.getParametr().getChastota()*1000);
			} catch (InterruptedException interapt) {
				// TODO Auto-generated catch block
				//interapt.printStackTrace();
				System.out.println("Interapt - Opiration");
			}
			
				
				String signature = base64_shifr(0, offerid, 0,"","");
				

				String json5 = "{'ApiContext':{'Login':'" + login + "','Wmid':'" + wmid + "','Culture':'" + culture + "','Signature':'" + signature + "'},'OfferId':'"+ offerid + "'}";
				RequestBody body5 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json5);
				Request request5 = new Request.Builder()
					.url("https://api.indx.ru/api/v2/trade/OfferDelete")
					.post(body5)
					.build();
				Response response5 = null;
 				try{
					response5 = clientz.newCall(request5).execute();
					final String coin_price5 = response5.body().string(); 
					try{
					JSONObject jsonres5 = new JSONObject(coin_price5);
					JSONObject jsonresval5 = jsonres5.getJSONObject("value");
					String jsoncode5 = jsonresval5.getString("Code");
					String result = "";
					if (jsoncode5.equals("0")){
						result = "Успешно";
						System.out.println("Успешно");
						
					
						}else{
							result = "ОШИБКА удаления заявки ";
							System.out.println("ОШИБКА удаления заявки ");
						
						}
					return result;
						}catch(JSONException e){System.out.println("ошибка парсера DelOrders");}
				}catch (IOException e) {
					
				}
				
			
		}
		return null;
			
	}
	public List<MyHistoryTrade> getHistoryTrading(int zCoin, String DateStart, String DateEnd) {
		
		boolean exit = true;
		schet = 0;
		while(exit) {
			schet++;
			param.setI(schet);
			param.upDate(0);
			if(param.isBoolAddOrder()||param.isBooldelOrder()||param.isChoiseclick()||param.getListDeystvie().size()!=0) {return null;};
			
			try {
				Thread.sleep(Parametr.getParametr().getChastota()*1000);
			} catch (InterruptedException interapt) {
				// TODO Auto-generated catch block
				//interapt.printStackTrace();
				System.out.println("Interapt - Opiration");
			}
			String signature6 = base64_shifr(5, 0,zCoin,DateStart,DateEnd);
			
			String json71 = "{'ApiContext':{'Login':'"+login+"','Wmid':'"+wmid+"','Culture':'"+culture+"','Signature':'"+signature6+"'},'Trading':{'ID':"+zCoin+",'DateStart':"+DateStart+",'DateEnd':"+DateEnd+"}}";
			
			RequestBody body71 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json71);
			Request request71 = new Request.Builder()
				.url("https://api.indx.ru/api/v2/trade/HistoryTrading")
				.post(body71)
				.build();
			Response response71 = null;
			try{
				response71 = clientz.newCall(request71).execute();
				 String coin_price6 = response71.body().string(); 
				try {
				JSONObject jsonres6 = new JSONObject(coin_price6);
				JSONArray jsonArrayresval6 = jsonres6.getJSONArray("value");
				Integer jsoncode6 = jsonres6.getInt("code");
				if(jsoncode6!=0){
						System.out.println(jsoncode6);
						return null;
						}
				
				List<MyHistoryTrade> myHistoryTrades= new ArrayList<>();
				
					for (int i = 0; i < jsonArrayresval6.length(); i++) {
						JSONObject friend = jsonArrayresval6.getJSONObject(i);
						String name = friend.getString("name");
						Integer id = friend.getInt("id");
						Integer isbid = friend.getInt("isbid");
						Integer notes = friend.getInt("notes");
						String stamp = friend.getString("stamp");
						Long stamplong = Long.valueOf(stamp);
						Double price = friend.getDouble("price");
						Date dateT = new Date(stamplong*1000-7200000);
						
						
						
						DateFormat simdateform = new SimpleDateFormat("dd/MM/yyyy \n HH:mm:ss");
						simdateform.setTimeZone(TimeZone.getDefault());
						String datel =  simdateform.format(dateT);
						
						MyHistoryTrade myHistoryTrade = new MyHistoryTrade(id, isbid, notes, datel+"", name, price);
						
						myHistoryTrades.add(myHistoryTrade);
					}
					
				
				param.setListHistoryTrades(myHistoryTrades);
				return myHistoryTrades;
				}catch(JSONException e){
					System.out.println("ошибка парсера HistoryTrading : "+coin_price6);
					
				}
			}catch (IOException e) {
				
			}
		}
		return null;
	}
	private String base64_shifr(int i, int offerid, int zCoin, String DateStart, String DateEnd){
		// шиврование
		String base64 = "";
		try{

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String r = "";
			if (i == 0){			//удаление заявки
				r = login+ ';' + password+ ';' + culture+ ';' + wmid + ';' + offerid;
			}
			if (i == 1){			//постановка заявки и прайс лист
				r = login+ ';' + password+ ';' + culture+ ';' + wmid + ';' + zCoin;
			}
			if (i == 3){			//мои заявки и баланс портфеля
				r = login+ ';' + password+ ';' + culture+ ';' + wmid;
			}
			if (i == 4){			//цены на коины
				r = login+ ';' + password+ ';' + culture;
			}
			if (i == 5){			//мои сделки
				r = login+ ';' + password+ ';' + culture+ ';' + wmid + ';' + zCoin + ';' + DateStart + ';' + DateEnd;
			}
			try{

				byte[] zl = digest.digest(r.getBytes("UTF-8"));

				base64 = android.util.Base64.encodeToString(zl, android.util.Base64.NO_WRAP);
			}catch (UnsupportedEncodingException e){}
		}catch (NoSuchAlgorithmException e){}
		return base64;
	}
}
