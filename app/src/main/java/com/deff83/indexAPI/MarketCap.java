package com.deff83.indexAPI;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.LinearLayout.*;
import java.util.*;
import android.text.*;

import javax.xml.parsers.*;
import org.xml.sax.*;
import java.io.*;
import org.jsoup.select.*;
import org.jsoup.nodes.*;
import org.jsoup.*;
import android.graphics.*;
import android.text.method.*;
import android.preference.*;
import java.net.*;
import android.net.*;
import com.squareup.picasso.*;
import java.text.*;

public class MarketCap extends Activity 
{
	SharedPreferences pref;
	SharedPreferences.Editor editor = null;
	//список в выдвижной панели
	private String[] mCatTitles;
	private ListView mDrawerListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		editor = pref.edit();
		
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.market_cap);
		
		//список в выдвижной панели
		mCatTitles = getResources().getStringArray(R.array.chat);
		mDrawerListView = (ListView) findViewById(R.id.left_drawermes);
		// подключим адаптер для списка
		mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
															R.layout.draw_list_item, mCatTitles));
		mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());
		
		
		
		
		
		
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		switch(id){
			case R.id.menuPurchasesListSortOrderCategory:
				Intent intent = new Intent(MarketCap.this, Oproecte.class);
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
			Intent intent = new Intent(MarketCap.this, MainActivity.class);
			switch (position){
				case 0:
					
					intent = new Intent(MarketCap.this, MainActivity.class);
					break;
				case 1:
					intent = new Intent(MarketCap.this, HistoryActivity.class);
					break;
				case 2:
					intent = new Intent(MarketCap.this, Userfunction.class);
					break;
				case 3:
					intent = new Intent(MarketCap.this, Myzayvk_act.class);
					break;
				case 4:
					intent = new Intent(MarketCap.this, Information.class);
					break;
				case 5:
					
					intent = new Intent(MarketCap.this, Setting.class);
					break;

				case 6:
					intent = new Intent(MarketCap.this, LoginActivity.class);
					editor.putInt("verification", 0);
				
					editor.commit();
					Intent i = new Intent(MarketCap.this, ServicePOST.class);
					stopService(i);
					break;
			}
			
			startActivity(intent);
			
		
		}
	}
}
