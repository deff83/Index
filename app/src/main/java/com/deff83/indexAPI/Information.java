package com.deff83.indexAPI;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import java.util.*;
import android.preference.*;

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
	TextView testper, testperpp;
	//лимт цены на биток
	ArrayList<Double> g;
	Double cenabitok;
	Double balance;
	Double minprice, maxprice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		//pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication());
		editor = pref.edit();
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);
		//список в выдвижной панели
		mCatTitles = getResources().getStringArray(R.array.information);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
        // подключим адаптер для списка
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
															R.layout.draw_list_item, mCatTitles));
		mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());
		//устанавливаем количество вощможных покурок битка
		minbit = (TextView) findViewById(R.id.textmin);
		maxbit = (TextView) findViewById(R.id.textmax);
		colbit = (TextView) findViewById(R.id.colbit);
		testper = (TextView) findViewById(R.id.testtextp);
		testperpp = (TextView) findViewById(R.id.testtextp2);
		
		cenabitok = Double.parseDouble(pref.getString("pricebit", "0.01"));
		balance = Double.parseDouble(pref.getString("balance", "0.01"));
		Integer colbitn = (int) Math.floor(balance/cenabitok);
		colbit.setText(colbitn.toString());
		
		Double mindoblcen = balance/(colbitn+1);
	 	minbit.setText(String.format(Locale.US, "%.4f",mindoblcen));
		
		Double maxdoblcen = balance/(colbitn);
		maxbit.setText(String.format(Locale.US, "%.4f",maxdoblcen));
		Set<String> gt = pref.getStringSet("z_perest_id", new HashSet<String>());
		String yu = " ";
		for (String b : gt){
			yu = yu + b + " ";
		}
		testper.setText(yu);
		
		Set<String> gtpr = pref.getStringSet("z_perest_price", new HashSet<String>());
		String yupr = " ";
		for (String b : gtpr){
			yupr = yupr + b + " ";
		}
		testperpp.setText(yupr);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		switch(id){
			case R.id.menuPurchasesListSortOrderCategory:
				Intent intent = new Intent(Information.this, Oproecte.class);
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
	//  Слушатель для элементов списка в выдвижной панели
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = null;
			switch (position){
				case 0:
					intent = new Intent(Information.this, MainActivity.class);
					break;



				case 1:
					intent = new Intent(Information.this, OpovActivity.class);
					break;
				case 2:
					intent = new Intent(Information.this, Userfunction.class);
					break;
				case 3:
					intent = new Intent(Information.this, Myzayvk_act.class);
					break;
				case 4:
					editor.putInt("messflag", 1);
					editor.commit();
					intent = new Intent(Information.this, Chat.class);
					break;

				case 6:
					intent = new Intent(Information.this, LoginActivity.class);
					editor.putInt("verification", 0);
					editor.putInt("col_tabl", 0);
					editor.putInt("tabl_hight", 0);
					editor.commit();
					Intent i = new Intent(Information.this, PlayService.class);
					stopService(i);
					break;
				case 5:
					intent = new Intent(Information.this, Setting.class);
					break;
			}
			startActivity(intent);
		}
	}
}
