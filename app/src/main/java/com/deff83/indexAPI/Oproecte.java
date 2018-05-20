package com.deff83.indexAPI;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import java.util.*;
import android.preference.*;
import android.view.View.*;

public class Oproecte extends Activity
{
	SharedPreferences pref;
	Context context = null;
	SharedPreferences.Editor editor = null;
	private RelativeLayout interceptor;
	//список в выдвижной панели
	private String[] mCatTitles;
    private ListView mDrawerListView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		//pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication());
		editor = pref.edit();
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oproecte);
		Button torgovat = (Button) findViewById(R.id.go_trade);
		Button donat = (Button) findViewById(R.id.go_donat);
		OnClickListener listener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				switch(v.getId()){
					case R.id.go_trade:
						Intent intent = new Intent(Oproecte.this, MainActivity.class);
						startActivity(intent);
						break;
					case R.id.go_donat:
						break;
				}
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
	
}
