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
		//pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication());
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
		//слушатель кнопки убрать все заявки
		listbutton = new OnClickListener() {
			@Override
			public void onClick(View v) {
						switch(v.getId()){
							case R.id.func3:
								/*Set<String> gtyj = pref.getStringSet("z_perest_id", new HashSet());
								Set<String> o = new HashSet<String>();
								for (String gb : gtyj){
									o.add(gb+"");
								}
								gtyj.removeAll(o);
								Toast.makeText(Userfunction.this, "рпсорорпс", Toast.LENGTH_SHORT).show();
								editor.putStringSet("z_perest_id", gtyj);*/
								editor.remove("z_perest_id");
								editor.remove("z_perest_price");
								editor.commit();
								break;
						}
						int i = pref.getInt("col_z", 0);
						//выполнение функции удаления заявок
						if (i != 0){
							switch(v.getId()){
								case R.id.button_del_all:
									editor.putInt("del_all", 1);
									editor.commit();
									break;
								
								
									
							}
						}
									
			}
		};
		
		
		Button but_del_all = (Button) findViewById(R.id.button_del_all);
		Button but_sell_all = (Button) findViewById(R.id.func2);
		Button but_clen_data = (Button) findViewById(R.id.func3);
		but_del_all.setOnClickListener(listbutton);
		but_sell_all.setOnClickListener(listbutton);
		but_clen_data.setOnClickListener(listbutton);
		but_sell_all.setEnabled(false);//неактивная кнопка
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
						intent = new Intent(Userfunction.this, OpovActivity.class);
						break;
					case 2:
						intent = new Intent(Userfunction.this, Myzayvk_act.class);
						break;
					case 3:
						intent = new Intent(Userfunction.this, Information.class);
						break;
					case 4:
						editor.putInt("messflag", 1);
						editor.commit();
						intent = new Intent(Userfunction.this, Chat.class);
						break;

					case 6:
						intent = new Intent(Userfunction.this, LoginActivity.class);
						editor.putInt("verification", 0);
						editor.putInt("col_tabl", 0);
						editor.putInt("tabl_hight", 0);
						editor.commit();
						Intent i = new Intent(Userfunction.this, PlayService.class);
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
