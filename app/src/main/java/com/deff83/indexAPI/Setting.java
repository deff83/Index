package com.deff83.indexAPI;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.view.inputmethod.*;

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
				Intent i = new Intent(Setting.this, PlayService.class);
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
		//запись при старте в edit
		
			
				chPost.setText(pref.getString("chPost", "10"));
			
				sizeSh.setText(pref.getString("sizeSh", "20"));
			
				coltablp.setText(pref.getString("tabl_hight", "10"));
			
		
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
		//пепевод введенного в edit записи в значение int
		String shPost = chPost.getText().toString();
		String size = sizeSh.getText().toString();
		String colt = coltablp.getText().toString();
		if (shPost.equals("")){
			shPost = "10";
			chPost.setText("10");
		}
		if (size.equals("")){
			size = "20";
			sizeSh.setText("20");
		}
		if (colt.equals("")){
			colt = "10";
			coltablp.setText("10");
		}
		// сохраниние нулевых значений в файле, и значений введенных в edit
		editor.putString("chPost", shPost);
		editor.putString("sizeSh", size);
		editor.putString("tabl_hight", colt);
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
			if (position == 0){
				Intent intent = new Intent(Setting.this, MainActivity.class);
				startActivity(intent);
			}
		}
	}
}
