package com.deff83.indexAPI;
import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.view.WindowManager.LayoutParams;
import android.widget.ActionMenuView.*;
import android.graphics.*;
import java.util.*;
import android.preference.*;

public class WidjetPrice extends Service
{
	
			SharedPreferences pref;
			SharedPreferences.Editor editor = null;
			//поля окна виджета
			private WindowManager windowManager;  
			private RelativeLayout rlay;
			private ImageView floatingFaceBubble;  
			private TextView textmin, tv1, tv2, textpricetek;
			private LinearLayout lincoin;
			private Timer timer;
			private TimerTask tasttime;
			private Runnable runnablex_timer;
			private Thread thredtime;
			private int zCoin;
			
			
			
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
				zCoin = Parametr.getParametr().getzCoinMain();
				
				 Toast.makeText(this, "Виджет запущен",
				  Toast.LENGTH_SHORT).show();
				rlay = new RelativeLayout(this);
				rlay.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				
				LayoutInflater inflater1 = LayoutInflater.from(this);
				LinearLayout tr = (LinearLayout) inflater1.inflate(R.layout.widjet, null);
				tv1 = (TextView) tr.findViewById(R.id.wid1);
				tv2 = (TextView) tr.findViewById(R.id.wid4);
				tv1.setTextSize(Float.parseFloat(pref.getString("sizeSh", "20")));
				tv2.setTextSize(Float.parseFloat(pref.getString("sizeSh", "20")));
				lincoin = (LinearLayout) tr.findViewById(R.id.widcoins);
				textpricetek = (TextView) tr.findViewById(R.id.textpricewj);
				tv1.setText("----");
				tv2.setText("----");
				
				rlay.addView(tr);
				windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);  
				//here is all the science of params
				final LayoutParams myParams = new WindowManager.LayoutParams(
					LayoutParams.WRAP_CONTENT,  
					LayoutParams.WRAP_CONTENT,
					android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ? LayoutParams.TYPE_APPLICATION_OVERLAY : LayoutParams.TYPE_PHONE,
					LayoutParams.FLAG_NOT_FOCUSABLE,  
					PixelFormat.TRANSLUCENT);
				myParams.gravity = Gravity.TOP | Gravity.LEFT;  
				int xzap = pref.getInt("xzap", 0);
				int yzap = pref.getInt("yzap", 100);
				myParams.x=xzap;  
				myParams.y=yzap;  
				// add a floatingfacebubble icon in window
				try {
					windowManager.addView(rlay, myParams);
				}catch (Exception e){
					System.out.println("bb"+e.getMessage());
				}
				
				try{  
					rlay.setOnTouchListener(new View.OnTouchListener() {  
							WindowManager.LayoutParams paramsT = myParams;
							private int initialX;  
							private int initialY;  
							private float initialTouchX;  
							private float initialTouchY;  
							private long touchStartTime = 0;  
							@Override  
							public boolean onTouch(View v, MotionEvent event) {  
								timer = new Timer();
								tasttime = new TimerTask(){
									@Override
									public void run(){
										editor.putInt("flagdrug", 1);
										editor.commit();
									}
								};
								 runnablex_timer = new Runnable() {
									@Override
									public void run() {
										int x = pref.getInt("chastwidj", 2);
										timer.schedule(tasttime, x*100);
									}};
									thredtime = new Thread(runnablex_timer);
								//remove face bubble on long press  
								if(System.currentTimeMillis()-touchStartTime>ViewConfiguration.getLongPressTimeout() && initialTouchX== event.getX()){  
									stopSelf();  
									return false;  
								}  
								switch(event.getAction()){  
									case MotionEvent.ACTION_DOWN:  
										touchStartTime = System.currentTimeMillis();  
										initialX = myParams.x;  
										initialY = myParams.y;  
										initialTouchX = event.getRawX();  
										initialTouchY = event.getRawY();  
										editor.putInt("flagdrug", 0);
										editor.commit();
										thredtime.start();
										break;  
									case MotionEvent.ACTION_UP:  
										editor.putInt("xzap", myParams.x);
										editor.putInt("yzap", myParams.y);
										editor.commit();
										if (pref.getInt("flagdrug", 1) == 0){
											editor.putInt("flagdrug", 0);
											editor.commit();
											String packageName = "com.deff83.indexAPI";
											String className = "com.deff83.indexAPI.MainActivity";
											Intent intent = new Intent("android.intent.action.MAIN");
											intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
											intent.setComponent(new ComponentName(packageName, className));
											startActivity(intent);
										}
										break;  
									case MotionEvent.ACTION_MOVE:  
										myParams.x = initialX + (int) (event.getRawX() - initialTouchX);  
										myParams.y = initialY + (int) (event.getRawY() - initialTouchY);  
										
										windowManager.updateViewLayout(v, myParams);  
										break;  
								}  
								return false;  
							}  
						});  
				} catch (Exception e){  
					e.printStackTrace();  
				}  
				tv1.setText(pref.getString("widjetmin", "0.00"));
						tv2.setText(pref.getString("widjetmax", "999.9"));
					
				
			}
			
			//запуск службы
			@Override
			public int onStartCommand(Intent intent, int flags, int startId) {
				try{
				int widjetintent = intent.getIntExtra("widjetintent", 0);
				switch (widjetintent){
					case 1:
						try{
					OrdersPriceList ordersPriceList = Parametr.getParametr().getOrdersPriceList();
								List<OrderPrice> listbuy = ordersPriceList.getListbuy();//левая колонка
								List<OrderPrice> listsell = ordersPriceList.getListsell();//левая колонка
								OrderPrice firstbuy = listbuy.get(0);
								OrderPrice firstsell = listsell.get(0);
								Double pricebuy = firstbuy.getPrice();
								Double pricesell = firstsell.getPrice();
								
								tv1.setText(" " + pricebuy+" ");
								tv2.setText(" " + pricesell+" ");
						}catch(Exception e){}
						break;
					case 3:
					try{
					List<CoinTool> listCoinTool = Parametr.getParametr().getListCoinTool();
								CoinTool coinTool = null;
								for (int i = 0; i < listCoinTool.size(); i++) {
									CoinTool coinTooltek = listCoinTool.get(i);
									int id = coinTooltek.getId();
									if (id==Parametr.getParametr().getzCoinMain()) coinTool = coinTooltek;
								}
								if (coinTool!=null){
									String name = coinTool.getName();
									String price = coinTool.getPrice();
									textpricetek.setText(price);
								}
					}catch(Exception e){}
					break;
					case 4:
					try{
					List<MyCoin> listmyCoins = Parametr.getParametr().getListMyCoins();
								//коины монетки
								lincoin.removeAllViews();
								for (int i = 0; i < listmyCoins.size(); i++){
									MyCoin myCoin = listmyCoins.get(i);
									int port_count = myCoin.getNotes();
									String port_name = myCoin.getName();
									Integer idcoin = myCoin.getId();
									if (port_count != 0){
									
										RelativeLayout rl = new RelativeLayout(this);
										rl.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
										TextView tvy = new TextView(this);
										tvy.setGravity(Gravity.RIGHT);
										tvy.setText(port_count + " " + port_name + " ");
										tvy.setBackgroundColor(getResources().getColor(R.color.colorwidback));
										int width = 20, high = 20;

										if (port_count > 10){port_count = 10;}
										switch (idcoin){
											case 66:
												for (int k=0; k<port_count; k++){
													ImageView imagecoin;
													imagecoin = new ImageView(this);
													RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
													lp.leftMargin = 10*k;
													imagecoin.setImageResource(R.drawable.bch);
													rl.addView(imagecoin, lp);
												}
												break;
											case 60:
												for (int k=0; k<port_count; k++){
													ImageView imagecoin;
													imagecoin = new ImageView(this);
													RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
													lp.leftMargin = 10*k;
													imagecoin.setImageResource(R.drawable.btc);
													rl.addView(imagecoin, lp);
												}
												break;
											case 67:
												for (int k=0; k<port_count; k++){
													ImageView imagecoin;
													imagecoin = new ImageView(this);
													RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
													lp.leftMargin = 10*k;
													imagecoin.setImageResource(R.drawable.btg);
													rl.addView(imagecoin, lp);
												}
												
												
												
												break;
											case 71:
												for (int k=0; k<port_count; k++){
													ImageView imagecoin;
													imagecoin = new ImageView(this);
													RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
													lp.leftMargin = 10*k;
													imagecoin.setImageResource(R.drawable.dash);
													rl.addView(imagecoin, lp);
												}
												break;
											case 64:
												for (int k=0; k<port_count; k++){
													ImageView imagecoin;
													imagecoin = new ImageView(this);
													RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
													lp.leftMargin = 10*k;
													imagecoin.setImageResource(R.drawable.eth);
													rl.addView(imagecoin, lp);
												}
												break;
											case 69:
												for (int k=0; k<port_count; k++){
													ImageView imagecoin;
													imagecoin = new ImageView(this);
													RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
													lp.leftMargin = 10*k;
													imagecoin.setImageResource(R.drawable.ltc);
													rl.addView(imagecoin, lp);
												}
												break;
											case 68:
												for (int k=0; k<port_count; k++){
													ImageView imagecoin;
													imagecoin = new ImageView(this);
													RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
													lp.leftMargin = 10*k;
													imagecoin.setImageResource(R.drawable.xmr);
													rl.addView(imagecoin, lp);
												}
												break;
											case 70:
												for (int k=0; k<port_count; k++){
													ImageView imagecoin;
													imagecoin = new ImageView(this);
													RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
													lp.leftMargin = 10*k;
													imagecoin.setImageResource(R.drawable.xrp);
													rl.addView(imagecoin, lp);
												}

												break;
										}
										RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
										lpt.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
										tvy.setLayoutParams(lpt);
										rl.addView(tvy, RelativeLayout.LayoutParams.MATCH_PARENT);
										lincoin.addView(rl);
									}
								
								}
					}catch(Exception e){};
					break;
				}
				}catch(Exception e){}
				return Service.START_STICKY;
			}
			//остановка службы
			@Override
			public void onDestroy() {
				
				windowManager.removeView(rlay);
				Toast.makeText(this, "Виджет остановлен",
							   Toast.LENGTH_SHORT).show();
				super.onDestroy();

			}
			

	}
