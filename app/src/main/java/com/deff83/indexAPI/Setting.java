package com.deff83.indexAPI;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.view.inputmethod.*;
import android.preference.*;

public class Setting extends Activity
{
	SharedPreferences pref;
	Context context = null;
	SharedPreferences.Editor editor = null;
	private RelativeLayout interceptor;
	//список в выдвижной панели
	private String[] mCatTitles;
    private ListView mDrawerListView;
	//алушатель кнопки
	OnClickListener listbutton;
	//поля в нмстройках
	EditText chPost;
	EditText sizeSh;
	EditText coltablp;
	EditText widjchast;
	//
	Integer flag_switch_sound_opov, flag_switch_sound_widget, flag_switch_sound_chat;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		
		editor = pref.edit();


		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		//список в выдвижной панели
		mCatTitles = getResources().getStringArray(R.array.setting);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
        // подключим адаптер для списка
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
															R.layout.draw_list_item, mCatTitles));
		mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());
		//слушатель кнопки сервиса
		listbutton = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Setting.this, ServicePOST.class);
				switch (v.getId()){
					case R.id.button_start:	//запуск службы
						try{
							startService(i);
						}catch(Exception e){}
						break;
					case R.id.button_stop:	//останов службы
						try{
							stopService(i);
						}catch(Exception e){}
				}
				}
				};
		Button btnStart = (Button) findViewById(R.id.button_start);
		Button btnStop = (Button) findViewById(R.id.button_stop);
		
		
		btnStart.setOnClickListener(listbutton);
		btnStop.setOnClickListener(listbutton);
		//инициализация полей састота post запроса
		chPost = (EditText) findViewById(R.id.edit_h_POST);
		//инициализация полей размер шрифт
		sizeSh = (EditText) findViewById(R.id.Shrift);
		//поле количество заявок в прасе
		coltablp = (EditText) findViewById(R.id.editcoltabl);
		//поле частоты нажатие на виджет
		widjchast = (EditText) findViewById(R.id.widjchast);
		//запись при старте в edit
		
			
				chPost.setText(Parametr.getParametr().getChastota()+"");
			
				sizeSh.setText(pref.getString("sizeSh", "20"));
			
				coltablp.setText(Parametr.getParametr().getcolTableOffer()+"");
			
				widjchast.setText("" + pref.getInt("chastwidj", 2));
		//функция изменения частоты запроса: не для вип
		//try{
		//if(Integer.valueOf(pref.getString("chPost", "2"))<5){            //не VIP
		//	chPost.setText("5");
		//}
		//}catch(Exception e){chPost.setText("5");}
		//слушатель,  layot
		interceptor = (RelativeLayout) findViewById(R.id.rel_layout2_setting);
		interceptor.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
						chPost.setFocusable(false);
						sizeSh.setFocusable(false);
						coltablp.setFocusable(false);
						widjchast.setFocusable(false);
							// сохраниние нулевых значений в файле, и значений введенных в edit
							
							int chastot = 20;
						try{chastot = Integer.valueOf(chPost.getText().toString());
						}catch(Exception e){};
						Parametr.getParametr().setChastota(chastot);
						editor.putString("sizeSh", sizeSh.getText().toString());
						int colTable = 20;
						try{colTable = Integer.valueOf(coltablp.getText().toString());
						}catch(Exception e){};
						Parametr.getParametr().setcolTableOffer(colTable);
						editor.putInt("col_Table", colTable);
						String yujh = widjchast.getText().toString();
						if (yujh.equals("")){
							yujh = "2";
							widjchast.setText("2");
						}
						editor.putInt("chastwidj", Integer.parseInt(widjchast.getText().toString()));
						editor.commit();
					}
					return v.performClick();
				}
			});
		//слушатель edit, установка акиивности вызов клавиатуры
		OnTouchListener on_touch_listener2_setting = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setFocusableInTouchMode(true);
				}
				return v.performClick();
			}
		};
		//вешаем на каждый edit один слушатель
		chPost.setOnTouchListener(on_touch_listener2_setting);
		sizeSh.setOnTouchListener(on_touch_listener2_setting);
		coltablp.setOnTouchListener(on_touch_listener2_setting);
		widjchast.setOnTouchListener(on_touch_listener2_setting);
		
		//переключатель
		Switch switch_sound_opov = (Switch) findViewById(R.id.sound_switch);
		flag_switch_sound_opov = 0;
		flag_switch_sound_opov = pref.getInt("sound_opov", 0);
		if (flag_switch_sound_opov == 0){
			switch_sound_opov.setChecked(true);//вкл
			
		}
		if(flag_switch_sound_opov == 1){
			

		}
		
		if (switch_sound_opov != null) {
			switch_sound_opov.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							editor.putInt("sound_opov", 0);
							
							editor.commit();
						} else{editor.putInt("sound_opov",1);  editor.commit();}
					}
				});
		}
		//переключатель widjet
		Switch switch_sound_widjet = (Switch) findViewById(R.id.sound_switch_widjet);
		flag_switch_sound_widget = 0;
		flag_switch_sound_widget = pref.getInt("sound_widjet", 1);
		if (flag_switch_sound_widget == 0){
			switch_sound_widjet.setChecked(true);//вкл

		}
		if(flag_switch_sound_widget == 1){


		}

		if (switch_sound_widjet != null) {
			switch_sound_widjet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							editor.putInt("sound_widjet", 0);

							editor.commit();
							Intent i = new Intent(Setting.this, WidjetPrice.class);
							startService(i);
						} else{
							editor.putInt("sound_widjet",1);  editor.commit();
							Intent i = new Intent(Setting.this, WidjetPrice.class);
							stopService(i);
							}
					}
				});
		}
		//переключатель chat
		Switch switch_sound_chat = (Switch) findViewById(R.id.sound_switch_chat);
		flag_switch_sound_chat = 1;
		flag_switch_sound_chat = pref.getInt("sound_chat", 1);
		if (flag_switch_sound_chat == 0){
			switch_sound_chat.setChecked(true);//вкл

		}
		if(flag_switch_sound_chat == 1){


		}

		if (switch_sound_chat != null) {
			switch_sound_chat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							editor.putInt("sound_chat", 0);
							editor.commit();
							
						} else{editor.putInt("sound_chat",1);  editor.commit();}
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
				Intent intent = new Intent(Setting.this, Oproecte.class);
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
		//Toast.makeText(this, "" +pref.getInt("sound_opov", 8),
					//  Toast.LENGTH_SHORT).show();
		// TODO: Implement this method
		super.onStart();
	}

	@Override
	protected void onStop()
	{
		//пепевод введенного в edit записи в значение int
		String shPost = chPost.getText().toString();
		String size = sizeSh.getText().toString();
		String colt = coltablp.getText().toString();
		String yujh = widjchast.getText().toString();
		if (yujh.equals("")){
			yujh = "2";
			
		}
		Integer widch = Integer.parseInt(yujh);
		if (shPost.equals("")){
			shPost = "2";
			chPost.setText("2");
		}
		if (size.equals("")){
			size = "20";
			sizeSh.setText("20");
		}
		if (colt.equals("")){
			colt = "30";
			coltablp.setText("30");
		}
		// сохраниние нулевых значений в файле, и значений введенных в edit
		editor.putString("chPost", shPost);
		editor.putString("sizeSh", size);
		editor.putString("tabl_hight", colt);
		editor.putInt("chastwidj", widch);
		int colTable = 20;
						try{colTable = Integer.valueOf(coltablp.getText().toString());
						}catch(Exception e){};
						Parametr.getParametr().setcolTableOffer(colTable);
						editor.putInt("col_Table", colTable);
		editor.commit();
		// TODO: Implement this method
		super.onStop();
	}

	@Override
	protected void onPause()
	{
		//System.out.println( "сохраниние");
		String shpostp = chPost.getText().toString();
		String colp = coltablp.getText().toString();
		String sizeshp = sizeSh.getText().toString();
		//editor = pref.edit();
		try{
			editor.putString("chPost", shpostp);
			editor.putString("sizeSh",sizeshp);
			editor.putString("tabl_hight", colp);
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
					intent = new Intent(Setting.this, MainActivity.class);
					break;
				case 1:
					
					intent = new Intent(Setting.this, HistoryActivity.class);
					break;
				case 2:
					intent = new Intent(Setting.this, Userfunction.class);
					break;
				case 3:
					intent = new Intent(Setting.this, Myzayvk_act.class);
					break;
				case 4:
					intent = new Intent(Setting.this, Information.class);
					break;
				case 5:
					
					intent = new Intent(Setting.this, Chat.class);
					break;
				
				case 6:
					intent = new Intent(Setting.this, LoginActivity.class);
					editor.putInt("verification", 0);
					editor.commit();
					Intent i = new Intent(Setting.this, ServicePOST.class);
					stopService(i);
					break;
			}
			
			startActivity(intent);
			
		}
	}
}
