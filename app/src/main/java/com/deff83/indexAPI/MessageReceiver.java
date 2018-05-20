package com.deff83.indexAPI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.*;
import android.app.*;
import android.content.res.*;
import android.support.v4.app.*;
import android.content.*;
import android.media.*;
import java.util.*;
import android.preference.*;
import android.text.*;




public class MessageReceiver extends BroadcastReceiver {
	
	private static Timer timer_sound;
	private static MediaPlayer player;
	SharedPreferences pref;
	SharedPreferences.Editor editor = null;
	
    @Override
    public void onReceive(Context context, Intent intent) {
		//получение файла данных

		//pref = context.getSharedPreferences("CAT", Context.MODE_PRIVATE);
		pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication());
		editor = pref.edit();
		
		int i = pref.getInt("flagtextinform", 0);
		if(i == 1){
			funcinfo(pref.getString("infor", "нет сообщения"), context);
		}
		try{
		//Toast.makeText(context, "рпсорорпс", Toast.LENGTH_SHORT).show();
		//ntent.getStringExtra("intent_service_name");
		//if (name_intent.equals("coin")){
		//	String coin_price = intent.getStringExtra("coin_price");
			//coin_prices(coin_price);
		//}
		//if (name_intent.equals("balance")){
		//player = MediaPlayer.create(context, R.raw.sound1);
		
		//получаем значение полученного сообщения, параметр price
	Double price = intent.getDoubleExtra("price",0.0);
	
	//Double price_edit = intent.getDoubleExtra("price_edit", 0.0);
	//Double price_edit2 = intent.getDoubleExtra("price_edit2", 999.9);
		

		
		
		//проверка если оповещение сработает
		if (pref.getInt("srabopov", 0) == 1){
			if (timer_sound == null){
				timer_sound = new Timer();
			timer_sound.schedule(new TimerTask(){


					@Override
					public void run() {
sound();

					}}, 0L,  18000);
					//оповецение о звуке
					
					
					
			}
			//editor.putInt("srabopov", 0);
			editor.commit();
		} else {
			
			try{

				timer_sound.cancel();}
			catch (Exception e){}
			timer_sound = null;
		}
			
			
			
	String ty_str = pref.getString("edit_price", "0.0");
	String ty_str2 = pref.getString("edit_price2", "999.9");
	Double price_edit = Double.parseDouble(ty_str);
	Double price_edit2 = Double.parseDouble(ty_str2);
	Double minus =  Math.round((price - price_edit)* 100.0) / 100.0;
		Double plus =  Math.round((price - price_edit2)* 100.0) / 100.0;
		String price_minuse;
		String price_pluse;
		if(pref.contains("price_minus")){
			price_minuse = pref.getString("price_minus", "0.5");

		} else {
			price_minuse = "0";

		}
		if(pref.contains("price_plus")){
			price_pluse = pref.getString("price_plus", "-0.5");

		} else {
			price_pluse = "0";

		}
		Double price_minuse_double = Double.parseDouble(price_minuse);
		Double price_pluse_double = Double.parseDouble(price_pluse);
		//открытие файла для записи
		editor = pref.edit();
		//проверка на ноль цены
		if (price != 0.0){
		//сравнение цены с нижней границей
			if (price <= price_edit + price_minuse_double && pref.getInt("sound_opov_gran", 0) == 0){
			sound();
			price_minuse_double = Math.round((minus - 0.05)* 100.0) / 100.0;
			
		Intent intent_receiver = new Intent(context, MainActivity.class);
		PendingIntent pi_receiver = PendingIntent.getActivity(context, 001, intent_receiver, PendingIntent.FLAG_ONE_SHOT);
		int notificationId = 001;

		NotificationCompat.Builder notificationBuilder =
            new NotificationCompat.Builder(context)
			.setContentIntent(pi_receiver)
			.setSmallIcon(R.drawable.trenddown)
			.setContentTitle(price.toString())
			.setContentText(minus.toString())
			.setTicker(minus.toString())
			.setAutoCancel(true)
		.setDefaults(Notification.DEFAULT_SOUND);

		NotificationManagerCompat notificationManager =
            NotificationManagerCompat.from(context);
		
			//editor.putInt("srabopov", 1);
			editor.commit();
		
		notificationManager.notify(notificationId, notificationBuilder.build());
			
			
		
				
			
			editor.putString("price_minus", price_minuse_double.toString());
			
		}
		//сравнение цены с верхней границей
			if (price >= price_edit2 + price_pluse_double && pref.getInt("sound_opov_gran", 0) == 0){
			sound();
			price_pluse_double = Math.round((plus + 0.05)* 100.0) / 100.0;
			
			Intent intent_receiver = new Intent(context, MainActivity.class);
			PendingIntent pi_receiver = PendingIntent.getActivity(context, 002, intent_receiver, PendingIntent.FLAG_ONE_SHOT);
			int notificationId = 002;

			NotificationCompat.Builder notificationBuilder =
				new NotificationCompat.Builder(context)
				.setContentIntent(pi_receiver)
				.setSmallIcon(R.drawable.trendup)
				.setContentTitle(price.toString())
				.setContentText(plus.toString())
				.setTicker(plus.toString())						//текст в строке заголовка
				.setDefaults(Notification.DEFAULT_SOUND)
				.setAutoCancel(true);

			NotificationManagerCompat notificationManager =
				NotificationManagerCompat.from(context);


			//editor.putInt("srabopov", 1);
			editor.commit();
			notificationManager.notify(notificationId, notificationBuilder.build());




			
			editor.putString("price_plus", price_pluse_double.toString());

			
		}
		}

		
		editor.putString("price", price.toString());
		
		//оповещение изменилось количество заявок
		int col_izm_res = pref.getInt("col_izm_res", 0);
		if (col_izm_res == 1){
			//код оповещения
			Intent intent_receiver = new Intent(context, MainActivity.class);
			PendingIntent pi_receiver = PendingIntent.getActivity(context, 003, intent_receiver, PendingIntent.FLAG_ONE_SHOT);
			int notificationId = 003;     //id оповещения

			NotificationCompat.Builder notificationBuilder =
				new NotificationCompat.Builder(context)
				.setContentIntent(pi_receiver)
				.setSmallIcon(R.drawable.srab_down)
				.setContentTitle("заявка сработала!")
				.setContentText(price.toString())
				.setTicker("заявка сработала")						//текст в строке заголовка
				.setDefaults(Notification.DEFAULT_SOUND)
				.setAutoCancel(true);

			NotificationManagerCompat notificationManager =
				NotificationManagerCompat.from(context);


			editor.putInt("srabopov", 1);
			editor.commit();
			notificationManager.notify(notificationId, notificationBuilder.build());
			
			editor.putInt("col_izm_res", 0);
		}
		if (col_izm_res == 2){
			//код оповещения
			Intent intent_receiver = new Intent(context, MainActivity.class);
			PendingIntent pi_receiver = PendingIntent.getActivity(context, 004, intent_receiver, PendingIntent.FLAG_ONE_SHOT);
			int notificationId = 004;     //id оповещения

			NotificationCompat.Builder notificationBuilder =
				new NotificationCompat.Builder(context)
				.setContentIntent(pi_receiver)
				.setSmallIcon(R.drawable.srab_up)
				.setContentTitle("поставлена заявка!")
				.setContentText(price.toString())
				.setTicker("поставлена заявка!")						//текст в строке заголовка
				.setDefaults(Notification.DEFAULT_SOUND)
				.setAutoCancel(true);

			NotificationManagerCompat notificationManager =
				NotificationManagerCompat.from(context);


			//editor.putInt("srabopov", 1);
			editor.commit();
			notificationManager.notify(notificationId, notificationBuilder.build());

			editor.putInt("col_izm_res", 0);
		}
		
		if (pref.getInt("minpriceopovfoin", 0) == 1){
			Opiration o = Opiration.getOpiration();
			
			ArrayList<Double> t = o.getlistpricex();
			Double pricebit = t.get(0);
			sound();
			//код оповещения
			Intent intent_receiver = new Intent(context, MainActivity.class);
			PendingIntent pi_receiver = PendingIntent.getActivity(context, 006, intent_receiver, PendingIntent.FLAG_ONE_SHOT);
			int notificationId = 006;     //id оповещения

			NotificationCompat.Builder notificationBuilder =
				new NotificationCompat.Builder(context)
				.setContentIntent(pi_receiver)
				.setSmallIcon(R.drawable.btc)
				.setContentTitle("цена снизилась!")
				.setContentText(pricebit.toString())
				.setTicker("цена снизилась!")						//текст в строке заголовка
				.setDefaults(Notification.DEFAULT_SOUND)
				.setAutoCancel(true);

			NotificationManagerCompat notificationManager =
				NotificationManagerCompat.from(context);


		
			editor.commit();
			notificationManager.notify(notificationId, notificationBuilder.build());
			editor.putString("edit_price_opmin", pricebit.toString());
			editor.putInt("sound_opov_price", 1);
			editor.putInt("minpriceopovfoin", 0);
			editor.commit();
		}
		if (pref.getInt("maxpriceopovfoin", 0) == 1){
			
			sound();
			Opiration o = Opiration.getOpiration();
			
			ArrayList<Double> t = o.getlistpricey();
			Double pricebit = t.get(0);
			//код оповещения
			Intent intent_receiver = new Intent(context, MainActivity.class);
			PendingIntent pi_receiver = PendingIntent.getActivity(context, 007, intent_receiver, PendingIntent.FLAG_ONE_SHOT);
			int notificationId = 007;     //id оповещения

			NotificationCompat.Builder notificationBuilder =
				new NotificationCompat.Builder(context)
				.setContentIntent(pi_receiver)
				.setSmallIcon(R.drawable.btc)
				.setContentTitle("цена поднялась!")
				.setContentText(pricebit.toString())
				.setTicker("цена поднялась!")						//текст в строке заголовка
				.setDefaults(Notification.DEFAULT_SOUND)
				.setAutoCancel(true);

			NotificationManagerCompat notificationManager =
				NotificationManagerCompat.from(context);



			editor.commit();
			notificationManager.notify(notificationId, notificationBuilder.build());
			editor.putString("edit_price_opmax", pricebit.toString());
			editor.putInt("sound_opov_price", 1);
			editor.putInt("maxpriceopovfoin", 0);
			editor.commit();
		}
		
			//editor.putString("otvet_coin", "0");
			//int activity_true = pref.getInt("activity_true", 1);
			//if (activity_true == 0){
				//editor.putInt("fin", 1);
			//	}
		
		editor.commit();
		}catch(Exception e){
			editor.putString("infor", e.toString());
			editor.putInt("flagtextinform", 1);
			editor.commit();
		}
	}
	private void sound(){
		int flag_switch_sound_opov = pref.getInt("sound_opov", 0);
		if (flag_switch_sound_opov == 0){
		//player.start();
		}
	}
	private void funcinfo(String text, Context ctxt){
		Toast.makeText(ctxt, Html.fromHtml(text), Toast.LENGTH_SHORT).show();
		editor.putInt("flagtextinform", 0);
		editor.commit();
	}
	//}
}
