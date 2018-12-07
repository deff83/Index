package com.deff83.indexAPI;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import android.view.View.*;
import java.util.*;
import android.preference.*;

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
		final Button but_del_all = (Button) findViewById(R.id.button_del_all);
		final Button but_sell_all = (Button) findViewById(R.id.sell_all);
		
		//слушатель кнопки убрать все заявки
		listbutton = new OnClickListener() {
			@Override
			public void onClick(View v) {
						switch(v.getId()){
							case R.id.sell_all:
									List<MyZayvkiForTable> listMyZayvkiForTablef = Parametr.getParametr().getMyZayvkiForTable();
									for (int i = 0; i < listMyZayvkiForTablef.size(); i++){
										MyZayvkiForTable myZayvkiForTable = listMyZayvkiForTablef.get(i);
										int idCoinz = myZayvkiForTable.getzCoin();
										int offerid = myZayvkiForTable.getOfferId();
										String name = myZayvkiForTable.getName();
										int kind = myZayvkiForTable.getKind();
										String price = myZayvkiForTable.getPrice();
										int notes = myZayvkiForTable.getNotes();
										if(kind==0){
											Parametr.getParametr().addDeystvie(new Deystvie(null, offerid, 0));
											AddOrder addorder = new AddOrder("false", idCoinz, notes, "0.0001");
											Parametr.getParametr().addDeystvie(new Deystvie(addorder, 0, 1));
										}
									}
									but_sell_all.setEnabled(false);
								break;
							case R.id.button_del_all:
									List<MyZayvkiForTable> listMyZayvkiForTable = Parametr.getParametr().getMyZayvkiForTable();
									for (int i = 0; i < listMyZayvkiForTable.size(); i++){
										MyZayvkiForTable myZayvkiForTable = listMyZayvkiForTable.get(i);
										int idCoinz = myZayvkiForTable.getzCoin();
										int offerid = myZayvkiForTable.getOfferId();
										String name = myZayvkiForTable.getName();
										String kind = myZayvkiForTable.getKindstring();
										String price = myZayvkiForTable.getPrice();
										int notes = myZayvkiForTable.getNotes();
										Parametr.getParametr().addDeystvie(new Deystvie(null, offerid, 0));
									}
									but_del_all.setEnabled(false);
								break;						
						}
			}
		};
		
		
		
		but_del_all.setOnClickListener(listbutton);
		but_sell_all.setOnClickListener(listbutton);
		
		//неактивная кнопка
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		switch(id){
			case R.id.menuPurchasesListSortOrderCategory:
				Intent intent = new Intent(Userfunction.this, Oproecte.class);
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
	//  Слушатель для элементов списка в выдвижной панели
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = null;
				switch (position){
					case 0:
						intent = new Intent(Userfunction.this, MainActivity.class);
						break;
					


					case 1:
						intent = new Intent(Userfunction.this, HistoryActivity.class);
						break;
					case 2:
						intent = new Intent(Userfunction.this, Myzayvk_act.class);
						break;
					case 3:
						intent = new Intent(Userfunction.this, Information.class);
						break;
					case 4:
						intent = new Intent(Userfunction.this, Chat.class);
						break;

					case 6:
						intent = new Intent(Userfunction.this, LoginActivity.class);
						editor.putInt("verification", 0);
						editor.commit();
						Intent i = new Intent(Userfunction.this, ServicePOST.class);
						stopService(i);
						break;
					case 5:
						intent = new Intent(Userfunction.this, Setting.class);
						break;
				}
				startActivity(intent);
			
		}
	}
}
