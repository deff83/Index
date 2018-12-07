package com.deff83.indexAPI;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import java.util.*;
import android.preference.*;

public class HistoryActivity extends Activity implements ISomeModel
{
	SharedPreferences pref;
	
	SharedPreferences.Editor editor = null;
	
	//список в выдвижной панели
	private String[] mCatTitles;
    private ListView mDrawerListView;
	private TableLayout tableLayout;
	private LayoutInflater inflater;
	//textview
	TextView textHistory;
			
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		
		editor = pref.edit();
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		//список в выдвижной панели
		mCatTitles = getResources().getStringArray(R.array.information);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
        // подключим адаптер для списка
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
															R.layout.draw_list_item, mCatTitles));
		mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());
		//устанавливаем количество вощможных покурок битка
		textHistory = (TextView) findViewById(R.id.textHistory);
		tableLayout = (TableLayout) findViewById(R.id.table_hist);
		inflater = LayoutInflater.from(this);
		
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		switch(id){
			case R.id.menuPurchasesListSortOrderCategory:
				Intent intent = new Intent(HistoryActivity.this, Oproecte.class);
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
		super.onStart();
	try{
		List<MyHistoryTrade> listMyHistoryTrade =  Parametr.getParametr().getListHistoryTrades();
		TableRow tr;
		tableLayout.removeAllViews();
		for (int i = 0; i < listMyHistoryTrade.size(); i++){
			MyHistoryTrade myHistoryTrade = listMyHistoryTrade.get(i);
			Integer id = myHistoryTrade.getId();
			Integer isbid = myHistoryTrade.getIsbid();
			Integer notes = myHistoryTrade.getNotes();
			String stamp = myHistoryTrade.getStamp();
			String name = myHistoryTrade.getName();
			Double price = myHistoryTrade.getPrice();
			String isbidstr = myHistoryTrade.getIsbidstr();
			tr = (TableRow) inflater.inflate(R.layout.tabl_row_history, null);
				//TextView toolidtw = (TextView) tr.findViewById(R.id.col1);
				TextView datetw = (TextView) tr.findViewById(R.id.col2);
				//TextView nametw = (TextView) tr.findViewById(R.id.col3);
				
				TextView kindtw = (TextView) tr.findViewById(R.id.col4);
				TextView notestw = (TextView) tr.findViewById(R.id.col5);
				TextView pricetw = (TextView) tr.findViewById(R.id.col6);
				
				Float floarsize = Float.parseFloat(pref.getString("sizeSh", "20"));
				Float floatsize2 = (Float) floarsize*2/3;
					//шрифт
					//toolidtw.setTextSize(floarsize);
					datetw.setTextSize(floatsize2);
					//nametw.setTextSize(floarsize);
					kindtw.setTextSize(floarsize);
					pricetw.setTextSize(floarsize);
					notestw.setTextSize(floarsize);
					textHistory.setTextSize(floarsize);
					//toolidtw.setText(String.valueOf(id));
					datetw.setText(" "+String.valueOf(stamp)+" ");
					//nametw.setText(name);
					textHistory.setText(" Истори сделок по "+name+" ");
					kindtw.setText(" "+isbidstr+" ");
					pricetw.setText(" "+price+" ");
					notestw.setText(" "+String.valueOf(notes)+" ");
					if(isbid==1){
					
						
						datetw.setBackgroundColor(getResources().getColor(R.color.titl_tablz));
						kindtw.setBackgroundColor(getResources().getColor(R.color.titl_tablz));
						pricetw.setBackgroundColor(getResources().getColor(R.color.titl_tablz));
						notestw.setBackgroundColor(getResources().getColor(R.color.titl_tablz));
						
					}
					if(isbid==0){
						datetw.setBackgroundColor(getResources().getColor(R.color.titl_tablz2));
						kindtw.setBackgroundColor(getResources().getColor(R.color.titl_tablz2));
						pricetw.setBackgroundColor(getResources().getColor(R.color.titl_tablz2));
						notestw.setBackgroundColor(getResources().getColor(R.color.titl_tablz2));
						
					}
				
				tableLayout.addView(tr);
		}
	}catch(Exception e){}
		
		Parametr.getParametr().addListener(this);
		
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
			case 7:
			runOnUiThread(new Runnable(){
			@Override
			public void run(){
				
					List<MyHistoryTrade> listMyHistoryTrade =  Parametr.getParametr().getListHistoryTrades();
					TableRow tr;
					tableLayout.removeAllViews();
					for (int i = 0; i < listMyHistoryTrade.size(); i++){
						MyHistoryTrade myHistoryTrade = listMyHistoryTrade.get(i);
				Integer id = myHistoryTrade.getId();
				Integer isbid = myHistoryTrade.getIsbid();
				Integer notes = myHistoryTrade.getNotes();
				String stamp = myHistoryTrade.getStamp();
				String name = myHistoryTrade.getName();
				Double price = myHistoryTrade.getPrice();
				String isbidstr = myHistoryTrade.getIsbidstr();
				tr = (TableRow) inflater.inflate(R.layout.tabl_row_history, null);
					//TextView toolidtw = (TextView) tr.findViewById(R.id.col1);
					TextView datetw = (TextView) tr.findViewById(R.id.col2);
					//TextView nametw = (TextView) tr.findViewById(R.id.col3);
					
					TextView kindtw = (TextView) tr.findViewById(R.id.col4);
					TextView notestw = (TextView) tr.findViewById(R.id.col5);
					TextView pricetw = (TextView) tr.findViewById(R.id.col6);
					
					Float floarsize = Float.parseFloat(pref.getString("sizeSh", "20"));
					Float floatsize2 = (Float) floarsize*2/3;
					//шрифт
					//toolidtw.setTextSize(floarsize);
					datetw.setTextSize(floatsize2);
					//nametw.setTextSize(floarsize);
					kindtw.setTextSize(floarsize);
					pricetw.setTextSize(floarsize);
					notestw.setTextSize(floarsize);
					textHistory.setTextSize(floarsize);
					//toolidtw.setText(String.valueOf(id));
					datetw.setText(" "+String.valueOf(stamp)+" ");
					//nametw.setText(name);
					textHistory.setText(" Истори сделок по "+name+" ");
					kindtw.setText(" "+isbidstr+" ");
					pricetw.setText(" "+price+" ");
					notestw.setText(" "+String.valueOf(notes)+" ");
					if(isbid==1){
					
						
						datetw.setBackgroundColor(getResources().getColor(R.color.titl_tablz));
						kindtw.setBackgroundColor(getResources().getColor(R.color.titl_tablz));
						pricetw.setBackgroundColor(getResources().getColor(R.color.titl_tablz));
						notestw.setBackgroundColor(getResources().getColor(R.color.titl_tablz));
						
					}
					if(isbid==0){
						datetw.setBackgroundColor(getResources().getColor(R.color.titl_tablz2));
						kindtw.setBackgroundColor(getResources().getColor(R.color.titl_tablz2));
						pricetw.setBackgroundColor(getResources().getColor(R.color.titl_tablz2));
						notestw.setBackgroundColor(getResources().getColor(R.color.titl_tablz2));
						
					}
					
					
					tableLayout.addView(tr);
				}
			}
			});
			break;
		}
	}
	//  Слушатель для элементов списка в выдвижной панели
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = null;
			switch (position){
				case 0:
					intent = new Intent(HistoryActivity.this, MainActivity.class);
					break;



				case 1:
					//intent = new Intent(Information.this, OpovActivity.class);
					intent = new Intent(HistoryActivity.this, MainActivity.class);
					break;
				case 2:
					//intent = new Intent(Information.this, Userfunction.class);
					intent = new Intent(HistoryActivity.this, Userfunction.class);
					break;
				case 3:
					intent = new Intent(HistoryActivity.this, Myzayvk_act.class);
					break;
				case 4:
					//editor.putInt("messflag", 1);
					//editor.commit();
					//intent = new Intent(Information.this, Chat.class);
					intent = new Intent(HistoryActivity.this, Chat.class);
					break;

				case 6:
					intent = new Intent(HistoryActivity.this, LoginActivity.class);
					editor.putInt("verification", 0);
					
					editor.commit();
					Intent i = new Intent(HistoryActivity.this, ServicePOST.class);
					stopService(i);
					break;
				case 5:
					intent = new Intent(HistoryActivity.this, Setting.class);
					break;
			}
			startActivity(intent);
		}
	}
}
