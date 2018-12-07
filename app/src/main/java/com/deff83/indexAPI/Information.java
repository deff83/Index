package com.deff83.indexAPI;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import java.util.*;
import android.preference.*;
import android.view.WindowManager.LayoutParams;

public class Information extends Activity implements ISomeModel
{
	SharedPreferences pref;
	Context context = null;
	SharedPreferences.Editor editor = null;
	private RelativeLayout interceptor;
	//список в выдвижной панели
	private String[] mCatTitles;
    private ListView mDrawerListView;
	//textview
	TextView minbit, minbch;
	TextView maxbit, maxbch;
	TextView colbit, colbch;
	TextView testper, testperpp;
	//лимт цены на биток
	ArrayList<Double> g;
	Double cenabitok;
	
	Double minprice, maxprice;
	private LinearLayout lay_info_min, lay_info_col, lay_info_max;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		
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
		lay_info_min = (LinearLayout) findViewById(R.id.lay_info_min);
		lay_info_col = (LinearLayout) findViewById(R.id.lay_info_col);
		lay_info_max = (LinearLayout) findViewById(R.id.lay_info_max);
		
		testper = (TextView) findViewById(R.id.testtextp);
		testperpp = (TextView) findViewById(R.id.testtextp2);
		
		
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
	@Override
	protected void onStart()
	{
		
		
		Parametr.getParametr().addListener(this);
		try{
				Double balance = Double.valueOf(Parametr.getParametr().getSvobprice());
				List<CoinTool> listCoinTool = Parametr.getParametr().getListCoinTool();
				lay_info_min.removeAllViews();
				lay_info_col.removeAllViews();
				lay_info_max.removeAllViews();
				TextView textmininfo =  new TextView(Information.this);
					textmininfo.setLayoutParams( new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT));
					textmininfo.setText("min цена");
					lay_info_min.addView(textmininfo);
				TextView textmaxinfo =  new TextView(Information.this);
					textmaxinfo.setLayoutParams( new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT));
					textmaxinfo.setText("max цена");
					lay_info_max.addView(textmaxinfo);
				TextView textcolinfo =  new TextView(Information.this);
					textcolinfo.setLayoutParams( new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT));
					textcolinfo.setText("количество сейчас");
					lay_info_col.addView(textcolinfo);
				for (int i=0; i<listCoinTool.size(); i++){
					CoinTool coinTool = listCoinTool.get(i);
					drawView(coinTool,balance);
				}
		}catch(Exception e){};
		// TODO: Implement this method
		super.onStart();
	}
	@Override
	protected void onStop()
	{
		
		Parametr.getParametr().removeListener(this);
		// TODO: Implement this method
		super.onStop();
	}
	@Override
	public void doUpdate(int argum){
		//Toast.makeText(this, "doUpdate", Toast.LENGTH_SHORT).show();
		
		switch(argum){
			case 4:
			runOnUiThread(new Runnable(){
			@Override
			public void run(){
				
				
				
				Double balance = Double.valueOf(Parametr.getParametr().getSvobprice());
				List<CoinTool> listCoinTool = Parametr.getParametr().getListCoinTool();
				lay_info_min.removeAllViews();
				lay_info_col.removeAllViews();
				lay_info_max.removeAllViews();
				TextView textmininfo =  new TextView(Information.this);
					textmininfo.setLayoutParams( new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT));
					textmininfo.setText("min цена");
					lay_info_min.addView(textmininfo);
				TextView textmaxinfo =  new TextView(Information.this);
					textmaxinfo.setLayoutParams( new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT));
					textmaxinfo.setText("max цена");
					lay_info_max.addView(textmaxinfo);
				TextView textcolinfo =  new TextView(Information.this);
					textcolinfo.setLayoutParams( new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT));
					textcolinfo.setText("количество сейчас");
					lay_info_col.addView(textcolinfo);
				for (int i=0; i<listCoinTool.size(); i++){
					CoinTool coinTool = listCoinTool.get(i);
					drawView(coinTool,balance);
				}
				
			}
			});
			break;
		}
	}
	public void drawView(CoinTool cointool, Double balance){
		Double cena = Double.valueOf(cointool.getPrice());//цена коина
		String name = cointool.getName();
		Integer colcoin = (int) Math.floor(balance/cena); //количество коинов
		Double mincen = balance/(colcoin+1);
		String mincenstr = String.format(Locale.US, "%.4f",mincen);
		Double maxcen = balance/(colcoin);
		String maxcenstr = String.format(Locale.US, "%.4f",maxcen);
		TextView textmininfo =  new TextView(Information.this);
					textmininfo.setLayoutParams( new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT));
					textmininfo.setText(mincenstr);
					lay_info_min.addView(textmininfo);
		TextView textmaxinfo =  new TextView(Information.this);
					textmaxinfo.setLayoutParams( new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT));
					textmaxinfo.setText(maxcenstr);
					lay_info_max.addView(textmaxinfo);
		TextView textcolinfo =  new TextView(Information.this);
					textcolinfo.setLayoutParams( new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT));
					textcolinfo.setText(colcoin+""+name);
					lay_info_col.addView(textcolinfo);
				
		
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
					//intent = new Intent(Information.this, OpovActivity.class);
					intent = new Intent(Information.this, HistoryActivity.class);
					break;
				case 2:
					intent = new Intent(Information.this, Userfunction.class);
					break;
				case 3:
					intent = new Intent(Information.this, Myzayvk_act.class);
					break;
				case 4:
					intent = new Intent(Information.this, Chat.class);
					break;

				case 6:
					intent = new Intent(Information.this, LoginActivity.class);
					editor.putInt("verification", 0);
					editor.commit();
					Intent i = new Intent(Information.this, ServicePOST.class);
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
