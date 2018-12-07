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


public class Receiver  implements ISomeModel {
	static SharedPreferences pref;
	
	SharedPreferences.Editor editor = null;
	private Context context;
	private int col_zOLD = 0;
	public Receiver(Context context){
		this.context = context;
		pref = context.getSharedPreferences("CAT", Context.MODE_PRIVATE);
		
		editor = pref.edit();
		col_zOLD = pref.getInt("colMyZ", 0);
	}
	@Override
	public void doUpdate(int argum){
		//Toast.makeText(this, "doUpdate", Toast.LENGTH_SHORT).show();
		if(pref.getInt("sound_opov", 0)==0){
			switch(argum){
				case 0:
				
				
				break;
				case 1:
				break;
				case 2: //
					List<MyZayvkiForTable> listMyZayvkiForTable = Parametr.getParametr().getMyZayvkiForTable();
					int col_z = listMyZayvkiForTable.size();
					col_zOLD = pref.getInt("colMyZ", 0);
					if(col_z>col_zOLD)doNotify(001, R.drawable.srab_up,"поставлена заявка!", "++++");
					if(col_z<col_zOLD)doNotify(002, R.drawable.srab_down,"заявка сработала!", "----");
					
					
					editor.putInt("colMyZ", col_z);
					editor.commit();
					
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
	
	}
	private void doNotify(int number, int res, String texttitle, String text){
		Intent intent_receiver = new Intent(context, MainActivity.class);
		PendingIntent pi_receiver = PendingIntent.getActivity(context, number, intent_receiver, PendingIntent.FLAG_ONE_SHOT);
		int notificationId = number;

		NotificationCompat.Builder notificationBuilder =
            new NotificationCompat.Builder(context)
			.setContentIntent(pi_receiver)
			.setSmallIcon(res)
			.setContentTitle(texttitle)
			.setContentText(text)
			.setTicker(texttitle)
			.setAutoCancel(true)
		.setDefaults(Notification.DEFAULT_SOUND);

		NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
		notificationManager.notify(notificationId, notificationBuilder.build());
	}
}



