package com.deff83.indexAPI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.*;
import android.app.*;
import android.content.res.*;
import android.support.v4.app.*;
import android.content.*;




public class MessageReceiver extends BroadcastReceiver {
	
	
	
	SharedPreferences.Editor editor = null;
	
    @Override
    public void onReceive(Context context, Intent intent) {
		
		
	
		//String name_intent = intent.getStringExtra("intent_service_name");
		//if (name_intent.equals("coin")){
		//	String coin_price = intent.getStringExtra("coin_price");
			//coin_prices(coin_price);
		//}
		//if (name_intent.equals("balance")){
		
		
		//получаем значение полученного сообщения, параметр price
	Double price = intent.getDoubleExtra("price",0.0);
	Double price_edit = intent.getDoubleExtra("price_edit", 0.0);
	Double price_edit2 = intent.getDoubleExtra("price_edit2", 999.9);
		Double minus =  Math.round((price - price_edit)* 100.0) / 100.0;
		Double plus =  Math.round((price - price_edit2)* 100.0) / 100.0;
		//получение файла данных
		SharedPreferences 
			pref = context.getSharedPreferences("CAT", Context.MODE_PRIVATE);
		String price_minuse;
		String price_pluse;
		if(pref.contains("price_minus")){
			price_minuse = pref.getString("price_minus", "0");

		} else {
			price_minuse = "0";

		}
		if(pref.contains("price_plus")){
			price_pluse = pref.getString("price_plus", "999.9");

		} else {
			price_pluse = "999.9";

		}
		Double price_minuse_double = Double.parseDouble(price_minuse);
		Double price_pluse_double = Double.parseDouble(price_pluse);
		//открытие файла для записи
		editor = pref.edit();
		//проверка на ноль цены
		if (price != 0.0){
		//сравнение цены с нижней границей
		if (price <= price_edit + price_minuse_double){
			
			price_minuse_double = Math.round((minus - 0.05)* 100.0) / 100.0;
			
		Intent intent_receiver = new Intent(context, MainActivity.class);
		PendingIntent pi_receiver = PendingIntent.getActivity(context, 001, intent_receiver, PendingIntent.FLAG_ONE_SHOT);
		int notificationId = 001;

		NotificationCompat.Builder notificationBuilder =
            new NotificationCompat.Builder(context)
			.setContentIntent(pi_receiver)
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentTitle(price.toString())
			.setContentText(minus.toString())
			.setTicker(minus.toString())
			.setAutoCancel(true)
		.setDefaults(Notification.DEFAULT_SOUND);

		NotificationManagerCompat notificationManager =
            NotificationManagerCompat.from(context);
		
		
		
		notificationManager.notify(notificationId, notificationBuilder.build());
			
			
		
				
			
			editor.putString("price_minus", price_minuse_double.toString());
			
		}
		//сравнение цены с верхней границей
		if (price >= price_edit2 + price_pluse_double){
			
			price_pluse_double = Math.round((plus + 0.05)* 100.0) / 100.0;
			
			Intent intent_receiver = new Intent(context, MainActivity.class);
			PendingIntent pi_receiver = PendingIntent.getActivity(context, 002, intent_receiver, PendingIntent.FLAG_ONE_SHOT);
			int notificationId = 002;

			NotificationCompat.Builder notificationBuilder =
				new NotificationCompat.Builder(context)
				.setContentIntent(pi_receiver)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(price.toString())
				.setContentText(plus.toString())
				.setTicker(plus.toString())						//текст в строке заголовка
				.setDefaults(Notification.DEFAULT_SOUND)
				.setAutoCancel(true);

			NotificationManagerCompat notificationManager =
				NotificationManagerCompat.from(context);



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
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("заявка сработала!")
				.setContentText(price.toString())
				.setTicker("заявка сработала")						//текст в строке заголовка
				.setDefaults(Notification.DEFAULT_SOUND)
				.setAutoCancel(true);

			NotificationManagerCompat notificationManager =
				NotificationManagerCompat.from(context);



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
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("поставлена заявка!")
				.setContentText(price.toString())
				.setTicker("поставлена заявка!")						//текст в строке заголовка
				.setDefaults(Notification.DEFAULT_SOUND)
				.setAutoCancel(true);

			NotificationManagerCompat notificationManager =
				NotificationManagerCompat.from(context);



			notificationManager.notify(notificationId, notificationBuilder.build());

			editor.putInt("col_izm_res", 0);
		}
		
		
		
		
			//editor.putString("otvet_coin", "0");
			//int activity_true = pref.getInt("activity_true", 1);
			//if (activity_true == 0){
				//editor.putInt("fin", 1);
			//	}
		
		editor.commit();
	}
	//}
}
