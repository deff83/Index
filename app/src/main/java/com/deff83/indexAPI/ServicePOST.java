package com.deff83.indexAPI;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;
import java.util.*;
import android.os.*;
import okhttp3.*;
import org.json.*;
import java.io.*;
import android.widget.*;
import android.app.*;
import android.content.*;
import android.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.security.*;
import java.text.*;
import android.preference.*;


public class ServicePOST extends Service {

	private SharedPreferences pref;
	private Receiver receiver;
	private PerestanovBot perestanov_bot;
	private SharedPreferences.Editor editor = null;
	private Opiration opirat = Opiration.getOpiration();
	private Parametr param = Parametr.getParametr();
	private OpirationChat opirat_chat = OpirationChat.getOpiration();
	private ParametrChat param_chat = ParametrChat.getParametrChat();
	private ScheduledExecutorService executor;
	private ScheduledExecutorService executor2;
	private WidjetPrice widjetPrice;
	private Context context;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
	//создание службы
    @Override
    public void onCreate() {
        super.onCreate();
		pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		editor = pref.edit();
		executor = Executors.newScheduledThreadPool(1);
		executor2 = Executors.newScheduledThreadPool(1);
		String login = pref.getString("login", "");
		String password = pref.getString("password", "");
		String culture = "ru-RU";
		String wmid = pref.getString("wmid", "");
		receiver = new Receiver(this);
		Parametr.getParametr().addSinglListener(receiver);
		perestanov_bot = new PerestanovBot(this);
		Parametr.getParametr().addSinglListener(perestanov_bot);
		opirat.inicialisation(login,wmid,password,culture);
		Toast.makeText(this, "Служба создана",   Toast.LENGTH_SHORT).show();
		int zCoinSave = pref.getInt("zCoinSave", 60);
		Parametr.getParametr().setzCoinMain(zCoinSave);
		opirat_chat.inicialisation(this);
		int flag_switch_sound_widget = pref.getInt("sound_widjet", 0);
		if(flag_switch_sound_widget == 0){
			Intent intentWidjet = new Intent(this, WidjetPrice.class);
			startService(intentWidjet);
		}
		context = this;
		int colTable = pref.getInt("col_Table", 20);
		Parametr.getParametr().setcolTableOffer(colTable);
    }
	
	//запуск службы
	private Timer timer;
	
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "Служба start", Toast.LENGTH_SHORT).show();
		//try{
			
		//	timer.cancel();}
		//catch (Exception e){}
		
		

		//labelvver.setText("go");
	
		
		Runnable servisePostRun = new Runnable(){


						@Override
						public void run() {
							
							try{
									
							param.setChoiseclick(false);
							param.setIStr("OfferList");
							OrdersPriceList ordersPriceList = opirat.getOfferList(param.getzCoinMain(), param.getcolTableOffer());
							if(ordersPriceList!=null){
								param.setOrdersPriceList(ordersPriceList);
								param.upDate(1);
								intentWidjet(1);
							}
							
							param.setIStr("MyOrders");
							List<MyZayvkiForTable> listMyZayvkiForTable = opirat.myOrders();
							if(listMyZayvkiForTable!=null){
								param.upDate(2);
							}
							param.setIStr("ToolBar");
							List<CoinTool> listCoinTool = opirat.getCoinTools();
							if(listCoinTool!=null){
								param.upDate(3);
								intentWidjet(3);
							}
							param.setIStr("MyCoin");
							List<MyCoin> listmyCoins = opirat.balance();
							if(listmyCoins!=null){
								param.upDate(4);
								intentWidjet(4);
							}
							param.setIStr("HistoryOffer");
							Calendar cal = Calendar.getInstance();
							Date datenow = cal.getTime();
							cal.add(Calendar.MONTH,-1);
							Date dateMesOldnow = cal.getTime();
							
							SimpleDateFormat simdateform = new SimpleDateFormat("yyyyMMdd");
							String DateEnd = simdateform.format(datenow);
							String DateStart = simdateform.format(dateMesOldnow);
							List<MyHistoryTrade> mySdelky = opirat.getHistoryTrading(param.getzCoinMain(),DateStart,DateEnd);
							if(mySdelky!=null){
								param.upDate(7);
							}
							
							List<Deystvie> listDeystvie = param.getListDeystvie();
							if(listDeystvie.size()!=0){
								
								Deystvie deystvie = listDeystvie.get(0);
								int dey = deystvie.getDey();
								switch(dey){
									case 0://удаление
										param.setDelOrder(deystvie.getDelOrder());
										param.setBooldelOrder(true);
										break;
									case 1://добавление заявки
										param.setOrderadd(deystvie.getAddOrder());
										param.setBoolAddOrder(true);
										break;
									case 2://удалить и поставить
										param.setOrderadd(deystvie.getAddOrder());
										param.setDelOrder(deystvie.getDelOrder());
										String priceperv = deystvie.getPriceperestanov();
										if(priceperv.equals("-")==false){
											perestanov_bot.addSavePerest(priceperv, deystvie.getAddOrder().getNotes_dialog(), deystvie.getAddOrder().getPrice_dialog());
										}
										param.setPerestanov(true);
										param.setBooldelOrder(true);
										break;
									
								}
								param.removeDeystvie();
							}
							if(param.isBooldelOrder()) {
								param.setIStr("DEL");
								String dellorder = opirat.delOrder(param.getDelOrder());
								param.setBooldelOrder(false);
								param.setAnswerOrderDell(dellorder);
								if(dellorder!=null){
									param.setAnswerOrderDell(dellorder);
									param.upDate(6);
									if(param.isPerestanov()){
										param.setPerestanov(false);
										param.setBoolAddOrder(true);
									}
								}
								
							}
							if(param.isBoolAddOrder()) {
								param.setIStr("ADD");
								AddOrder addordersostin = param.getOrderadd();
								String addorder = opirat.addOrder(addordersostin.getIsbid(), addordersostin.getzCoinadd(), addordersostin.getPrice_dialog(), addordersostin.getNotes_dialog());
								param.setBoolAddOrder(false);
								
								if(addorder!=null){
									param.setAnswerOrderAdd(addorder);
									param.upDate(5);
								}
							}
							param.setPerestanov(false);
							param.setBoolAddOrder(false);
							param.setBooldelOrder(false);
							}catch(Exception e){}//param.setIStr(e.toString());param.upDate(0);};
							
						}
				
			
		};
		executor.scheduleWithFixedDelay(servisePostRun, 0, 2, TimeUnit.SECONDS);
		Runnable servisePostMessRun = new Runnable(){


						@Override
						public void run() {
							
							try{
								int chatbool = pref.getInt("sound_chat", 1);
								if(chatbool==0){
									String lastid = param_chat.getLastId();
									if (lastid==null){
										lastid = opirat_chat.getlasthundrid(90);
									}
									if (lastid!=null){
										
										List<ChatMessage> listMess =  opirat_chat.mess(lastid);
										if(listMess!=null){
											ParametrChat.getParametrChat().setListChatMessage(listMess);
											param_chat.upDateChat(1);
										}
									}
								}else{
									param_chat.upDateChat(0);
								}
							}catch(Exception e){}
							
						}
		};
		executor2.scheduleWithFixedDelay(servisePostMessRun, 0, 5, TimeUnit.SECONDS);
		return Service.START_STICKY;
		
    }
	//остановка службы
    @Override
    public void onDestroy() {
		try{
			
			timer.cancel();}
		catch (Exception e){}
		executor.shutdownNow();
        executor2.shutdownNow();
        Toast.makeText(this, "Служба остановлена", Toast.LENGTH_SHORT).show();
		super.onDestroy();
        
    }
	private void intentWidjet(int arg){
		int flag_switch_sound_widget = pref.getInt("sound_widjet", 0);
		if(flag_switch_sound_widget == 0){
			Intent intentWidjet = new Intent(context, WidjetPrice.class);
			intentWidjet.putExtra("widjetintent", arg);
			startService(intentWidjet);
		}
	}
	
	
	
	
	
	
}
