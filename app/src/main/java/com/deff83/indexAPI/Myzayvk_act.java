	package com.deff83.indexAPI;
	import android.app.*;
	import android.os.*;
	import android.content.*;
	import android.widget.*;
	import android.view.View.*;
	import android.view.*;
	import android.view.inputmethod.*;

public class Myzayvk_act extends Activity
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
		//слушатель
		BroadcastReceiver br;
		IntentFilter intFilt;
		//шрифт
		Float floarsize;
		//лайаут
		LinearLayout linerLayout2;
		LinearLayout linmycoin;
		//слушатель 
		OnClickListener gettablz;
		//текст загрузка
		TextView load;
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
			pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
			editor = pref.edit();


			// TODO: Implement this method
			super.onCreate(savedInstanceState);
			setContentView(R.layout.zayvki_activity);
			//лайаут
			linerLayout2 = (LinearLayout) findViewById(R.id.layoutlin);
			load = (TextView) findViewById(R.id.downloadcoins);
			//список в выдвижной панели
			mCatTitles = getResources().getStringArray(R.array.myz);
			mDrawerListView = (ListView) findViewById(R.id.left_drawer4);
			// подключим адаптер для списка
			mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
																R.layout.draw_list_item, mCatTitles));
			mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());
			br = null;
			br = new BroadcastReceiver() {
				// действия при получении сообщений
				public void onReceive(Context context, Intent intent) {
					try{
						tabl();
					}
					catch(Exception e){}
				}
			};
			//слушатель таблицы заявок
			gettablz = new OnClickListener(){
				@Override
				public void onClick(View v){
				}
			};
			// создаем фильтр для BroadcastReceiver
			intFilt = new IntentFilter("CAT");
			// регистрируем (включаем) BroadcastReceiver
			registerReceiver(br, intFilt);
		}
public void tabl(){
	
	int col_z = pref.getInt("portlength", 0);
	load.setText(null);
	floarsize = Float.parseFloat(pref.getString("sizeSh", "20"));
	//if (col_tabl != col_z){
	linerLayout2.removeAllViews();
	for (int i = 0; i < col_z; i++){
		int port_count =pref.getInt("port_notes" + i , 0);
		//if (port_count != 0){
		LayoutInflater inflate = LayoutInflater.from(this);
		linmycoin = (LinearLayout) inflate.inflate(R.layout.mycoins, null);
		linmycoin.setId(i + 5500);
		TextView nameCoin;
		TextView countCoin;
		nameCoin = (TextView) linmycoin.findViewById(R.id.namecoin);
		countCoin = (TextView) linmycoin.findViewById(R.id.countcoin);
		linmycoin.setOnClickListener(gettablz);
		//проверка нажата ли таблица для установки цвета
		//шрифт
		nameCoin.setTextSize(floarsize);
		countCoin.setTextSize(floarsize);
		String port_name = pref.getString("port_name" + i, "");
		nameCoin.setText(port_name);
		countCoin.setText(String.valueOf(port_count));
		linerLayout2.addView(linmycoin);
		//}
	}
	
	editor.commit();
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

		@Override
		protected void onPause()
		{
			
			// TODO: Implement this method
			super.onPause();
		}

		//  Слушатель для элементов списка в выдвижной панели
		private class DrawerItemClickListener implements ListView.OnItemClickListener {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0){
					Intent intent = new Intent(Myzayvk_act.this, MainActivity.class);
					startActivity(intent);
				}
			}
		}
	}
