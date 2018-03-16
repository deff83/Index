package com.mycompany.myapp5;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import android.view.View.*;

public class Userfunction extends Activity
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
	
	static Thread myThread;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		editor = pref.edit();
		
		
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_function);
		//список в выдвижной панели
		mCatTitles = getResources().getStringArray(R.array.userfunction);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
        // подключим адаптер для списка
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
															R.layout.draw_list_item, mCatTitles));
		mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());
		//слушатель кнопки убрать все заявки
		listbutton = new OnClickListener() {
			@Override
			public void onClick(View v) {
	
						int i = pref.getInt("col_z", 0);
						//выполнение функции удаления заявок
						if (i != 0){
							switch(v.getId()){
								case R.id.button_del_all:
									editor.putInt("del_all", 1);
									editor.commit();
									break;
								case R.id.button_del:
									editor.putInt("del", 1);
									editor.commit();
									Toast.makeText(getApplicationContext(),   "исполнено ", Toast.LENGTH_SHORT).show();		
									break;
							}
						}
									
			}
		};
		
		
		Button but_del_all = (Button) findViewById(R.id.button_del_all);
		Button but_del = (Button) findViewById(R.id.button_del);
		but_del_all.setOnClickListener(listbutton);
		but_del.setOnClickListener(listbutton);
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
			Toast.makeText(getApplicationContext(),   "Выбран пункт " + position, Toast.LENGTH_SHORT).show();
			
				Intent intent = null;
				switch (position){
					case 0:
						intent = new Intent(Userfunction.this, MainActivity.class);
						break;
					case 1:
						intent = new Intent(Userfunction.this, Setting.class);
						break;
				}
				startActivity(intent);
			
		}
	}
}
