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
		
		//алушатель кнопки
		OnClickListener listbutton;
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
			editor = pref.edit();
			editor.putInt("add", 0);
			editor.commit();
			zCoin = pref.getInt("zCoin", 60);
			// TODO: Implement this method
			super.onCreate(savedInstanceState);
			//устанока титла окна
			int i = pref.getInt("idCoin", 0);
			String title = pref.getString("name_coin" + i, "");
			setTitle("Функции"+ title);
			setContentView(R.layout.funct_red);
			int j_z = pref.getInt("j_redz", 0);
			final int z_id =pref.getInt("my_offer_" + j_z + "offerid", 0);
		
			//кнопка установить цену с минимальным отрывом перед стаканом
			Button but_min= (Button) findViewById(R.id.refresh_z);
			but_min.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// нажато "редактировать заявку"
						Toast.makeText(getBaseContext(), "подождите", Toast.LENGTH_SHORT).show();
						int kind_upz = pref.getInt("kind_upz", 0);
						String price_dialog_str_min = "---";
						Double jk;
						if (kind_upz == 0){

							price_dialog_str_min = pref.getString("tabl0", "0");
							jk = Double.parseDouble(price_dialog_str_min)+0.0001;
							price_dialog_str_min = String.format(Locale.US, "%.4f",jk);
						}
						if(kind_upz == 1){

							price_dialog_str_min = pref.getString("tabl_prod0", "999");
							jk = Double.parseDouble(price_dialog_str_min)-0.0001;
							price_dialog_str_min = String.format(Locale.US, "%.4f",jk);
						}
						editor.putInt("j_del", z_id);
						editor.putInt("del", 1);
						editor.putInt("task2", 1);
						editor.putString("pricebuyauto", price_dialog_str_min);
						editor.commit();
						Toast.makeText(getApplicationContext(), "Цена постановки: "+price_dialog_str_min, Toast.LENGTH_SHORT).show();
						finish();
					}
				});
		


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
