package com.deff83.indexAPI;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import java.util.*;
import android.preference.*;
import android.view.View.*;

import org.json.*;

public class Oproecte extends Activity
{
	SharedPreferences pref;
	Context context = null;
	SharedPreferences.Editor editor = null;
	private RelativeLayout interceptor;
	//список в выдвижной панели
	private String[] mCatTitles;
    private ListView mDrawerListView;
	//поле теста тестов
	private TextView textizm;
	private BroadcastReceiver br;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		//pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication());
		editor = pref.edit();
		editor.putString("resptest", "null0");
		editor.commit();
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oproecte);
		textizm = (TextView) findViewById(R.id.texttestresponse);
		Button torgovat = (Button) findViewById(R.id.go_trade);
		Button donat = (Button) findViewById(R.id.go_donat);
		OnClickListener listener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = null;
				switch(v.getId()){
					case R.id.go_trade:
						intent = new Intent(Oproecte.this, MainActivity.class);
						break;
					case R.id.go_donat:
						intent = new Intent(Oproecte.this, Sps_dialog.class);
						break;
					
				}
				startActivity(intent);
			}
		};
		torgovat.setOnClickListener(listener);
		donat.setOnClickListener(listener);
		
		
		
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		switch(id){
			case R.id.menuPurchasesListSortOrderCategory:
				Intent intent = new Intent(Oproecte.this, Oproecte.class);
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
		//слушатель
		br = new BroadcastReceiver() {
			// действия при получении сообщений
			public void onReceive(Context context, Intent intent) {
				try{
					izm();
				}
				catch(Exception e){}
			}
		};

		// создаем фильтр для BroadcastReceiver
		IntentFilter intFilt = new IntentFilter("CAT");
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
	

	private synchronized void postdonat(){
		
		
	}
	private void izm(){
		String response = pref.getString("resptest", "null");
		textizm.setText(response);
		//Toast.makeText(Oproecte.this, "рпсорорпс", Toast.LENGTH_SHORT).show();
		if(pref.getInt("flagerrorsps", 0)== 1){
			editor.putInt("flagerrorsps", 0);
			editor.commit();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setTitle("Ошибка!")
				.setMessage(pref.getString("textmesdialog", "null"))
		 .setIcon(R.drawable.logo48x48)
		 .setCancelable(false)
		 .setNegativeButton("Хорошо",
		 new DialogInterface.OnClickListener() {
		 public void onClick(DialogInterface dialog, int id) {
		 dialog.cancel();
		 }
		 });
		 AlertDialog alert = builder.create();
		 alert.show();
		 
		 }
	}
}
