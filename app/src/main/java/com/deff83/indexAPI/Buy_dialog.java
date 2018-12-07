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
import android.preference.*;

public class Buy_dialog extends Activity
{
	private Parametr param = Parametr.getParametr();
	
	private LinearLayout interceptor;
	
	private Integer zCoin;
	private Integer btn_z;
	private Integer kind;
	private EditText price_dialog;
	private EditText notes_dialog;
	//алушатель кнопки
	private OnClickListener listbutton;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		//pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		
		zCoin = param.getzCoinMain();
		btn_z = param.getBuysell();
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		
		setTitle("title");
		setContentView(R.layout.buy_dialog);
		//this.setFinishOnTouchOutside(false);
		//инициализация едита цены
		price_dialog = (EditText) findViewById(R.id.price_dialog);
		notes_dialog = (EditText) findViewById(R.id.notes_dialog);
		
		//слушатель,  layot
		interceptor = (LinearLayout) findViewById(R.id.lin_dialog);
		interceptor.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
						price_dialog.setFocusable(false);
						notes_dialog.setFocusable(false);
						
					}
					return v.performClick();
				}
			});
		//слушатель edit, установка акиивности вызов клавиатуры
		OnTouchListener on_touch_listener = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setFocusableInTouchMode(true);
				}
				return v.performClick();
			}
		};
		//вешаем на каждый edit один слушатель
		price_dialog.setOnTouchListener(on_touch_listener);
		notes_dialog.setOnTouchListener(on_touch_listener);
		//кнопка отмена
		Button close_button = (Button) findViewById(R.id.cancel);
		close_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		//кнопка отправить заявку
		Button but_ok = (Button) findViewById(R.id.ok);
		but_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String type = "false";
					if(kind == 0){
						
					}else{type = "true";};
					int notes = 0;
					try{
						notes = Integer.valueOf(notes_dialog.getText()+"");
					}catch(Exception e){
					}
					AddOrder addorder = new AddOrder(type, zCoin, notes, price_dialog.getText()+"");
					
					param.addDeystvie(new Deystvie(addorder, 0, 1));
					
					finish();
				}
			});
			//кнопка ---- попытка бота
			Button ok2 = (Button) findViewById(R.id.ok2);
		ok2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					/*
							AlertDialog.Builder builder = new AlertDialog.Builder(Buy_dialog.this);
							builder.setTitle("Bot")
								.setMessage("бот вкл выкл")    
								.setCancelable(true)
								.setNegativeButton("вкл",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										if (pref.getInt("bot", 0) == 0){
										// нажато "удалить заявку"
										editor.putInt("bot", 1); //флаг бот включен
										editor.putInt("botzcoin", zCoin);//передаем боту id коина где ботить
										Toast.makeText(getBaseContext(), "Бот запущен",
													   Toast.LENGTH_SHORT).show();
										editor.commit();
										}
									}
								})
								.setPositiveButton("выкл", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										// нажато "редактировать заявку"
										if (pref.getInt("bot", 0) == 1){
										editor.putInt("bot", 0);
											Toast.makeText(getBaseContext(), "Бот остановлен",
														   Toast.LENGTH_SHORT).show();
										editor.commit();
										}
									}
								})
								;
							AlertDialog alert = builder.create();
							alert.show();*/
					}
				}
		);
		//кнопка установить цену с минимальным отрывом перед стаканом
		Button but_min= (Button) findViewById(R.id.minprice);
		but_min.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					OrdersPriceList ordersPriceList = Parametr.getParametr().getOrdersPriceList();
					List<OrderPrice> listOrders;
					
					
		if (kind == 1){
			listOrders = ordersPriceList.getListbuy();
			OrderPrice orderPrice = listOrders.get(0);
			
			Double jk = orderPrice.getPrice()+0.0001;
			price_dialog.setText(String.format(Locale.US, "%.4f",jk));
		}
		if(kind == 0){
			listOrders = ordersPriceList.getListsell();
			OrderPrice orderPrice = listOrders.get(0);
			Double jk = orderPrice.getPrice()-0.0001;
			price_dialog.setText(String.format(Locale.US, "%.4f",jk));
		}
				}
			});
		//переключатель
		Switch switch_dialog = (Switch) findViewById(R.id.monitored_switch);
		
		if (btn_z == 0){
			kind = 1;
			switch_dialog.setChecked(true);
		}
		if(btn_z == 1){
			kind = 0;
			
		}
		if (switch_dialog != null) {
			switch_dialog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							kind = 1;
							
						} else{kind = 0; }
					}
			});
		}
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
