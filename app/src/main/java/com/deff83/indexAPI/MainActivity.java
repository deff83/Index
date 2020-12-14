package com.deff83.indexAPI;
import android.app.*;
import android.net.Uri;
import android.os.*;
import okhttp3.*;
import java.io.*;

import android.provider.Settings;
import android.widget.*;
import java.lang.Math;
import org.w3c.dom.*;
import java.util.*;
import org.json.*;
import android.view.*;
import android.content.*;
import android.view.inputmethod.*;
import android.view.View.*;
import android.text.*;
import android.graphics.*;
import android.preference.*;

import android.view.WindowManager.LayoutParams;

import android.view.ViewDebug.*;
import android.widget.Toolbar.*;
import android.support.v4.content.*;
import android.content.res.*;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.widget.*;
import android.transition.*;
import android.graphics.drawable.*;
public class MainActivity extends Activity implements ISomeModel
{
	private int CommonVariables = 5463 & 0xffffff00;

	//список в выдвижной панели
	private String[] mCatTitles;
    private ListView mDrawerListView;

	static BroadcastReceiver br;
	static IntentFilter intFilt;
	private TextView textSirviceInfo, answerPOST, textrab_gud;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor = null;
	private LayoutInflater inflater;
	private TableLayout tableLayout, tableLayout2;
	private LinearLayout llt;
	private OnClickListener gettablz;
	private OnClickListener getButtonText;
	private ImageView imageinfo;
	private 	int i = 0;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			checkDrawOverlayPermission();
		}


		pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		editor = pref.edit();
		int verification = pref.getInt("verification", 0);
		
		if(verification == 0){
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent);
		}
		
        super.onCreate(savedInstanceState);
		
        setContentView(R.layout.main);
		
		//список в выдвижной панели
		mCatTitles = getResources().getStringArray(R.array.cats_array_ru);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
		// подключим адаптер для списка
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this, R.layout.draw_list_item, mCatTitles));
		mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());
		
		imageinfo = (ImageView) findViewById(R.id.circlinfo);
		//информация о работе сервиса
		textSirviceInfo = (TextView) findViewById(R.id.textSirviceInfo);
		textrab_gud = (TextView) findViewById(R.id.rab_gud);
		
		textrab_gud.setOnTouchListener(new View.OnTouchListener() {  
							@Override  
							public boolean onTouch(View v, MotionEvent event) {  
								
								switch(event.getAction()){  
									case MotionEvent.ACTION_DOWN:  
											
										break;
									case MotionEvent.ACTION_UP:  
											
										break;
									case MotionEvent.ACTION_MOVE:  
											
										break;
								}  
								return false;  
							}  
		}
		);
				
		
		answerPOST = (TextView) findViewById(R.id.answerPOST);
		inflater = LayoutInflater.from(this);
		tableLayout = (TableLayout) findViewById(R.id.table);
		tableLayout2 = (TableLayout) findViewById(R.id.table2);
		llt = (LinearLayout) findViewById(R.id.layout_button);
		if(verification == 1){
			Intent intent = new Intent(MainActivity.this, ServicePOST.class);
			startService(intent);
		}
		//слушатель кнопок туулбара коинов
		getButtonText = new OnClickListener() {
		 @Override
		 public void onClick(View v) {
			
			v.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_d));
			int buttonId = v.getId() - 1000;
			for (int j = 0; j < col_toolOLD; j++){
					if (buttonId != j){
						
					}
					
			}
			Parametr.getParametr().setChoiseclick(true);
			CoinTool coinTool = listCoinTool.get(buttonId);
			Parametr.getParametr().setzCoinMain(coinTool.getId());
			count_tabl2 = 0;
			tableLayout.removeAllViews();
			setTitle("Index API - "+coinTool.getName());
			editor.putInt("zCoinSave", coinTool.getId());
			editor.commit();
		 }
		 };
		//слушатель ордеров
		gettablz = new OnClickListener(){
			 @Override
			 public void onClick(View v){
				 int j_z = v.getId() - 4000;
				 //окрашиваем строчку
				 final TextView toolidtw_z;
				 final TextView offeridtw_z;
				 final TextView nametw_z;
				 final TextView kindtw_z;
				 final TextView pricetw_z;
				 final TextView notestw_z;

				 toolidtw_z = (TextView) v.findViewById(R.id.colm1);
				 offeridtw_z = (TextView) v.findViewById(R.id.colm2);
				 nametw_z = (TextView) v.findViewById(R.id.colm3);
				 kindtw_z = (TextView) v.findViewById(R.id.colm4);
				 pricetw_z = (TextView) v.findViewById(R.id.colm5);
				 notestw_z = (TextView) v.findViewById(R.id.colm6);
				 
				 toolidtw_z.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				 offeridtw_z.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				 nametw_z.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				 kindtw_z.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				 pricetw_z.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				 notestw_z.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				 //получаем данные				 
				final MyZayvkiForTable myZayvkiForTable = listMyZayvkiForTable.get(j_z);
				                                                
				 int z_id = myZayvkiForTable.getzCoin();
				final int offerid = myZayvkiForTable.getOfferId();
				String name_z = myZayvkiForTable.getName();
				String kind = myZayvkiForTable.getKindstring();
				String price_z = myZayvkiForTable.getPrice();
				int notes_z = myZayvkiForTable.getNotes();
				 //выводим диалоговое окно
				 AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				 builder.setTitle(name_z)
					 .setMessage("Выберите действие для заявки" + "  "+z_id + "\n" + name_z +"  " +price_z +"  (" + notes_z +" нот)")    
					 .setCancelable(true)
					 .setNegativeButton(R.string.delz,
					 new DialogInterface.OnClickListener() {
						 public void onClick(DialogInterface dialog, int id) {
							
							//убираем цвет нажатой таблицы
							 toolidtw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							 offeridtw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							 nametw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							 kindtw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							 pricetw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							 notestw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							
							Parametr.getParametr().addDeystvie(new Deystvie(null, offerid, 0));
							
							dialog.cancel();
						 }
					 })
					 .setPositiveButton(R.string.editz, 
					 new DialogInterface.OnClickListener() {
						 public void onClick(DialogInterface dialog, int id) {
							 
							 //убираем цвет нажатой таблицы
							 toolidtw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							 offeridtw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							 nametw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							 kindtw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							 pricetw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							 notestw_z.setBackgroundColor(getResources().getColor(R.color.colorTabl));
							Parametr.getParametr().setDelOrder(offerid);
							Parametr.getParametr().setBooldelOrder(true);
							Parametr.getParametr().setRedMyZayvkiForTable(myZayvkiForTable);
							Parametr.getParametr().setRedaction(true);
							 dialog.cancel();
						 }
					 });
					 
					 /*.setNeutralButton(R.string.serdialog,        //для VIP
					 new DialogInterface.OnClickListener() {
						 public void onClick(DialogInterface dialog, int id) {
							 //нажата кнопка другие функции
							 Parametr.getParametr().setRedMyZayvkiForTable(myZayvkiForTable);
							 Intent intent = new Intent(MainActivity.this, Functred_dialog.class);
							 startActivity(intent);
							 }});*/
				AlertDialog alert = builder.create();
				alert.show();
			 }
		 };
	//инициализация кнопок покупки продажи
		final Button btnZ_Buy = (Button) findViewById(R.id.zayvka_start);
        final Button btnZ_Sell = (Button) findViewById(R.id.zayvka_stop);
		btnZ_Buy.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonred));
		btnZ_Sell.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttongreen));
		//слушатель кнопки купить
		btnZ_Buy.setOnTouchListener(new  View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						//кнопка нажата, присваиваем один цвет
						btnZ_Buy.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonredup));
						return true;
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						//кнопка отжата, присваиваем другой цвет
						btnZ_Buy.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonred));
						Parametr.getParametr().setBuysell(0);
						Intent intent = new Intent(MainActivity.this, Buy_dialog.class);
						
						startActivity(intent);
						return true;
					}
					return false;
				}
				});
		//слушатель кнопки продать
		btnZ_Sell.setOnTouchListener(new  View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						//кнопка нажата, присваиваем один цвет
						btnZ_Sell.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttongreenup));
						return true;
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						//кнопка отжата, присваиваем другой цвет
						btnZ_Sell.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttongreen));
						Parametr.getParametr().setBuysell(1);
						Intent intent = new Intent(MainActivity.this, Buy_dialog.class);
						startActivity(intent);
					return true;
					}
					return false;
				}
			});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		switch(id){
			case R.id.menuPurchasesListSortOrderCategory:
				Intent intent = new Intent(MainActivity.this, Oproecte.class);
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
		textSirviceInfo.setText("Загрузка...");
		String balance = Parametr.getParametr().getBalance();
		String svobprice = Parametr.getParametr().getSvobprice();
		answerPOST.setText("");
		TextView text_balance = (TextView) findViewById(R.id.text2);
		text_balance.setText(balance + "$(" + svobprice + ")");
		try{
		tabl_price();
		}catch(Exception e){}
		try{
		tabl_z();
		}catch(Exception e){}
		try{
		coin_prices();
		}catch(Exception e){}
		
		Parametr.getParametr().addListener(this);
		
		Toast.makeText(this, "Добро пожаловать...", Toast.LENGTH_SHORT).show();
		
	
		
		// TODO: Implement this method
		super.onStart();
	}
	@Override
	public void doUpdate(int argum){
		//Toast.makeText(this, "doUpdate", Toast.LENGTH_SHORT).show();
		
		switch(argum){
			case 0:
				final int param = Parametr.getParametr().getI();
				final String setIStr = Parametr.getParametr().getIStr();
				
				runOnUiThread(new Runnable(){
				@Override
				public void run(){
					textSirviceInfo.setText(setIStr+":"+param+"");
					switch (setIStr){
					case "OfferList":
							setColorImg(1);
						break;
					case "MyOrders":
							setColorImg(2);
						break;
					case "ToolBar":
							setColorImg(3);
						break;
					case "MyCoin":
							setColorImg(4);
						break;
					case "ADD":
							setColorImg(5);
						break;
					case "DEL":
							setColorImg(6);
						break;
					case "HistoryOffer":
							setColorImg(7);
						break;
					}
				}
				});
			break;
			case 1:	tabl_price();//answerPOST.setText("");
			break;
			case 2: tabl_z();//answerPOST.setText("");
			break;
			case 3:coin_prices();//answerPOST.setText("");
			break;
			case 4:
				final String balance = Parametr.getParametr().getBalance();
				final String svobprice = Parametr.getParametr().getSvobprice();
				runOnUiThread(new Runnable(){
				@Override
				public void run(){
					answerPOST.setText("");
					TextView text_balance = (TextView) findViewById(R.id.text2);
					text_balance.setText(balance + "$(" + svobprice + ")");
				}
				});
			
			break;
			case 5:runOnUiThread(new Runnable(){
				@Override
				public void run(){
				answerPOST.setText(Parametr.getParametr().getAnswerOrderAdd());
				
				}
				});
			break;
			case 6:runOnUiThread(new Runnable(){
				@Override
				public void run(){
				answerPOST.setText(Parametr.getParametr().getAnswerOrderDell());
				if(Parametr.getParametr().isRedaction()){
					MyZayvkiForTable myZayvkiForTable = Parametr.getParametr().getRedMyZayvkiForTable();
					int kind = myZayvkiForTable.getKind();
					if(kind==0){//продать
						Parametr.getParametr().setBuysell(1);
						Intent intentred = new Intent(MainActivity.this, Buy_dialog.class);
						startActivity(intentred);
					}
					if(kind==1){//купить
						Parametr.getParametr().setBuysell(0);
						Intent intentredd = new Intent(MainActivity.this, Buy_dialog.class);
						startActivity(intentredd);

					}
					Parametr.getParametr().setRedaction(false);
				}
				}
				});
			break;
		}
	
	}
	@Override
	protected void onStop()
	{
		try{
			unregisterReceiver(br);
		}catch(Exception e){}
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
	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		
		super.onDestroy();
	}
	
	//функция отображения прайса
	Integer count_tabl2 = 0;
	Float floarsize;
	private int tabl_hightOLD = 0;
	public void tabl_price (){
		OrdersPriceList ordersPriceList = Parametr.getParametr().getOrdersPriceList();
					final List<OrderPrice> listbuy = ordersPriceList.getListbuy();//левая колонка
					final List<OrderPrice> listsell = ordersPriceList.getListsell();//левая колонка
					
					final int tabl_hight = Math.max(listbuy.size(),listsell.size());
					if(tabl_hight!=tabl_hightOLD){
						count_tabl2 = 0;
					}
					tabl_hightOLD = tabl_hight;
		runOnUiThread(new Runnable(){
			@Override
			public void run(){
						answerPOST.setText("");
					
					TableRow tr;
					//размер шрифта
					floarsize = Float.parseFloat(pref.getString("sizeSh", "20"));
					if ( count_tabl2 != 1){
						tableLayout.removeAllViews();
						tr = (TableRow) inflater.inflate(R.layout.tabl_row, null);
						TextView tv_sh = (TextView) tr.findViewById(R.id.col1);
						TextView tv2_sh = (TextView) tr.findViewById(R.id.col4);
						TextView tv_notes_sh = (TextView) tr.findViewById(R.id.col2);
						TextView tv2_notes_sh = (TextView) tr.findViewById(R.id.col5);
						tv_sh.setTextSize(floarsize);
						tv_sh.setText("      Цена      ");
						tv_sh.setGravity(Gravity.CENTER);
						tv_sh.setBackgroundColor(getResources().getColor(R.color.titl_tablz));
						tv2_sh.setTextSize(floarsize);
						tv2_sh.setText("      Цена      ");
						tv2_sh.setGravity(Gravity.CENTER);
						tv2_sh.setBackgroundColor(getResources().getColor(R.color.titl_tablz2));
						tv_notes_sh.setTextSize(floarsize);
						tv_notes_sh.setText(" Нот ");
						tv_notes_sh.setGravity(Gravity.CENTER);
						tv_notes_sh.setBackgroundColor(getResources().getColor(R.color.titl_tablz));
						tv2_notes_sh.setTextSize(floarsize);
						tv2_notes_sh.setText(" Нот ");
						tv2_notes_sh.setGravity(Gravity.CENTER);
						tv2_notes_sh.setBackgroundColor(getResources().getColor(R.color.titl_tablz2));
						tableLayout.addView(tr);
								for (int i = 0; i < tabl_hight; i++) {
											
											//Создаем строку таблицы, используя шаблон из файла /res/layout/table_row.xml
											tr = (TableRow) inflater.inflate(R.layout.tabl_row, null);
											tr.setId(i + 3000);
											tableLayout.addView(tr);
								}
								count_tabl2 = 1;
								//editor.commit();*/
					 }
					for (int i = 0; i < tabl_hight; i++) {
								tr = (TableRow) findViewById(i + 3000);
								TextView tv = (TextView) tr.findViewById(R.id.col1);
								TextView tv2 = (TextView) tr.findViewById(R.id.col4);
								
								
								//tr.setOnClickListener(prlistener);
								//tv2.setOnClickListener(prlistener);
						String xx = "";
						String xv = "";
						if(i<listbuy.size())xx=""+listbuy.get(i).getPrice();
						if(i<listsell.size())xv=""+listsell.get(i).getPrice();
						
						//размер шрифта
						tv.setTextSize(floarsize);
						tv2.setTextSize(floarsize);
						//значения прайса
						tv.setText(xx);
						tv2.setText(xv);
						//функция сравнения номера заявки
						String offerid = "0";
						String offerid_prod = "0";
						if(i<listbuy.size())offerid=""+listbuy.get(i).getOfferid();
						if(i<listsell.size())offerid_prod=""+listsell.get(i).getOfferid();
						
						TextView tv_notes = (TextView) tr.findViewById(R.id.col2);
						TextView tv2_notes = (TextView) tr.findViewById(R.id.col5);
						tv_notes.setTextSize(floarsize);
						tv2_notes.setTextSize(floarsize);
						String xx_notes = "";
						String xv_notes = "";
						if(i<listbuy.size())xx_notes=""+listbuy.get(i).getNotes();
						if(i<listsell.size())xv_notes=""+listsell.get(i).getNotes();
						
						tv_notes.setText(xx_notes);
						tv2_notes.setText(xv_notes);
						
			
			
			
					//
					if (offerid.equals("0")){
						tv.setTypeface(null);
						tv.setBackgroundColor(getResources().getColor(R.color.colorTabl));
						tv_notes.setBackgroundColor(getResources().getColor(R.color.colorTabl));
						
					} else{
						tv.setTypeface(null, Typeface.BOLD);
						tv.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
						tv_notes.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
					}
					if (offerid_prod.equals("0")){
						tv2.setTypeface(null);
						tv2.setBackgroundColor(getResources().getColor(R.color.colorTabl));
						tv2_notes.setBackgroundColor(getResources().getColor(R.color.colorTabl));
						
					}else{
						tv2.setTypeface(null, Typeface.BOLD);
						tv2.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
						tv2_notes.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
					}
					}
		}});
		}
		
	private List<MyZayvkiForTable> listMyZayvkiForTable;
	public void tabl_z(){
		
		listMyZayvkiForTable = Parametr.getParametr().getMyZayvkiForTable();
					
		runOnUiThread(new Runnable(){
			@Override
			public void run(){
		answerPOST.setText("");
		
		int col_z = listMyZayvkiForTable.size();
		
		floarsize = Float.parseFloat(pref.getString("sizeSh", "20"));
		
			tableLayout2.removeAllViews();
			TableRow tr2;
			
			for (int i = 0; i < col_z; i++){
				MyZayvkiForTable myZayvkiForTable = listMyZayvkiForTable.get(i);
				tr2 = (TableRow) inflater.inflate(R.layout.tabl2_row, null);
				tr2.setId(i + 4000);
				tr2.setOnClickListener(gettablz);
				
				TextView toolidtw;
				TextView offeridtw;
				TextView nametw;
				TextView kindtw;
				TextView pricetw;
				TextView notestw;
				
				toolidtw = (TextView) tr2.findViewById(R.id.colm1);
				offeridtw = (TextView) tr2.findViewById(R.id.colm2);
				nametw = (TextView) tr2.findViewById(R.id.colm3);
				kindtw = (TextView) tr2.findViewById(R.id.colm4);
				pricetw = (TextView) tr2.findViewById(R.id.colm5);
				notestw = (TextView) tr2.findViewById(R.id.colm6);
					
				//шрифт
				toolidtw.setTextSize(floarsize);
				offeridtw.setTextSize(floarsize);
				nametw.setTextSize(floarsize);
				kindtw.setTextSize(floarsize);
				pricetw.setTextSize(floarsize);
				notestw.setTextSize(floarsize);
				
				int idCoinz = myZayvkiForTable.getzCoin();
				int offerid = myZayvkiForTable.getOfferId();
				String name = myZayvkiForTable.getName();
				String kind = myZayvkiForTable.getKindstring();
				String price = myZayvkiForTable.getPrice();
				int notes = myZayvkiForTable.getNotes();
					
				//проверка нажата ли таблица для установки цвета
				//int pressz = pref.getInt("press_tabl", 3);
				//if (pressz == offerid){
				//	toolidtw.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				//	offeridtw.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				//	nametw.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				//	kindtw.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				//	pricetw.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				//	notestw.setBackgroundColor(getResources().getColor(R.color.colorMyZayvka));
				//}
				
				kindtw.setText(kind);
				toolidtw.setText(String.valueOf(idCoinz));
				offeridtw.setText(String.valueOf(offerid));
				nametw.setText(name);
				pricetw.setText(price);
				notestw.setText(String.valueOf(notes));
				tableLayout2.addView(tr2);
			}
			
	}});
	}
	
	//функция отображения кнопок туулбара цен коинов
	Integer count_coin2 = 0;
	Integer col_toolOLD = 0;
	private List<CoinTool> listCoinTool;
	public void coin_prices(){
		listCoinTool = Parametr.getParametr().getListCoinTool();
					
		runOnUiThread(new Runnable(){
			@Override
			public void run(){
		answerPOST.setText("");
		
		int col_tool = listCoinTool.size();
		if (col_tool != col_toolOLD || count_coin2 != 1){
			llt.removeAllViews();
			for (int i = 0; i < col_tool; i++) {
				Button btn =  new Button(MainActivity.this);
					btn.setLayoutParams( new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT));
					btn.setTextSize(12);
					btn.setId(i + 1000);
					btn.setOnClickListener(getButtonText);
					llt.addView(btn);
			}
			col_toolOLD = col_tool;
			count_coin2 = 1;
			}
			Button but;
			for (int i = 0; i < col_toolOLD; i++) {
				CoinTool coinTool = listCoinTool.get(i);
				String name = coinTool.getName();
				String price = coinTool.getPrice();
				but = (Button) findViewById(i + 1000);
				but.setText(name + "\n" + price);
				but.setBackgroundDrawable(getResources().getDrawable(R.drawable.button));
				if(coinTool.getId()==Parametr.getParametr().getzCoinMain())but.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_up));
							/*price_double = Double.parseDouble(price);
							i_file_coin = pref.getString(i_str, "0.0");
							i_file_coin_double = Double.parseDouble(i_file_coin);
							String up_button = pref.getString(i_str +"2", "0");
							if (up_button.equals("0")){
									but.setBackgroundDrawable(getResources().getDrawable(R.drawable.button));
							}
							else {
									but.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_up));
									
									editor.putString(i_str + "2", "1");
									editor.commit();
							}
							try{
										if (i_str_color == 0){
													but.setTextColor(getResources().getColor( R.color.colorGreen));
										}
										if(i_str_color == 1){
													but.setTextColor(getResources().getColor( R.color.colorRed));
										}
										else{}
							}
							catch(Exception e){

							}
							if (price_double > (i_file_coin_double + (price_double * 0.001))){
										editor.putInt(i_str + "1", 0);
							}
							if (price_double < (i_file_coin_double - (price_double * 0.001) )){
										editor.putInt(i_str + "1", 1);
							}
							if (i_file_coin_double == 0){
										editor.putInt(i_str + "1", 2);
							}
							editor.putString(i_str, price);
							editor.putString("otvet_prices", "0");

							editor.commit();*/
		}
	 	
	
	
	}});
	};
	


//  Слушатель для элементов списка в выдвижной панели
private class DrawerItemClickListener implements ListView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
		switch (position){
			case 0:
				intent = new Intent(MainActivity.this, HistoryActivity.class);
				break;
			case 1:
				intent = new Intent(MainActivity.this, Userfunction.class);
			break;
			case 2:
				intent = new Intent(MainActivity.this, Myzayvk_act.class);
			break;
			case 3:
				intent = new Intent(MainActivity.this, Information.class);
			break;
			case 4:
				intent = new Intent(MainActivity.this, Chat.class);
			break;
			//case 5:
				//intent = new Intent(MainActivity.this, MarketCap.class);
			//break;
			case 5:
				intent = new Intent(MainActivity.this, Setting.class);
			break;
			case 6:
				intent = new Intent(MainActivity.this, LoginActivity.class);
				editor.putInt("verification", 0);
				
				editor.commit();
				Intent i = new Intent(MainActivity.this, ServicePOST.class);
				stopService(i);
				
				break;
			default:
			intent = new Intent(MainActivity.this, LoginActivity.class);
			editor.putInt("verification", 0);
				
				editor.commit();
				Intent is = new Intent(MainActivity.this, ServicePOST.class);
				stopService(is);
		}
		startActivity(intent);
    }
}
private void setColorImg(int colorim){
	switch(colorim){
		case 1:
			imageinfo.setImageResource(R.drawable.circle_red);
		break;
	case 2:
			imageinfo.setImageResource(R.drawable.circle_blue);
		break;
	case 3:
			imageinfo.setImageResource(R.drawable.circle_green);
		break;
	case 4:
			imageinfo.setImageResource(R.drawable.circle_yellow);
		break;
	case 5:
			imageinfo.setImageResource(R.drawable.circle_black);
		break;
	case 6:
			imageinfo.setImageResource(R.drawable.circle_white);
		break;
	case 7:
			imageinfo.setImageResource(R.drawable.circle_violet);
		break;
	}
}

	public void checkDrawOverlayPermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (!Settings.canDrawOverlays(getApplicationContext())) {
				Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + this.getPackageName()));
				startActivityForResult(intent, CommonVariables);
			} else {
				//createView();
			}
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CommonVariables) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				if (Settings.canDrawOverlays(getApplicationContext())) {
					//createView();
				}
			}
		}
	}
}
