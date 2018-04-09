package com.deff83.indexAPI;
	import android.app.*;
	import android.os.*;
	import android.content.*;
	import android.widget.*;
	import android.view.View.*;
	import android.view.*;
	import android.view.inputmethod.*;
	import android.view.ViewDebug.*;
	import java.util.*;

	public class Functred_dialog extends Activity
	{
		SharedPreferences pref;
		Context context = null;
		SharedPreferences.Editor editor = null;
		private LinearLayout interceptor;
		Integer btn_z;
		Integer zCoin;
		Integer colstoplos;
		EditText stoplos_edit;
		//алушатель кнопки
		OnClickListener listbutton;
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
			editor = pref.edit();
			final Set<String> h = pref.getStringSet("z_stoplos",  new HashSet<String>());
			editor.putInt("add", 0);
			editor.commit();
			zCoin = pref.getInt("zCoin", 60);
			// TODO: Implement this method
			super.onCreate(savedInstanceState);
			//устанока титла окна
			final int i = pref.getInt("idCoin", 0);
			String title = pref.getString("name_coin" + i, "");
			setTitle("Функции"+ title);
			setContentView(R.layout.funct_red);
			int j_z = pref.getInt("j_redz", 0);
			//поля на которые нажали
			final Integer z_coin = pref.getInt("my_offer_" + j_z, 0);
			final int z_id =pref.getInt("my_offer_" + j_z + "offerid", 0);
			final String z_price = pref.getString("my_offer_"+j_z+"price", "0.0");
			final int z_notes = pref.getInt("my_offer_" + j_z+"notes", 0);
			stoplos_edit = (EditText) findViewById(R.id.stoplosedit);
			if (h.contains(z_id+"")==false){
			Double x = 0.0;
			try{x = Double.parseDouble(pref.getString("tabl0", "0"));}catch(Exception e){}
			stoplos_edit.setText(String.format(Locale.US, "%.4f", (x*0.98)));
			}else{
				stoplos_edit.setText(pref.getString("stoplosust" +z_id, "0"));
			}
			//кнопка установить цену с минимальным отрывом перед стаканом
			Button but_min= (Button) findViewById(R.id.refresh_z);
			but_min.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// нажато "редактировать заявку"
						Toast.makeText(getBaseContext(), "подождите", Toast.LENGTH_SHORT).show();
						final Thread perepostthread;
						final String price_dialog_str_mind = "ошибка";
						Runnable runnablex = new Runnable() {
							@Override
							public void run() {
						int kind_upz = pref.getInt("kind_upz", 0);
						String price_dialog_str_min = "---";
						Double jk;
						String isbid = "";
						Opiration o = Opiration.getOpiration();
						
						ArrayList<Double> g = new ArrayList();
						if (kind_upz == 0){
							o.pricelist(z_coin, 1);
							
							g = o.getlistpricex();
							price_dialog_str_min = g.get(0).toString();
							jk = Double.parseDouble(price_dialog_str_min)+0.0001;
							price_dialog_str_min = String.format(Locale.US, "%.4f",jk);
							isbid = "true";
						}
						if(kind_upz == 1){
							o.pricelist(z_coin, 1);
							
							g = o.getlistpricey();
							price_dialog_str_min = g.get(0).toString();
							jk = Double.parseDouble(price_dialog_str_min)-0.0001;
							price_dialog_str_min = String.format(Locale.US, "%.4f",jk);
							isbid = "false";
						}
						int x = o.del(z_id);
						if (x==1){
							//	Toast.makeText(getApplicationContext(), "Цена постановки: "+price_dialog_str_mind, Toast.LENGTH_SHORT).show();
						o.add(z_coin, z_notes, isbid,Double.parseDouble(price_dialog_str_min));
						}
						//editor.putInt("j_del", z_id);
						//editor.putInt("del", 1);
						//editor.putInt("task2", 1);
						//editor.putString("pricebuyauto", price_dialog_str_min);
						//editor.putInt("coinauto", z_coin);
							//	System.out.println(z_coin);
						//editor.commit();
						
						}
						};
						
						
						perepostthread = new Thread(runnablex);
						perepostthread.start();
						finish();
					}
				});
				//стоп лос
		Button stoplos = (Button) findViewById(R.id.stop_los);
		stoplos.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Double stoplosp = 0.0;
				try{
				stoplosp = Double.parseDouble(stoplos_edit.getText().toString());
				} catch (Exception e){}
				//colstoplos = pref.getInt("col_stop_los", 0);
				//if (pref.getInt("stoplosflag" + z_id, 0) == 0){
					
				
				//editor.putString("stoplos" + colstoplos, stoplos_edit.getText().toString());
				
				if (h.contains(z_id+"")==false){
				h.add(z_id+"");
					editor.putStringSet("z_stoplos", h);
					editor.putString("stoplosprice"+z_id, z_price);
					editor.putInt("stoplosnotes"+z_id, z_notes);
					editor.putInt("stoploscoin" + z_id, z_coin);
					editor.putString("stoplosust"+z_id, stoplos_edit.getText().toString());
					editor.commit();
					Toast.makeText(getApplicationContext(), ""+ z_id, Toast.LENGTH_SHORT).show();
				}
				//editor.putStringSet();
				//editor.putInt("stoplosflag" + z_id, 1); 
				//editor.commit();
				//}
				
				finish();
			}
		}
		);
			Button stoplosdel = (Button) findViewById(R.id.stop_los_del);
			stoplosdel.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v){
						if (h.contains(z_id+"")){
							h.remove(z_id+"");
							editor.remove("stoplosprice"+z_id);
							editor.remove("stoplosnotes"+z_id);
							editor.remove("stoplosust"+z_id);
							editor.remove("stoploscoin"+z_id);
							editor.putStringSet("z_stoplos", h);
							editor.commit();
						}
					}
			});
		
			//слушатель edit, установка акиивности вызов клавиатуры
			OnTouchListener on_touch_listener3 = new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						v.setFocusableInTouchMode(true);
					}
					return v.performClick();
				}
			};
			//вешаем на каждый edit один слушатель
			stoplos_edit.setOnTouchListener(on_touch_listener3);
		}

		@Override
		public void finish()
		{
			
			// TODO: Implement this method
			super.finish();
		}

		@Override
		protected void onStart()
		{

			// TODO: Implement this method
			super.onStart();
		}

		@Override
		protected void onStop()
		{
			// TODO: Implement this method
			super.onStop();
		}



	}
