package com.mycompany.myapp5;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
//import android.support.annotation.Nullable;
import android.widget.Toast;
import java.util.*;
import android.os.*;
import okhttp3.*;
import org.json.*;
import java.io.*;
import android.widget.*;
import android.app.*;
import android.content.*;


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
						//myThread2.setDaemon(true);
						}catch(Exception e){}
				try{
					
		myThread2.start();
		//editor = pref.edit();
		editor.putInt("fin", 0);
		editor.commit();
			//fin = 1;
		}catch (Exception e){}
		
		}
		
	}	}		, 0L,  1000);
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
	
	//do time consuming operations
	public void request (Double ty, Double ty2) {
		
		//создание объекта ответа
		OkHttpClient client = new OkHttpClient();
		String json = "{'ApiContext':{'Login':'U0bEaZLzlags7j0','Wmid':'280113070531','Culture':'ru-RU','Signature':'Y9miCjx8fgnSW6glpAuIxRBO9t3GBbZXw8vxZJ+ZP+c='}}";
		RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
		Request request = new Request.Builder()
			.url("https://api.indx.ru/api/v2/trade/Balance")
			.post(body)
			.build();
		Response response = null;
		Intent intent;
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
		String json2 = "{'ApiContext':{'Login':'U0bEaZLzlags7j0','Wmid':'280113070531','Culture':'ru-RU','Signature':'YyYa1jmzcUnsUfqDOtWGcwLMdVtQ+qWQp9KY02ds6Fo='}}";
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
		/*
	

		//создание объекта ответа
		OkHttpClient client3 = new OkHttpClient();
		String json3 = "{'ApiContext':{'Login':'U0bEaZLzlags7j0','Wmid':'280113070531','Culture':'ru-RU','Signature':'wznCOrrwzvO6vVsYnRF68utGxGWYFxFVOT6WPpjzTFM='},'Trading':{'ID':60}}";
		RequestBody body3 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json3);
		Request request3 = new Request.Builder()
			.url("https://api.indx.ru/api/v2/trade/OfferList")
			.post(body3)
			.build();
		Response response3 = null;
 		try{

			response3 = client3.newCall(request3).execute();
			//System.out.println(response2);
			//ответ тела post запроса
			String coin_price3 = response3.body().string(); 





		}
		catch (IOException e) {
		}
*/

		//создание намерения CAT
			
		String CAT_ACTION = "CAT";

		intent.setAction(CAT_ACTION);


		//intent.putExtra("intent_service_name", "tabl_price");
		intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
		//отправка широковещательного сообщения
		client = null;
		//client2 = null;
		request = null;
		request2 = null;
		sendBroadcast(intent);

		
	}
	
	
	
	
	public void data_service(){
		

	}
	
	
}
