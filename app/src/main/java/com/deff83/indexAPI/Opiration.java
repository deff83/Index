package com.deff83.indexAPI;
import android.content.*;
import android.app.*;
import java.security.*;
import android.util.*;
import java.io.*;
import okhttp3.*;
import org.json.*;
import android.widget.*;
import java.util.*;
import android.preference.*;
//singleton
public class Opiration 
{
	private String login;
	private String wmid;
	private String password;
	private String culture;
	
	private static Opiration opiration;
	private static OkHttpClient clientx;
	private static OkHttpClient clienty;
	private static OkHttpClient clientz;
	
	private ArrayList<Double> pricelistx;
	
	public ArrayList getlistpricex(){
		return pricelistx;
	}
	private ArrayList<Double> pricelisty;
	public ArrayList getlistpricey(){
		return pricelisty;
	}
	private Opiration(){
		
	}
	public void inicialisation (String login,String wmid,String password,String culture){
		this.login = login;
		this.wmid = wmid;
		this.password = password;
		this.culture = culture;
		
	}
	public static Opiration getOpiration(){
		
		if (opiration == null){
			clientx = new OkHttpClient();
			clienty = new OkHttpClient();
			clientz = new OkHttpClient();
			opiration = new Opiration();
		}
		
		return opiration;
	}
	
	//добавление заявки
	public synchronized int add(int zCoinadd, int notes_dialog, String isbid, Double price_dialog){
	
		
		String signaturex = base64_shifr(1, 0, zCoinadd);
		String isanonymous = "true";
		String jsonx = "{'ApiContext':{'Login':'"+login+"','Wmid':'"+wmid+"','Culture':'"+culture+"','Signature':'"+signaturex
		+"'},'Offer':{'ID':"+zCoinadd+",'Count':"+notes_dialog+",'IsAnonymous':"+isanonymous+",'IsBid':"+isbid+",'Price':"+price_dialog+"}}";
		//	String json6 = "{'ApiContext':{'Login':'" + login + "','Wmid':'" + wmid + "','Culture':'" + culture + "','Signature':'" + signature + "'},'OfferId':{'ID':"+60+",'Count':"+1+",'IsAnonymous':true,'IsBid':true,'Price':"+ 6.0000 +"}}";
		RequestBody bodyx = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonx);
		Request requestx = new Request.Builder()
			.url("https://api.indx.ru/api/v2/trade/OfferAdd")
			.post(bodyx)
			.build();
		Response responsex = null;
		System.out.println();
		try{
			responsex = clientx.newCall(requestx).execute();
			final String coin_pricex = responsex.body().string(); 
			try {
				JSONObject jsonresx = new JSONObject(coin_pricex);
				JSONObject jsonresvalx = jsonresx.getJSONObject("value");
				String jsoncode6 = jsonresvalx.getString("Code");
				if (jsoncode6.equals("0")){
					
					return 1;
				}else{return 2;
				}
			}catch(JSONException e){}
		}catch (IOException e) {
			//intent.putExtra("l", "ошибка");
		}
			
		return 0;
	}
	
	public synchronized int del(int offerid){
		//удаление заявки
		//OkHttpClient client3 = new OkHttpClient();
		
		if (offerid != 0){

			String base64 = base64_shifr(0, offerid, 0);
			String signature = base64;

			String json5 = "{'ApiContext':{'Login':'" + login + "','Wmid':'" + wmid + "','Culture':'" + culture + "','Signature':'" + signature + "'},'OfferId':'"+ offerid + "'}";
			RequestBody body5 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json5);
			Request request5 = new Request.Builder()
				.url("https://api.indx.ru/api/v2/trade/OfferDelete")
				.post(body5)
				.build();
			Response response5 = null;
			try{
				response5 = clienty.newCall(request5).execute();
				final String coin_price5 = response5.body().string(); 
				try{
					JSONObject jsonres5 = new JSONObject(coin_price5);
					JSONObject jsonresval5 = jsonres5.getJSONObject("value");
					String jsoncode5 = jsonresval5.getString("Code");
					if (jsoncode5.equals("0")){
						//Toast.makeText(, "успешно поставлена", Toast.LENGTH_SHORT).show();
						return 1;
						//intent.putExtra("list_price", coin_price5.toString());
					}else{return 2;}
				}catch(JSONException e){}
			}catch (IOException e) {
				//intent.putExtra("l", "ошибка");
			}
			
		}
		
	
		return 0;
	}
	
	public synchronized void pricelist(int zCoin, int size){
		//запрос прайс листа
		String signature_baz = base64_shifr(1, 0, zCoin);
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
			final String coin_price3 = response3.body().string(); 

			try{
				JSONObject jsonObject;
				jsonObject = new JSONObject(coin_price3);

				JSONArray jsonArray = jsonObject.getJSONArray("value");
				String cfv = String.valueOf( jsonArray.length());
				Double z = 0.0;
				Integer z_col, z_offerid;
				pricelistx = new ArrayList();
				for(int i= 0; i < size; i++){
					JSONObject json_54 = jsonArray.getJSONObject(i);
					z = json_54.getDouble("price");
					z_col = json_54.getInt("notes");
					z_offerid = json_54.getInt("offerid");
					pricelistx.add(z);
				}
				int j = size;
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
				pricelisty = new ArrayList();
				for(int i= 0; i < size; i++){
					JSONObject json_45 = jsonArray.getJSONObject(j+i);
					y = json_45.getDouble("price");
					y_col = json_45.getInt("notes");
					y_offerid = json_45.getInt("offerid");
					pricelisty.add(y);
				}

				
				

				//			intent.putExtra("list_price", pref.getString("tabl0", "ошибка в значении"));

			}catch (JSONException e){}
			//}};
			//Thread thread45 = new Thread(runnable45);

			//thread45.start();

		}catch (IOException e) {
			//intent.putExtra("list_price", "ошибка");
		}
		
	}
	
	public String base64_shifr(int i, int offerid, int zCoin){
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
			try{

				byte[] zl = digest.digest(r.getBytes("UTF-8"));

				base64 = Base64.encodeToString(zl, Base64.NO_WRAP);
			}catch (UnsupportedEncodingException e){}
		}catch (NoSuchAlgorithmException e){}
		return base64;
	}
	
}
