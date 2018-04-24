package com.deff83.indexAPI;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import java.util.*;

public class Information extends Activity
{
	SharedPreferences pref;
	Context context = null;
	SharedPreferences.Editor editor = null;
	private RelativeLayout interceptor;
	//список в выдвижной панели
	private String[] mCatTitles;
    private ListView mDrawerListView;
	//textview
	TextView minbit;
	TextView maxbit;
	TextView colbit;
	//лимт цены на биток
	ArrayList<Double> g;
	Double cenabitok;
	Double balance;
	Double minprice, maxprice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		editor = pref.edit();
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);
		//список в выдвижной панели
		mCatTitles = getResources().getStringArray(R.array.setting);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
        // подключим адаптер для списка
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
															R.layout.draw_list_item, mCatTitles));
		mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());
		//устанавливаем количество вощможных покурок битка
		minbit = (TextView) findViewById(R.id.textmin);
		maxbit = (TextView) findViewById(R.id.textmax);
		colbit = (TextView) findViewById(R.id.colbit);
		
		cenabitok = Double.parseDouble(pref.getString("pricebit", "0.01"));
		balance = Double.parseDouble(pref.getString("balance", "0.01"));
		Integer colbitn = (int) Math.floor(balance/cenabitok);
		colbit.setText(colbitn.toString());
		
		Double mindoblcen = balance/(colbitn+1);
		minbit.setText(String.format(Locale.US, "%.4f",mindoblcen));
		
		Double maxdoblcen = balance/(colbitn);
		maxbit.setText(String.format(Locale.US, "%.4f",maxdoblcen));
		
	}
	//  Слушатель для элементов списка в выдвижной панели
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (position == 0){
				Intent intent = new Intent(Information.this, MainActivity.class);
				startActivity(intent);
			}
		}
	}
}
