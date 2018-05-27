package com.deff83.indexAPI;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.view.View.*;
import android.view.inputmethod.*;
import android.graphics.*;
import android.preference.*;

public class OpovActivity extends Activity
{
	
	SharedPreferences pref;
	Context context = null;
	SharedPreferences.Editor editor = null;
	private RelativeLayout interceptor;
	//поля нижней цены
	EditText price_edit;
	TextView price_minuse;
	//поля верхней цены
	EditText price_edit2;
	TextView price_pluse;
	//поля цены оповещения
	EditText price_min, price_max;
	TextView btctext;
	//список в выдвижной панели
	private String[] mCatTitles;
    private ListView mDrawerListView;
	//флаги перекдючателей
	Integer flag_switch_sound_opov_gran, flag_switch_sound_opov_price;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		//pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication());
		editor = pref.edit();
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opoveshenia);
		
		//список в выдвижной панели
		mCatTitles = getResources().getStringArray(R.array.opoveshenia);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer3);
        // подключим адаптер для списка
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
															R.layout.draw_list_item, mCatTitles));
		mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());
		//инициализация полей нижней цены
		price_edit = (EditText) findViewById(R.id.price_edit);
		price_minuse = (TextView) findViewById(R.id.price_minus);
		//инициализация полей верхней цены
		price_edit2 = (EditText) findViewById(R.id.price_edit2);
		price_pluse = (TextView) findViewById(R.id.price_plus);
		//инициализация полей оповещерия цены коина
		price_max = (EditText) findViewById(R.id.price_maxop);
		price_min = (EditText) findViewById(R.id.price_minop);
		btctext = (TextView) findViewById(R.id.btctextv);
		
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
		//запись при старте в edit цена продажи
		try {
			if(pref.contains("edit_price_opmin")){
				price_min.setText(pref.getString("edit_price_opmin", ""));
			}
		} catch (Exception e){}
		//запись при старте в edit цена покупки
		try {
			if(pref.contains("edit_price_opmax")){
				price_max.setText(pref.getString("edit_price_opmax", ""));
			} 
		} catch (Exception e){}
		//если после этого записи в editах нет то написать
		String editmin = price_min.getText().toString();
		String editmax = price_max.getText().toString();
		if (editmin.equals("")){
			editmin = "0.0";
			price_min.setText("0.0");
		}
		if (editmax.equals("")){
			editmax = "999.9";
			price_max.setText("999.9");
		}
		//слушатель,  layot
		interceptor = (RelativeLayout) findViewById(R.id.rel_layout3);
		interceptor.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
						price_edit.setFocusable(false);
						price_edit2.setFocusable(false);
						price_max.setFocusable(false);
						price_min.setFocusable(false);
					}
					return v.performClick();
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
		OnClickListener onclicklist = new OnClickListener(){
			@Override
			public void onClick(View v){
				price_min.setText(pref.getString("tabl0", "0.0"));
				price_max.setText(pref.getString("tabl_prod0", "999.9"));
			}
		};
		btctext.setOnClickListener(onclicklist);
		//вешаем на каждый edit один слушатель
		price_edit.setOnTouchListener(on_touch_listener3);
		price_edit2.setOnTouchListener(on_touch_listener3);
		price_max.setOnTouchListener(on_touch_listener3);
		price_min.setOnTouchListener(on_touch_listener3);
		//переключатель границ портфеля
		Switch switch_sound_opov_gran = (Switch) findViewById(R.id.switch1);
		flag_switch_sound_opov_gran = 0;
		flag_switch_sound_opov_gran = pref.getInt("sound_opov_gran", 1);
		if (flag_switch_sound_opov_gran == 0){
			switch_sound_opov_gran.setChecked(true);//вкл

		}
		if(flag_switch_sound_opov_gran == 1){


		}

		if (switch_sound_opov_gran != null) {
			switch_sound_opov_gran.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							editor.putInt("sound_opov_gran", 0);

							editor.commit();
						} else{editor.putInt("sound_opov_gran",1);  editor.commit();}
					}
				});
		}
		//переключатель цены коина
		Switch switch_sound_opov_price = (Switch) findViewById(R.id.switch2);
		flag_switch_sound_opov_price = 0;
		flag_switch_sound_opov_price = pref.getInt("sound_opov_price", 1);
		if (flag_switch_sound_opov_price == 0){
			switch_sound_opov_price.setChecked(true);//вкл

		}
		if(flag_switch_sound_opov_price == 1){


		}

		if (switch_sound_opov_price != null) {
			switch_sound_opov_price.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							editor.putInt("sound_opov_price", 0);
							//запись цены оповещений
							editor.putString("edit_price_opmin", price_min.getText().toString());
							editor.putString("edit_price_opmax", price_max.getText().toString());
							
							editor.commit();
						} else{editor.putInt("sound_opov_price",1);  editor.commit();}
					}
				});
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		switch(id){
			case R.id.menuPurchasesListSortOrderCategory:
				Intent intent = new Intent(OpovActivity.this, Oproecte.class);
				startActivity(intent);
				break;
		}
		// TODO: Implement this method
		return super.onOptionsItemSelected(item);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	@Override
	protected void onStart()
	{
		try{
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
		}catch(Exception e){
			editor.putString("edit_price", "999.9");
			editor.putString("edit_price2", "0.0");
			editor.commit();
		}
		// TODO: Implement this method
		super.onStart();
	}

	@Override
	protected void onStop()
	{
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
		//запись цены оповещений
		editor.putString("edit_price_opmin", price_min.getText().toString());
		editor.putString("edit_price_opmax", price_max.getText().toString());
		editor.commit();
		// TODO: Implement this method
		super.onStop();
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
			//запись цены оповещений
			editor.putString("edit_price_opmin", price_min.getText().toString());
			editor.putString("edit_price_opmax", price_max.getText().toString());
			editor.commit();
		} catch (Exception e){}
		// TODO: Implement this method
		super.onPause();
	}
	
//  Слушатель для элементов списка в выдвижной панели
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = null;
			switch (position){
				case 0:
					intent = new Intent(OpovActivity.this, MainActivity.class);
					break;
				
				
				case 1:
					intent = new Intent(OpovActivity.this, Userfunction.class);
					break;
				case 2:
					intent = new Intent(OpovActivity.this, Myzayvk_act.class);
					break;
				case 3:
					intent = new Intent(OpovActivity.this, Information.class);
					break;
				case 4:
					editor.putInt("messflag", 1);
					editor.commit();
					intent = new Intent(OpovActivity.this, Chat.class);
					break;

				case 6:
					intent = new Intent(OpovActivity.this, LoginActivity.class);
					editor.putInt("verification", 0);
					editor.putInt("col_tabl", 0);
					editor.putInt("tabl_hight", 0);
					editor.commit();
					Intent i = new Intent(OpovActivity.this, PlayService.class);
					stopService(i);
					break;
				case 5:
					intent = new Intent(OpovActivity.this, Setting.class);
					break;
			}
				startActivity(intent);
			
			
		}
	}}
