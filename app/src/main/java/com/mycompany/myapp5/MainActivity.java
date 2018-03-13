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
import android.icu.util.*;
import android.view.ViewDebug.*;
import android.widget.Toolbar.*;
import android.support.v4.content.*;
import android.content.res.*;
import android.support.v7.app.ActionBarActivity;
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
	BroadcastReceiver br;
	private RelativeLayout interceptor;
	private AlarmManager am;
	private PendingIntent pi;
	SharedPreferences pref;
	Context context = null;
	SharedPreferences.Editor editor = null;
	//поля нижней цены
	EditText price_edit;
	TextView price_minuse;
	//поля верхней цены
	EditText price_edit2;
	TextView price_pluse;
	LinearLayout llt;
	//поля таблицы
	TableLayout tableLayout;
	TableLayout tableLayout2;
	//LayoutInflater inflater;
	int str_schet;
	OnClickListener getButtonText;
	LinearLayout.LayoutParams lButtonParams;
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
        setContentView(R.layout.main);
		
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
		//инициализация полей нижней цены
		price_edit = (EditText) findViewById(R.id.price_edit);
		price_minuse = (TextView) findViewById(R.id.price_minus);
		//инициализация полей верхней цены
		price_edit2 = (EditText) findViewById(R.id.price_edit2);
		price_pluse = (TextView) findViewById(R.id.price_plus);
		//запись при старте в edit нижняя цена
		try {
		if(pref.contains("edit_price")){
			price_edit.setText(pref.getString("edit_price", ""));
		}
		} catch (Exception e){}
		//запись при старте в edit верхняя цена
		try {
			if(pref.contains("edit_price2")){
				price_edit2.setText(pref.getString("edit_price2", ""));
			}
		} catch (Exception e){}
		//слушатель,  layot
		interceptor = (RelativeLayout) findViewById(R.id.rel_layout);
		interceptor.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
						price_edit.setFocusable(false);
						price_edit2.setFocusable(false);
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
		price_edit.setOnTouchListener(on_touch_listener);
		price_edit2.setOnTouchListener(on_touch_listener);
		//слушатель кнопки купить
		btnZ_Start.setOnClickListener(new  View.OnClickListener() {
				@Override
				public void onClick(View view) {
					
					
					}});
		//слушатель кнопки продать
		btnZ_Stop.setOnClickListener(new  View.OnClickListener() {
				@Override
				public void onClick(View view) {
					editor.putInt("del", 1);
					editor.commit();
					
					}});
		// запуск службы, нажатие на кнопку старт
        btnStart.setOnClickListener(new  View.OnClickListener() {
				@Override
				public void onClick(View view) {
					//пепевод введенного в edit записи в значение double
					String edit = price_edit.getText().toString();
					String edit2 = price_edit2.getText().toString();
					Double text, text2;
					if (edit.equals("")){
						edit = "0.0";
						price_edit.setText("0.0");
					}
					if (edit2.equals("")){
						edit2 = "999.9";
						price_edit2.setText("999.9");
					}
					// сохраниние нулевых значений в файле, и значений введенных в edit
					editor = pref.edit();
					editor.putString("edit_price2", edit2);
					editor.putString("edit_price", edit);
					editor.putString("price_plus","0");
					editor.putString("price_minus", "0");
					editor.commit();
					//вызов функции включения в запись менеджера временных задач
					final Intent i = new Intent(MainActivity.this, PlayService.class);
					startService(i);
					}});
//остановка временой щадачи и остановка службы
		btnStop.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					//Intent intentstop = new Intent(MainActivity.this, PlayService.class);
					stopService(new Intent(MainActivity.this, PlayService.class));
				}
			});
		// кнопка "широковещптельное сообщение", для тестов
		Button intent_buttin = (Button) findViewById(R.id.intent_button);
		//int color = ContextCompat.(this, R.color.colorGreen);
		//intent_buttin.setBackgroundColor(getResources().getColor( R.color.colorGreen));
		intent_buttin.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
 						//здесь код для слушателя кнопок
			}
		});

		// создаем BroadcastReceiver, слушатель главного MainActivity приложения
		br = new BroadcastReceiver() {
		// действия при получении сообщений
		public void onReceive(Context context, Intent intent) {
			int res = pref.getInt("res", 0);
			if (res == 0){
			editor.putInt("res", 1);
			editor.commit();
			try{
			String coin_price = intent.getStringExtra("coin_price");
			coin_prices(coin_price);
			String list_price = intent.getStringExtra("list_price");
			tabl_price(list_price);
			tabl_z();
				Double price_intent = intent.getDoubleExtra("price", 0.0);
				String price_intent_string = price_intent.toString();
				TextView text_balance = (TextView) findViewById(R.id.text2);
				TextView text_fullanswer = (TextView) findViewById(R.id.text);
				text_balance.setText(price_intent_string);
				text_fullanswer.setText("");
				//надписи внизу edit
				String price_minus =pref.getString("price_minus", "");
				Double price_minus_d = Double.parseDouble(price_minus);
				if (price_minus_d < 0){
					price_minuse.setTextColor(Color.RED);
				}
				else {
					price_minuse.setTextColor(Color.WHITE);
				}
				String price_edit =pref.getString("edit_price", "");
				Double price_edit_d = Double.parseDouble(price_edit);
				Double minuses = Math.round((price_edit_d + price_minus_d) * 100.0) / 100.0;
				price_minuse.setText(minuses.toString() + "   (" + price_minus + ")");
				String price_plus =pref.getString("price_plus", "");
				Double price_plus_d = Double.parseDouble(price_plus);
				if (price_plus_d > 0){
					price_pluse.setTextColor(Color.GREEN);
				} else {
					price_pluse.setTextColor(Color.WHITE);
				}
				String price_edit2 =pref.getString("edit_price2", "");
				Double price_edit2_d = Double.parseDouble(price_edit2);

				Double pluses = Math.round((price_edit2_d + price_plus_d) * 100.0) / 100.0;
				price_pluse.setText(pluses.toString() + "   (" + price_plus + ")");
			}
			catch (Exception e){}
			editor.putInt("fin", 1);
			}
				editor.putInt("res", 0);
				editor.commit();
			}
			};
		// создаем фильтр для BroadcastReceiver
		IntentFilter intFilt = new IntentFilter("CAT");
		// регистрируем (включаем) BroadcastReceiver
		registerReceiver(br, intFilt);
}

	@Override
	protected void onPause()
	{
		//System.out.println( "сохраниние");
		String edit = price_edit.getText().toString();

		String edit2 = price_edit2.getText().toString();
		//editor = pref.edit();
		try{
		editor.putString("edit_price", edit);
		editor.putString("edit_price2",edit2);
		editor.commit();
		} catch (Exception e){}
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
													btn.setLayoutParams( new LinearLayout.LayoutParams(wight,  100));
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
		//вывод какой-нибуть величины после обработки функции вывода таблицы
		Toast.makeText(this, list_price, Toast.LENGTH_SHORT).show();
	}
	public void tabl_z(){
		int col_izm = pref.getInt("col_izm", 0);
		int col_z = pref.getInt("col_z", 0);
		if (col_izm == 1){
			tableLayout2.removeAllViews();
			TableRow tr2;
			
			for (int i = 0; i < col_z; i++){
				LayoutInflater inflate = LayoutInflater.from(this);
				tr2 = (TableRow) inflate.inflate(R.layout.tabl2_row, null);
				TextView toolidtw;
				TextView offeridtw;
				TextView nametw;
				TextView kindtw;
				TextView pricetw;
				TextView notestw;
				
					toolidtw = (TextView) tr2.findViewById(R.id.colm1);
					offeridtw = (TextView) tr2.findViewById(R.id.colm2);
					nametw = (TextView) tr2.findViewById(R.id.colm3);
					kindtw = (TextView) tr2.findViewById(R.id.colm4);
					pricetw = (TextView) tr2.findViewById(R.id.colm5);
					notestw = (TextView) tr2.findViewById(R.id.colm6);
					vCoin = 60;
					
					int offerid =pref.getInt("my_offer" + vCoin + "_" + i + "offerid", 0);
					String name =pref.getString("my_offer" + vCoin + "_" + i + "name", "");
					int kind =pref.getInt("my_offer" + vCoin + "_" + i + "kind", 0);
					String price =pref.getString("my_offer" + vCoin + "_" + i + "price", "");
					int notes =pref.getInt("my_offer" + vCoin + "_" + i + "notes", 0);
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
	}

}
