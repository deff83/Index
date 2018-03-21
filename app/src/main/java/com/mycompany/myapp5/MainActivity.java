package com.mycompany.myapp5;
import android.app.*;
import android.os.*;
import okhttp3.*;
import java.io.*;
import android.widget.*;
import org.apache.http.*;
import org.w3c.dom.*;
import java.util.*;
import org.json.*;
import android.view.*;
import android.content.*;
import android.view.inputmethod.*;
import android.view.View.*;
import android.text.*;
import android.graphics.*;
import android.preference.*;

import android.view.ViewDebug.*;
import android.widget.Toolbar.*;
import android.support.v4.content.*;
import android.content.res.*;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.widget.*;
public class MainActivity extends Activity
{
	//список в выдвижной панели
	private String[] mCatTitles;
    private ListView mDrawerListView;
	//
	String textr;
	int vCoin;
	String buy;
	String sale;
	Integer del;
	static BroadcastReceiver br;
	static IntentFilter intFilt;
	static Intent i;
	private RelativeLayout interceptor;
	private AlarmManager am;
	private PendingIntent pi;
	SharedPreferences pref;
	Context context = null;
	SharedPreferences.Editor editor = null;
	private SwipeRefreshLayout swipe;
	LinearLayout llt;
	//поля таблицы
	TableLayout tableLayout;
	TableLayout tableLayout2;
	//LayoutInflater inflater;
	int str_schet;
	OnClickListener getButtonText;
	OnClickListener gettablz;
	LinearLayout.LayoutParams lButtonParams;
	//текст свайп вниз
	TextView text_error;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		count_coin2 = 0;
    	count_tabl2 = 0;
		str_schet = 0;
		buy = "покупка";
		sale = "продажа";
		del = 0;
		pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		editor = pref.edit();
		editor.putInt("count_coin",0);
		editor.putInt("activity_true", 1);
		editor.putInt("tabl_hight", 50);
		editor.putInt("res", 0);
		editor.putInt("col_izm", 1);
		editor.putInt("del", 0);
		editor.commit();
        super.onCreate(savedInstanceState);
		int verification = pref.getInt("verification", 0);
		if (verification != 1){
			Intent exit = new Intent(this, LoginActivity.class);
			startActivity(exit);
		}
        setContentView(R.layout.main);
		final Intent ir = new Intent(getApplication(), PlayService.class);
		//создаем объект свайпа
		swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		//слушптель свайпа
		swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
			@Override
			public void onRefresh() {
				
				startService(ir);
				
			}
		});
		//текст ошибки
		text_error = (TextView) findViewById(R.id.text_error);
		//кнопка акшнбара
		//final ActionBar actionBar = getSupportActionBar();
		//actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher);
		//actionBar.setDisplayHomeAsUpEnabled(true);
		//список в выдвижной панели
		mCatTitles = getResources().getStringArray(R.array.cats_array_ru);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
        // подключим адаптер для списка
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
															R.layout.draw_list_item, mCatTitles));
		mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());
		llt = (LinearLayout) findViewById(R.id.layout_button);
		//  LinearLayout llt = new LinearLayout(this);
        //layout params for every Button
		getButtonText = new OnClickListener() {
		 @Override
		 public void onClick(View v) {
			 
			 v.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_d));
			 
			 int buttonId = v.getId() - 1000;
			 editor.putString(buttonId + "10002", "1");
			 for (int j = 0; j < count_coin; j++){
					if (buttonId != j){
							editor.putString(j + "10002", "0");
					}
					editor.commit();
			 }
		 }
		 };
		 //слушатель таблицы заявок
		 gettablz = new OnClickListener(){
			 @Override
			 public void onClick(View v){
				 int j = v.getId() - 4000;
				
				 //окрашиваем строчку
				 final TextView toolidtw_z;
				 final TextView offeridtw_z;
				 final TextView nametw_z;
				 final TextView kindtw_z;
				 final TextView pricetw_z;
				 final TextView notestw_z;

				 toolidtw_z = (TextView) v.findViewById(R.id.colm1);
				 offeridtw_z = (TextView) v.findViewById(R.id.colm2);
				 nametw_z = (TextView) v.findViewById(R.id.colm3);
				 kindtw_z = (TextView) v.findViewById(R.id.colm4);
				 pricetw_z = (TextView) v.findViewById(R.id.colm5);
				 notestw_z = (TextView) v.findViewById(R.id.colm6);
				 
				 toolidtw_z.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				 offeridtw_z.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				 nametw_z.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				 kindtw_z.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				 pricetw_z.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				 notestw_z.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				 //получаем данные				 
				 String name_z =pref.getString("my_offer" + vCoin + "_" + i + "name", "");
				 final int z_id =pref.getInt("my_offer" + vCoin + "_" + j + "offerid", 0);
				 final String price_z =pref.getString("my_offer" + vCoin + "_" + j + "price", "");
				 int notes_z =pref.getInt("my_offer" + vCoin + "_" + j + "notes", 0);
				 //устанавливаем флаг нажатия
				 editor.putInt("press_tabl", z_id);
				 editor.commit();
				 //заголовок окна
				 String title_z = pref.getString("name_coin" + 0, "");
				 //выводим диалоговое окно
				 AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				 builder.setTitle(title_z)
					 .setMessage("Выберите действие для заявки" + "  "+z_id + "\n" + name_z +"  " +price_z +"  (" + notes_z +" нот)")                      			
					 .setCancelable(true)
					 .setNegativeButton(R.string.delz,
					 new DialogInterface.OnClickListener() {
						 public void onClick(DialogInterface dialog, int id) {
							 // нажато "Отмена"
							 Toast.makeText(getBaseContext(), "подождите", 
											Toast.LENGTH_SHORT).show();
							//убираем цвет нажатой таблицы
							 toolidtw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							 offeridtw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							 nametw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							 kindtw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							 pricetw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							 notestw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							//убираем флаг нажатия
							editor.putInt("press_tabl", 3);
							editor.putInt("del", 1);
							editor.putInt("j_del", z_id);
							editor.commit();
							 dialog.cancel();
						 }
					 })
					 .setPositiveButton(R.string.editz, 
					 new DialogInterface.OnClickListener() {
						 public void onClick(DialogInterface dialog, int id) {
							 // нажато "OK"
							 Toast.makeText(getBaseContext(), "OK", 
											Toast.LENGTH_SHORT).show();
							 //убираем флаг нажатия
							 editor.putInt("press_tabl", 3);
							 editor.commit();
							 dialog.cancel();
						 }
					 });
				 AlertDialog alert = builder.create();
				 alert.show();
			 }
		 };
		//таблица цен
		tableLayout = (TableLayout) findViewById(R.id.table);
		//таблица заявок
		tableLayout2 = (TableLayout) findViewById(R.id.table2);
		tabl_z();
		editor.putInt("col_izm", 0);
		//код обработки кнопок
		final Button btnStart = (Button) findViewById(R.id.button_start);
        final Button btnStop = (Button) findViewById(R.id.button_stop);
		//инициализация кнопок покупки продажи
		final Button btnZ_Start = (Button) findViewById(R.id.zayvka_start);
        final Button btnZ_Stop = (Button) findViewById(R.id.zayvka_stop);
		
		//слушатель кнопки купить
		btnZ_Start.setOnClickListener(new  View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(MainActivity.this, Buy_dialog.class);
					editor.putInt("btn_z", 0);
					editor.commit();
					startActivity(intent);
					//editor.putInt("add", 1);
					//editor.commit();					
					}});
		//слушатель кнопки продать
		btnZ_Stop.setOnClickListener(new  View.OnClickListener() {
				@Override
				public void onClick(View view) {
					
					Intent intent = new Intent(MainActivity.this, Buy_dialog.class);
					editor.putInt("btn_z", 1);
					editor.commit();
					startActivity(intent);
					
					}});
		//старт снрвиса при создании активити
		int serv = pref.getInt("serv", 0);
		if (serv == 0){
			Intent i = new Intent(this, PlayService.class);
			startService(i);
		}
		
		
	}

	@Override
	protected void onStart()
	{
		Toast.makeText(this, "Добро пожаловать...", Toast.LENGTH_SHORT).show();
		// создаем BroadcastReceiver, слушатель главного MainActivity приложения


		br = null;
		br = new BroadcastReceiver() {
			// действия при получении сообщений
			public void onReceive(Context context, Intent intent) {
				//int res = pref.getInt("res", 0);
				//if (res == 0){
				//editor.putInt("res", 1);
				//editor.commit();
				try{
					String coin_price = intent.getStringExtra("coin_price");

					String list_price = intent.getStringExtra("list_price");

					Double price_intent = intent.getDoubleExtra("price", 0.0);
					String price_intent_string = price_intent.toString();
					TextView text_balance = (TextView) findViewById(R.id.text2);
					
					text_balance.setText(price_intent_string + "$");
					
					coin_prices(coin_price);
					tabl_price(list_price);
					tabl_z();
					//надписи внизу edit
					
				}
				catch (Exception e){}
				editor.putInt("fin", 1);
				//	}
				//editor.putInt("res", 0);
				//	editor.commit();
			}
		};

		// создаем фильтр для BroadcastReceiver
		intFilt = new IntentFilter("CAT");
		// регистрируем (включаем) BroadcastReceiver
		registerReceiver(br, intFilt);
		// TODO: Implement this method
		super.onStart();
	}
	

	@Override
	protected void onStop()
	{
		try{
			unregisterReceiver(br);
		}catch(Exception e){}
		// TODO: Implement this method
		super.onStop();
	}

	@Override
	protected void onPause()
	{
	
		// TODO: Implement this method
		super.onPause();
	}
	//будет функция запуск сервиса
	public void run_alarm (){
	}
	Integer count_coin;
	Integer count_coin2;
	Integer i_str_color;
	Double price_double;
	String i_file_coin;
	Double i_file_coin_double;
	public void coin_prices(String answir){
	try {
				count_coin = pref.getInt("count_coin", 0);
		// инициализируем кнопки
		try{
				JSONObject jsonObject;
				jsonObject = new JSONObject(answir);
				JSONArray jsonarray = jsonObject.getJSONArray("value");
						if (count_coin != jsonarray.length() || count_coin2 != 1){
									llt.removeAllViews();
											for (int i = 0; i < jsonarray.length(); i++) {
													Button btn =  new Button(this);
													int wight;
													wight = LayoutParams.WRAP_CONTENT;
													btn.setLayoutParams( new LinearLayout.LayoutParams(wight,  LayoutParams.WRAP_CONTENT));
													btn.setTextSize(12);
													btn.setId(i + 1000);
													btn.setOnClickListener(getButtonText);
													llt.addView(btn);
											}
									count_coin2 = 1;
									editor.putInt("count_coin", jsonarray.length());
									editor.commit();
						}
				Button but;
				for (int i = 0; i < jsonarray.length(); i++) {
							JSONObject friend = jsonarray.getJSONObject(i);
							String name = friend.getString("name");
							Integer id_coin = friend.getInt("id");
							editor.putString("name_coin"+i, name);
							editor.putInt("id_coin"+i,id_coin);
							String price = friend.getString("price");
							String i_str = String.valueOf(i) + "1000";
							i_str_color = pref.getInt(i_str + "1", 2);
							but = (Button) findViewById(i + 1000);
							but.setText(name + "\n" + price);
				
							price_double = Double.parseDouble(price);
							i_file_coin = pref.getString(i_str, "0.0");
							i_file_coin_double = Double.parseDouble(i_file_coin);
							String up_button = pref.getString(i_str +"2", "0");
							if (up_button.equals("0")){
									but.setBackgroundDrawable(getResources().getDrawable(R.drawable.button));
							}
							else {
									but.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_up));
									editor.putString(i_str + "2", "1");
									editor.commit();
							}
							try{
										if (i_str_color == 0){
													but.setTextColor(getResources().getColor( R.color.colorGreen));
										}
										if(i_str_color == 1){
													but.setTextColor(getResources().getColor( R.color.colorRed));
										}
										else{}
							}
							catch(Exception e){

							}
							if (price_double > (i_file_coin_double + (price_double * 0.001))){
										editor.putInt(i_str + "1", 0);
							}
							if (price_double < (i_file_coin_double - (price_double * 0.001) )){
										editor.putInt(i_str + "1", 1);
							}
							if (i_file_coin_double == 0){
										editor.putInt(i_str + "1", 2);
							}
							editor.putString(i_str, price);
							editor.putString("otvet_prices", "0");

							editor.commit();
				}
	 	} catch (JSONException e){
				 //ошибка обработка JSON обьекта
		}
	}catch(Exception e){
		//остальные возникающие ошибки
	}

	};
	Integer count_tabl;
	Integer count_tabl2;
	String list;
	public void tabl_price (String list_price){
		int tabl_hight = pref.getInt("tabl_hight", 10);
		list = list_price;
		TableRow tr;
		if ( count_tabl2 != 1){
		 			for (int i = 0; i < tabl_hight; i++) {
		 						LayoutInflater inflater = LayoutInflater.from(this);
		 						//Создаем строку таблицы, используя шаблон из файла /res/layout/table_row.xml
		 						tr = (TableRow) inflater.inflate(R.layout.tabl_row, null);
		 						tr.setId(i + 3000);
		 						tableLayout.addView(tr);
		 			}
		 			count_tabl2 = 1;
					// editor.putInt("count_tabl", jsonArray.length());
		 			editor.commit();
		 }
		for (int i = 0; i < tabl_hight; i++) {
					tr = (TableRow) findViewById(i + 3000);
					TextView tv = (TextView) tr.findViewById(R.id.col1);
					TextView tv2 = (TextView) tr.findViewById(R.id.col4);
					String xx = pref.getString("tabl" + i, "");
					String xv = pref.getString("tabl_prod" + i, "");
					tv.setText(xx);
					tv2.setText(xv);
					//функция сравнения номера заявки
					String offerid = pref.getString("tabl_offerid" + i, "0");
					String offerid_prod = pref.getString("tabl_offerid_prod" + i, "0");
										TextView tv_notes = (TextView) tr.findViewById(R.id.col2);
					TextView tv2_notes = (TextView) tr.findViewById(R.id.col5);
					String xx_notes = pref.getString("tabl_notes" + i, "");
					String xv_notes = pref.getString("tabl_notes_prod" + i, "");
					tv_notes.setText(xx_notes);
					tv2_notes.setText(xv_notes);
					//
					if (offerid.equals("0")){
						tv.setTypeface(null);
						tv.setBackgroundColor(getResources().getColor(R.color.colorTabl));
						tv_notes.setBackgroundColor(getResources().getColor(R.color.colorTabl));
						
					} else{
						tv.setTypeface(null, Typeface.BOLD);
						tv.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
						tv_notes.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
					}
					if (offerid_prod.equals("0")){
						tv2.setTypeface(null);
						tv2.setBackgroundColor(getResources().getColor(R.color.colorTabl));
						tv2_notes.setBackgroundColor(getResources().getColor(R.color.colorTabl));
					}else{
						tv2.setTypeface(null, Typeface.BOLD);
						tv2.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
						tv2_notes.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
					}
		}
		text_error.setText("");
		swipe.setRefreshing(false);
		//вывод какой-нибуть величины после обработки функции вывода таблицы
		Toast.makeText(this, list_price, Toast.LENGTH_SHORT).show();
	}
	public void tabl_z(){
		int col_izm = pref.getInt("col_izm", 0);
		int col_z = pref.getInt("col_z", 0);
		int col_tabl = pref.getInt("col_tabl", 0);
		//if (col_tabl != col_z){
			tableLayout2.removeAllViews();
			TableRow tr2;
			editor.putInt("col_tabl", col_z);
			for (int i = 0; i < col_z; i++){
				LayoutInflater inflate = LayoutInflater.from(this);
				tr2 = (TableRow) inflate.inflate(R.layout.tabl2_row, null);
				tr2.setId(i + 4000);
				
				TextView toolidtw;
				TextView offeridtw;
				TextView nametw;
				TextView kindtw;
				TextView pricetw;
				TextView notestw;
				
					toolidtw = (TextView) tr2.findViewById(R.id.colm1);
					offeridtw = (TextView) tr2.findViewById(R.id.colm2);
				tr2.setOnClickListener(gettablz);
					nametw = (TextView) tr2.findViewById(R.id.colm3);
					kindtw = (TextView) tr2.findViewById(R.id.colm4);
					pricetw = (TextView) tr2.findViewById(R.id.colm5);
					notestw = (TextView) tr2.findViewById(R.id.colm6);
					
					//проверка нажата ли таблица для установки цвета
					
					vCoin = 60;
					
					int offerid =pref.getInt("my_offer" + vCoin + "_" + i + "offerid", 0);
					String name =pref.getString("my_offer" + vCoin + "_" + i + "name", "");
					int kind =pref.getInt("my_offer" + vCoin + "_" + i + "kind", 0);
					String price =pref.getString("my_offer" + vCoin + "_" + i + "price", "");
					int notes =pref.getInt("my_offer" + vCoin + "_" + i + "notes", 0);
					
					//
					int pressz = pref.getInt("press_tabl", 3);
					if (pressz == offerid){
				toolidtw.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				offeridtw.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				nametw.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				kindtw.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				pricetw.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				notestw.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				}
					if ( kind == 0){
						
						kindtw.setText(sale);
					}
					if (kind == 1){
						kindtw.setText(buy);
					}
					toolidtw.setText(String.valueOf(vCoin));
					offeridtw.setText(String.valueOf(offerid));
					nametw.setText(name);
					
					pricetw.setText(price.toString());
					notestw.setText(String.valueOf(notes));
				tableLayout2.addView(tr2);
			}
			editor.putInt("col_izm", 0);
			editor.commit();
		
	}


//  Слушатель для элементов списка в выдвижной панели
private class DrawerItemClickListener implements ListView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
		switch (position){
			case 0:
				intent = new Intent(MainActivity.this, OpovActivity.class);
				break;
			case 1:
				intent = new Intent(MainActivity.this, Userfunction.class);
				break;
			case 2:
				intent = new Intent(MainActivity.this, Setting.class);
				break;
			case 3:
				intent = new Intent(MainActivity.this, LoginActivity.class);
				editor.putInt("verification", 0);
				editor.putInt("col_tabl", 0);
				editor.putInt("tabl_hight", 0);
				editor.commit();
				Intent i = new Intent(MainActivity.this, PlayService.class);
				stopService(i);
				break;
		}
		startActivity(intent);
    }
}}
