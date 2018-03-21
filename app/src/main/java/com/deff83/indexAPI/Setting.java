package com.deff83.indexAPI;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;

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
		//слушатель кнопки убрать все заявки
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
