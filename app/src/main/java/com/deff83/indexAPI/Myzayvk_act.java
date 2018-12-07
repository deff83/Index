	package com.deff83.indexAPI;
	import android.app.*;
	import android.os.*;
	import android.content.*;
	import android.widget.*;
	import android.view.View.*;
	import android.view.*;
	import android.view.inputmethod.*;
	import android.util.*;
	import java.util.*;
	import android.widget.LinearLayout.*;
	import android.preference.*;

public class Myzayvk_act extends Activity implements ISomeModel
	{
		SharedPreferences pref;
		
		SharedPreferences.Editor editor = null;
		private RelativeLayout interceptor;
		//список в выдвижной панели
		private String[] mCatTitles;
		private ListView mDrawerListView;
		//алушатель кнопки
		OnClickListener listbutton;
		
		
		//шрифт
		Float floarsize;
		//лайаут
		FrameLayout framlay;
		LinearLayout linerLayout2;
		RelativeLayout linmycoin;
		//слушатель 
		OnClickListener gettablz;
		//текст загрузка
		TextView load;
		TextView fullfiat;
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
			pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
			
			editor = pref.edit();


			// TODO: Implement this method
			super.onCreate(savedInstanceState);
			setContentView(R.layout.zayvki_activity);
			framlay = (FrameLayout) findViewById(R.id.content_framez);
			framlay.setBackgroundDrawable(getResources().getDrawable(R.drawable.phonmyz2));
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
			
					
				
			
			
			fullfiat = (TextView) findViewById(R.id.fullfiat);
			fullfiat.setText(null);
		}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		switch(id){
			case R.id.menuPurchasesListSortOrderCategory:
				Intent intent = new Intent(Myzayvk_act.this, Oproecte.class);
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
public void tabl(){
	List<MyCoin> listmyCoins = Parametr.getParametr().getListMyCoins();
	int col_z = listmyCoins.size();
	load.setText(null);
	fullfiat.setText("Full Fiat Money");
	floarsize = Float.parseFloat(pref.getString("sizeSh", "20"));
	framlay.setBackgroundDrawable(getResources().getDrawable(R.drawable.phonmyz2));
	linerLayout2.removeAllViews();
	for (int i = 0; i < col_z; i++){
		MyCoin myCoin = listmyCoins.get(i);
		int port_count = myCoin.getNotes();
		fullfiat.setText(null);
		framlay.setBackgroundDrawable(getResources().getDrawable(R.drawable.phonmyz1));
		LayoutInflater inflate = LayoutInflater.from(Myzayvk_act.this);
		linmycoin = (RelativeLayout) inflate.inflate(R.layout.mycoins, null);
		linmycoin.setId(i + 5500);
		TextView nameCoin;
		TextView countCoin, volumecoin;
		
		RelativeLayout linimg;
		nameCoin = (TextView) linmycoin.findViewById(R.id.namecoin);
		countCoin = (TextView) linmycoin.findViewById(R.id.countcoin);
		volumecoin = (TextView) linmycoin.findViewById(R.id.volumecoin);
		linimg = (RelativeLayout) linmycoin.findViewById(R.id.linimg);
		linmycoin.setOnClickListener(gettablz);
		//
		//шрифт
		nameCoin.setTextSize(floarsize);
		countCoin.setTextSize(floarsize);
		volumecoin.setTextSize(floarsize);
		String port_name = myCoin.getName();
		Integer idcoin = myCoin.getId();
		
		List<CoinTool> listCoinTool = Parametr.getParametr().getListCoinTool();
		int col_tool = listCoinTool.size();
		String valumeCoinpr = "";
		for (int j = 0; j < col_tool; j++) {
				CoinTool coinTool = listCoinTool.get(j);
				int idcointool = coinTool.getId();
				try{
				if (idcointool==idcoin){
					Double pricecointo = Double.valueOf(coinTool.getPrice());
					Double valumDouble = pricecointo * port_count;
					valumeCoinpr = String.format(Locale.US, "%.4f", valumDouble);	
				}
				}catch(Exception e){}
		}
		
		nameCoin.setText(port_name);
		countCoin.setText(String.valueOf(port_count));
		volumecoin.setText(valumeCoinpr+"$");
		
		if (port_count > 10){port_count = 10;}
		switch (idcoin){
			case 66:
				for (int k=0; k<port_count; k++){
					ImageView imagecoin;
					imagecoin = new ImageView(Myzayvk_act.this);
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,LayoutParams.MATCH_PARENT);
					lp.leftMargin = 10*k;
					imagecoin.setImageResource(R.drawable.bch);
					linimg.addView(imagecoin, lp);
				}
				break;
			case 60:
				for (int k=0; k<port_count; k++){
					ImageView imagecoin;
					imagecoin = new ImageView(Myzayvk_act.this);
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,LayoutParams.MATCH_PARENT);
					lp.leftMargin = 10*k;
					imagecoin.setImageResource(R.drawable.btc);
					linimg.addView(imagecoin, lp);
					}
					break;
			case 67:
				for (int k=0; k<port_count; k++){
					ImageView imagecoin;
					imagecoin = new ImageView(Myzayvk_act.this);
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,LayoutParams.MATCH_PARENT);
					lp.leftMargin = 10*k;
					imagecoin.setImageResource(R.drawable.btg);
					linimg.addView(imagecoin, lp);
				}
				break;
			case 71:
				for (int k=0; k<port_count; k++){
					ImageView imagecoin;
					imagecoin = new ImageView(Myzayvk_act.this);
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,LayoutParams.MATCH_PARENT);
					lp.leftMargin = 10*k;
					imagecoin.setImageResource(R.drawable.dash);
					linimg.addView(imagecoin, lp);
				}
				break;
			case 64:
				for (int k=0; k<port_count; k++){
					ImageView imagecoin;
					imagecoin = new ImageView(Myzayvk_act.this);
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,LayoutParams.MATCH_PARENT);
					
					lp.leftMargin = 10*k;
					imagecoin.setImageResource(R.drawable.eth);
					linimg.addView(imagecoin, lp);
				}
				break;
			case 69:
				for (int k=0; k<port_count; k++){
					ImageView imagecoin;
					imagecoin = new ImageView(Myzayvk_act.this);
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,LayoutParams.MATCH_PARENT);
					lp.leftMargin = 10*k;
					imagecoin.setImageResource(R.drawable.ltc);
					linimg.addView(imagecoin, lp);
				}
				break;
			case 68:
				for (int k=0; k<port_count; k++){
					ImageView imagecoin;
					imagecoin = new ImageView(Myzayvk_act.this);
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,LayoutParams.MATCH_PARENT);
					lp.leftMargin = 10*k;
					imagecoin.setImageResource(R.drawable.xmr);
					linimg.addView(imagecoin, lp);
				}
				break;
			case 70:
				for (int k=0; k<port_count; k++){
					ImageView imagecoin;
					imagecoin = new ImageView(Myzayvk_act.this);
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,LayoutParams.MATCH_PARENT);
					lp.leftMargin = 10*k;
					imagecoin.setImageResource(R.drawable.xrp);
					linimg.addView(imagecoin, lp);
				}
				
				break;
		}
		linerLayout2.addView(linmycoin);
		}
	}
	
	

	@Override
	public void doUpdate(int argum){
		//Toast.makeText(this, "doUpdate", Toast.LENGTH_SHORT).show();
		
		switch(argum){
			case 4:
			runOnUiThread(new Runnable(){
			@Override
			public void run(){
			tabl();
			}});
			break;
		}
	}
		@Override
		protected void onStart()
		{
			Parametr.getParametr().addListener(this);
			try{
						tabl();
					}
					catch(Exception e){}
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
		protected void onPause()
		{
			
			// TODO: Implement this method
			super.onPause();
		}

		//  Слушатель для элементов списка в выдвижной панели
		private class DrawerItemClickListener implements ListView.OnItemClickListener {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = null;
				switch (position){
					default:
						intent = new Intent(Myzayvk_act.this, MainActivity.class);
						break;



					case 1:
						intent = new Intent(Myzayvk_act.this, HistoryActivity.class);
						break;
					case 2:
						intent = new Intent(Myzayvk_act.this, Userfunction.class);
						break;
					case 3:
						intent = new Intent(Myzayvk_act.this, Information.class);
						break;
					case 4:
						
						intent = new Intent(Myzayvk_act.this, Chat.class);
						break;

					case 6:
						intent = new Intent(Myzayvk_act.this, LoginActivity.class);
						editor.putInt("verification", 0);
						
						editor.commit();
						Intent i = new Intent(Myzayvk_act.this, ServicePOST.class);
						stopService(i);
						break;
					case 5:
						intent = new Intent(Myzayvk_act.this, Setting.class);
						break;
				}
				startActivity(intent);
			}
		}
	}
