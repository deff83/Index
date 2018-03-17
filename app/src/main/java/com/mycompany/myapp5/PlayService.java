package com.mycompany.myapp5;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;
import java.util.*;
import android.os.*;
import okhttp3.*;
import org.json.*;
import java.io.*;
import android.widget.*;
import android.app.*;
import android.content.*;
import android.util.*;
import org.apache.commons.codec.digest.*;
import java.security.*;


public class PlayService extends Service {
	Double ty;
	Double ty2;
	SharedPreferences pref;
	String otvet_tabl;
	String otvet_prices;
	String otvet_coin;
	SharedPreferences.Editor editor = null;
	//старт потока
	//инициализация потока post запроса
	
	
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
	//создание службы
    @Override
    public void onCreate() {
        super.onCreate();
		
		fin = 1;
        Toast.makeText(this, "Служба создана",
					   Toast.LENGTH_SHORT).show();
      
    }
	Timer timer_server;
	Integer fin;
	Thread myThread2;
	//запуск службы
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		
		pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		s_znachen();
		editor = pref.edit();
		editor.putInt("fin", 1);
		editor.commit();
		Toast.makeText(this, "старт",
					   Toast.LENGTH_SHORT).show();
		
		
					//создание run потока
		try{
			timer_server.cancel();}
		catch (Exception e){}
		timer_server = new Timer();
	
		//инициализация таймера
		timer_server.schedule(new TimerTask(){
			
			
				@Override
				public void run() {
					Runnable runnable;
					final Thread myThread2;
					 runnable = new Runnable() {
						@Override
						public void run() {

							//выполнение функции POST запроса
							request(ty, ty2);


						}


					};
					 myThread2 = new Thread(runnable);
					String ty_str = pref.getString("edit_price", "0.0");
					String ty_str2 = pref.getString("edit_price2", "0.0");
					
					ty = Double.parseDouble(ty_str);
					ty2 = Double.parseDouble(ty_str2);
					otvet_coin = pref.getString("otvet_coin", "0");
					//уничтожение ссех потоков
					
					
						
				
					//____________
					fin = pref.getInt("fin",1);
					
					if (fin == 1){
		try{
						myThread2.setDaemon(true);
						}catch(Exception e){}
				try{
					
		myThread2.start();
		//editor = pref.edit();
		editor.putInt("fin", 0);
		editor.commit();
			//fin = 1;
		}catch (Exception e){}
		
		}
		
	}	}		, 0L, 6L * 1000);
		//Toast.makeText(this, "Служба запущена", Toast.LENGTH_SHORT).show();
       
		return Service.START_STICKY;
    }
	//остановка службы
    @Override
    public void onDestroy() {
		
       
		timer_server.cancel();
		
        Toast.makeText(this, "Служба остановлена",
					   Toast.LENGTH_SHORT).show();
		super.onDestroy();
        
    }
	OkHttpClient client;
	Intent intent;
	String login;
	String password;
	String culture;
	String wmid;
	String signature;
	Integer zCoin;
	public void s_znachen(){
		zCoin = 60;
		login = pref.getString("login", "");
		password = pref.getString("password", "");
		culture = "ru-RU";
		wmid = pref.getString("wmid", "");
		
	}
	//do time consuming operations
	public void request (Double ty, Double ty2) {
		
		
		//создание объекта ответа
		client = new OkHttpClient();
		String json = "{'ApiContext':{'Login':'"+login+"','Wmid':'"+wmid+"','Culture':'"+culture+"','Signature':'Y9miCjx8fgnSW6glpAuIxRBO9t3GBbZXw8vxZJ+ZP+c='}}";
		RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
		Request request = new Request.Builder()
			.url("https://api.indx.ru/api/v2/trade/Balance")
			.post(body)
			.build();
		Response response = null;
		
		intent = new Intent();
 		try{
			
			response = client.newCall(request).execute();
			//System.out.println(response);
			//ответ тела post запроса
			String answir = response.body().string(); 
			
			try{
			
				JSONObject jsonObject;
				jsonObject = new JSONObject(answir);
				JSONObject value = jsonObject.getJSONObject("value");
				JSONObject balance = value.getJSONObject("balance");
				Double price = balance.getDouble("price");
				//создание намерения CAT
		
				intent.putExtra("price_edit", ty);
				intent.putExtra("price_edit2", ty2);
				intent.putExtra("price", price);
			//	intent.putExtra("intent_service_name", "balance");
				//intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
				//отправка широковещательного сообщения
				
				jsonObject = null;
				
				}
				catch (JSONException e){
					
				}
		}
		catch (Exception e) {
		}
		
	
	//post запрос на цены коинов
	//do time consuming operations
	

		//создание объекта ответа
		//OkHttpClient client2 = new OkHttpClient();
		String json2 = "{'ApiContext':{'Login':'"+login+"','Wmid':'"+wmid+"','Culture':'"+culture+"','Signature':'YyYa1jmzcUnsUfqDOtWGcwLMdVtQ+qWQp9KY02ds6Fo='}}";
		RequestBody body2 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json2);
		Request request2 = new Request.Builder()
			.url("https://api.indx.ru/api/v2/trade/Tools")
			.post(body2)
			.build();
		Response response2 = null;
		try{
 		try{

			response2 = client.newCall(request2).execute();
			//System.out.println(response2);
			//ответ тела post запроса
	String coin_price2 = response2.body().string(); 

			
				
				
			
			
				intent.putExtra("coin_price", coin_price2);
		//	intent.putExtra("intent_service_name", "coin");
				//intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
				//отправка широковещательного сообщения
			
			
		}
		catch (IOException e) {
		}
		}
		catch (Exception e) {
		}
		
	

		//создание объекта ответа
		//OkHttpClient client3 = new OkHttpClient();
		String json3 = "{'ApiContext':{'Login':'"+login+"','Wmid':'"+wmid+"','Culture':'"+culture+"','Signature':'wznCOrrwzvO6vVsYnRF68utGxGWYFxFVOT6WPpjzTFM='},'Trading':{'ID':"+zCoin+"}}";
		RequestBody body3 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json3);
		Request request3 = new Request.Builder()
			.url("https://api.indx.ru/api/v2/trade/OfferList")
			.post(body3)
			.build();
		Response response3 = null;
 		try{

			response3 = client.newCall(request3).execute();
			//System.out.println(response2);
			//ответ тела post запроса
			final String coin_price3 = response3.body().string(); 

		




		
		
		int count_tabl = pref.getInt("count_tabl", 0);
		int tabl_hight = pref.getInt("tabl_hight", 10);
		//Runnable runnable45 = new Runnable() {
		//	@Override
			//public void run() {
				try{
					JSONObject jsonObject;
					jsonObject = new JSONObject(coin_price3);

					JSONArray jsonArray = jsonObject.getJSONArray("value");
					String cfv = String.valueOf( jsonArray.length());
					Double z = 0.0;
					Integer z_col, z_offerid;
					for(int i= 0; i < tabl_hight; i++){
						JSONObject json_54 = jsonArray.getJSONObject(i);
						z = json_54.getDouble("price");
						z_col = json_54.getInt("notes");
						z_offerid = json_54.getInt("offerid");
						editor.putString("tabl" + i, z.toString());
						editor.putString("tabl_notes" + i, z_col.toString());
						editor.putString("tabl_offerid" + i, z_offerid.toString());
					}
					int j = tabl_hight;
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
					for(int i= 0; i < tabl_hight; i++){
							JSONObject json_45 = jsonArray.getJSONObject(j+i);
							y = json_45.getDouble("price");
							y_col = json_45.getInt("notes");
							y_offerid = json_45.getInt("offerid");
							editor.putString("tabl_prod" + i, y.toString());
							editor.putString("tabl_notes_prod" + i, y_col.toString());
							editor.putString("tabl_offerid_prod" + i, y_offerid.toString());
					}
					editor.commit();
						ystr = y.toString();
				
		//			intent.putExtra("list_price", pref.getString("tabl0", "ошибка в значении"));
				
				}catch (JSONException e){}
			//}};
		//Thread thread45 = new Thread(runnable45);

		//thread45.start();
			
		}catch (IOException e) {
			intent.putExtra("list_price", "ошибка");
		}
		
		
		//запрос мои заявки
		
		//OkHttpClient client3 = new OkHttpClient();
		String json4 = "{'ApiContext':{'Login':'"+login+"','Wmid':'"+wmid+"','Culture':'"+culture+"','Signature':'Y9miCjx8fgnSW6glpAuIxRBO9t3GBbZXw8vxZJ+ZP+c='}}";
		RequestBody body4 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json4);
		Request request4 = new Request.Builder()
			.url("https://api.indx.ru/api/v2/trade/OfferMy")
			.post(body4)
			.build();
		Response response4 = null;
 		try{

			response4 = client.newCall(request4).execute();
			//System.out.println(response2);
			//ответ тела post запроса
			final String coin_price4 = response4.body().string(); 
			try{
				JSONObject jsonObject;
				jsonObject = new JSONObject(coin_price4);
				JSONArray jsonArray2 = jsonObject.getJSONArray("value");
				String cfv = String.valueOf( jsonArray2.length());
				int col_z = pref.getInt("col_z",0);
				if (pref.getInt("col_izm", 0) == 0){
					if (col_z == jsonArray2.length()){}else{
						editor.putInt("col_izm", 1);
					}
				}
				if (pref.getInt("col_izm_res", 0) == 0){
					if (col_z == jsonArray2.length()){}else{
						if (col_z < jsonArray2.length()){
							editor.putInt("col_izm_res", 2);
						} else {
							editor.putInt("col_izm_res", 1);
						}
					}
				}
				editor.putInt("col_z", jsonArray2.length());
				String x = "net";
				for (int i=0; i < jsonArray2.length(); i++){
					JSONObject json_my_offer = jsonArray2.getJSONObject(i);
					Integer toolid = json_my_offer.getInt("toolid");
					String my_offer_tool = "my_offer" +toolid + "_" + i;
					Integer offerid = json_my_offer.getInt("offerid");
					editor.putInt(my_offer_tool + "offerid", offerid);
					String name = json_my_offer.getString("name");
					editor.putString(my_offer_tool+"name", name);
					Integer kind = json_my_offer.getInt("kind");
					editor.putInt(my_offer_tool+"kind",kind);
					String price = json_my_offer.getString("price");
					editor.putString(my_offer_tool+"price",price);
					Integer notes = json_my_offer.getInt("notes");
					editor.putInt(my_offer_tool+"notes",notes);
					x = my_offer_tool;
				}
				
				
				
				
				
			}
			catch (JSONException e){}
		}catch (IOException e) {
			//intent.putExtra("l", "ошибка");
		}
		//удаление заявки
		int del = pref.getInt("del", 0);
		if (del == 1){
			del_zayvki(0);
		}
		//удаление всех заявок сразу
		int del_all = pref.getInt("del_all", 0);
		if (del_all == 1){
			int col_z = pref.getInt("col_z", 0);
			for (int i=0; i<col_z; i++){
				del_zayvki(i);
			}
			editor.putInt("del_all", 0);
		} 
		//постановка заявки
		int add = pref.getInt("add", 0);
		if (add == 1){
			add_zayvk();
		}
		request = null;
		request2 = null;
		zapisi();
		
	}
	
	public void zapisi(){
		//создание намерения CAT
		String CAT_ACTION = "CAT";
		intent.setAction(CAT_ACTION);
		//intent.putExtra("intent_service_name", "tabl_price");
		intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
		//отправка широковещательного сообщения
		client = null;
		//client2 = null;
		
		//int res = pref.getInt("res", 0);
		//if (res == 0){ 
		sendBroadcast(intent);
		//	}
		editor.putInt("fin", 1);
		editor.commit();
	}
	
	public void del_zayvki(int j_del){
		
			//удаление заявки
			//OkHttpClient client3 = new OkHttpClient();
			int offerid = pref.getInt("my_offer" + zCoin + "_" + j_del + "offerid", 0);
			if (offerid != 0){
				
				String base64 = base64_shifr(0, offerid);
				signature = base64;

				String json5 = "{'ApiContext':{'Login':'" + login + "','Wmid':'" + wmid + "','Culture':'" + culture + "','Signature':'" + signature + "'},'OfferId':'"+ offerid + "'}";
				RequestBody body5 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json5);
				Request request5 = new Request.Builder()
					.url("https://api.indx.ru/api/v2/trade/OfferDelete")
					.post(body5)
					.build();
				Response response5 = null;
 				try{
					response5 = client.newCall(request5).execute();
					final String coin_price5 = response5.body().string(); 
					//intent.putExtra("list_price", coin_price5.toString());
				}catch (IOException e) {
					//intent.putExtra("l", "ошибка");
				}
				editor.putInt("my_offer" + zCoin + "_" + 0 + "offerid", 0);
			}
			editor.putInt("del", 0);

	}
	public void add_zayvk(){
		editor.putInt("add", 0);
		editor.commit();
		String base64 = base64_shifr(1, 0);
		String signature6 = base64;
		String isbid = "";
		Double price_dialog = Double.parseDouble(pref.getString("price_dialog", "0.0"));
		int notes_dialog = pref.getInt("notes_dialog",1);
		
		Integer typ_oper = pref.getInt("typ_oper", 0);
		if (typ_oper == 0){
			isbid = "false";
		}
		if (typ_oper == 1){
			isbid = "true";
		}
		String isanonymous = "true";
		 
		//{"ApiContext":{"Login":"U0bEaZLzlags7j0","Wmid":"280113070531","Culture":"ru-RU","Signature":"wznCOrrwzvO6vVsYnRF68utGxGWYFxFVOT6WPpjzTFM="},"Offer":{
		String json6 = "{'ApiContext':{'Login':'"+login+"','Wmid':'"+wmid+"','Culture':'"+culture+"','Signature':'"+signature6+"'},'Offer':{'ID':"+zCoin+",'Count':"+notes_dialog+",'IsAnonymous':"+isanonymous+",'IsBid':"+isbid+",'Price':"+price_dialog+"}}";
		//	String json6 = "{'ApiContext':{'Login':'" + login + "','Wmid':'" + wmid + "','Culture':'" + culture + "','Signature':'" + signature + "'},'OfferId':{'ID':"+60+",'Count':"+1+",'IsAnonymous':true,'IsBid':true,'Price':"+ 6.0000 +"}}";
		RequestBody body6 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json6);
		Request request6 = new Request.Builder()
			.url("https://api.indx.ru/api/v2/trade/OfferAdd")
			.post(body6)
			.build();
		Response response6 = null;
		try{
			response6 = client.newCall(request6).execute();
			final String coin_price6 = response6.body().string(); 
			try {
			JSONObject jsonres6 = new JSONObject(coin_price6);
			JSONObject jsonresval6 = jsonres6.getJSONObject("value");
			String jsoncode6 = jsonresval6.getString("Code");
			if (jsoncode6.equals("0")){
			intent.putExtra("list_price", "успешно поставлена");
			}else{intent.putExtra("list_price", "ошибка постановки заявки");}
			}catch(JSONException e){}
		}catch (IOException e) {
			//intent.putExtra("l", "ошибка");
		}
	
		
	}
	public String base64_shifr(int i, int offerid){
		// шиврование
		String base64 = "";
		try{

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String r = "";
			if (i == 0){			//удаление заявки
			 r = login+ ';' + password+ ';' + culture+ ';' + wmid + ';' + offerid;
			}
			if (i == 1){			//постановка заявки
				r = login+ ';' + password+ ';' + culture+ ';' + wmid + ';' + zCoin;
			}
			try{

				byte[] zl = digest.digest(r.getBytes("UTF-8"));

				base64 = Base64.encodeToString(zl, Base64.NO_WRAP);
			}catch (UnsupportedEncodingException e){}
		}catch (NoSuchAlgorithmException e){}
		return base64;
	}
	public void data_service(){
		

	}
	
	
}
