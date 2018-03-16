package com.mycompany.myapp5;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.view.View.*;
import android.view.inputmethod.*;
import android.graphics.*;

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
	//список в выдвижной панели
	private String[] mCatTitles;
    private ListView mDrawerListView;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
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
		interceptor = (RelativeLayout) findViewById(R.id.rel_layout3);
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
		price_edit.setOnTouchListener(on_touch_listener3);
		price_edit2.setOnTouchListener(on_touch_listener3);
		
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
			editor.commit();
		} catch (Exception e){}
		// TODO: Implement this method
		super.onPause();
	}
	
//  Слушатель для элементов списка в выдвижной панели
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Toast.makeText(getApplicationContext(),   "Выбран пункт " + position, Toast.LENGTH_SHORT).show();
			Intent intent = null;
			switch (position){
				case 0:
					intent = new Intent(OpovActivity.this, MainActivity.class);
					break;
				case 1:
					intent = new Intent(OpovActivity.this, Setting.class);
					break;
			}
				startActivity(intent);
			
			
		}
	}}
