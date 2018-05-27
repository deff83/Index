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

	public class MesWidjet extends Service
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
		private Float floarsize;
		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}
		//создание службы
		@Override
		public void onCreate() {
			super.onCreate();
			//pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
			pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication());
			editor = pref.edit();


			Toast.makeText(this, "Служба создана",
						   Toast.LENGTH_SHORT).show();
			rlay = new RelativeLayout(this);
			rlay.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

			floatingFaceBubble = new ImageView(this);  

			textmin = new TextView(this);
			textmin.setText("hgvhgvh");
			//a face floating bubble as imageView  
			floatingFaceBubble.setImageResource(R.drawable.bch);  

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
			//rlay.addView(floatingFaceBubble);
			rlay.addView(tr);
			windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);  
			//here is all the science of params  
			final LayoutParams myParams = new WindowManager.LayoutParams(  
				LayoutParams.WRAP_CONTENT,  
				LayoutParams.WRAP_CONTENT,  
				LayoutParams.TYPE_PHONE,  
				LayoutParams.FLAG_NOT_FOCUSABLE,  
				PixelFormat.TRANSLUCENT);  
			myParams.gravity = Gravity.TOP | Gravity.LEFT;  
			int xzap = pref.getInt("xzap", 0);
			int yzap = pref.getInt("yzap", 100);
			myParams.x=xzap;  
			myParams.y=yzap;  
			// add a floatingfacebubble icon in window  
			windowManager.addView(rlay, myParams); 

			try{  
				
				//for moving the picture on touch and slide  
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
								windowManager.removeView(floatingFaceBubble);  
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
			BroadcastReceiver br = new BroadcastReceiver() {
				// действия при получении сообщений
				public void onReceive(Context context, Intent intent) {
				/*
					int col_mes = pref.getInt("colmes", 0);
					
					floarsize = Float.parseFloat(pref.getString("sizeSh", "20"));

					linerLayout2.removeAllViews();

					try{
						for (int i=col_mes-100; i<col_mes; i++){
							LayoutInflater inflate = LayoutInflater.from(this);
							linmes = (LinearLayout) inflate.inflate(R.layout.line_mess, null);
							TextView namemes;
							TextView wmidmes;
							namemes = (TextView) linmes.findViewById(R.id.namemes);
							wmidmes = (TextView) linmes.findViewById(R.id.wmidmes);
							String idmesstr = pref.getString("idmes" + i, "");
							String nickname = pref.getString("nick" + i, "");
							namemes.setText(nickname);
							wmidmes.setLinkTextColor(Color.RED);
							wmidmes.setLinksClickable(true);
							wmidmes.setMovementMethod(LinkMovementMethod.getInstance());
							String mess = pref.getString("mess" + i, "");
							try{
								//DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
								//DocumentBuilder builder = factory.newDocumentBuilder();

								Document doc = Jsoup.parse(mess);
								//builder.parse(new InputSource(new StringReader(mess)));
								//NodeList as = doc.getElementsByTagName("a");
								Elements as = doc.select("a.user-link");
								for (Element a: as){
									String texta = a.attr("nickname");
									a.text(texta);
									//a.attr("style", "color: #ffffff");
									a.attr("href", "");



								}
								wmidmes.setText(Html.fromHtml(doc.toString()))
									;

								//E  a = doc.getElementsByTagName("a");
							}catch (Exception e){
								wmidmes.setText(e.toString());
							}
							//wmidmes.setText(Html.fromHtml(mess));

							linerLayout2.addView(linmes);
						}
					} catch (Exception e){}





					editor.commit();

					lincoin.removeAllViews();
					for (int i = 0; i < col_z; i++){
						int port_count =pref.getInt("port_notes" + i , 0);
						if (port_count != 0){

							RelativeLayout rl = new RelativeLayout(context);
							rl.setLayoutParams((new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)));
							TextView tvy = new TextView(context);

							String port_name = pref.getString("port_name" + i, "");
							Integer idcoin = pref.getInt("port_id" + i, 0);


							tvy.setGravity(Gravity.RIGHT);

							//tvy.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
							tvy.setText(port_count + " " + port_name + " ");
							int width = 20, high = 20;

							if (port_count > 10){port_count = 10;}
							switch (idcoin){
								case 66:
									for (int k=0; k<port_count; k++){
										ImageView imagecoin;
										imagecoin = new ImageView(context);
										RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
										lp.leftMargin = 10*k;
										imagecoin.setImageResource(R.drawable.bch);
										rl.addView(imagecoin, lp);
									}
									break;
								case 60:
									for (int k=0; k<port_count; k++){
										ImageView imagecoin;
										imagecoin = new ImageView(context);
										RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
										lp.leftMargin = 10*k;
										imagecoin.setImageResource(R.drawable.btc);
										rl.addView(imagecoin, lp);
									}
									break;
								case 67:
									for (int k=0; k<port_count; k++){
										ImageView imagecoin;
										imagecoin = new ImageView(context);
										RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
										lp.leftMargin = 10*k;
										imagecoin.setImageResource(R.drawable.btg);
										rl.addView(imagecoin, lp);
									}



									break;
								case 71:
									for (int k=0; k<port_count; k++){
										ImageView imagecoin;
										imagecoin = new ImageView(context);
										RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
										lp.leftMargin = 10*k;
										imagecoin.setImageResource(R.drawable.dash);
										rl.addView(imagecoin, lp);
									}
									break;
								case 64:
									for (int k=0; k<port_count; k++){
										ImageView imagecoin;
										imagecoin = new ImageView(context);
										RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
										lp.leftMargin = 10*k;
										imagecoin.setImageResource(R.drawable.eth);
										rl.addView(imagecoin, lp);
									}
									break;
								case 69:
									for (int k=0; k<port_count; k++){
										ImageView imagecoin;
										imagecoin = new ImageView(context);
										RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
										lp.leftMargin = 10*k;
										imagecoin.setImageResource(R.drawable.ltc);
										rl.addView(imagecoin, lp);
									}
									break;
								case 68:
									for (int k=0; k<port_count; k++){
										ImageView imagecoin;
										imagecoin = new ImageView(context);
										RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
										lp.leftMargin = 10*k;
										imagecoin.setImageResource(R.drawable.xmr);
										rl.addView(imagecoin, lp);
									}
									break;
								case 70:
									for (int k=0; k<port_count; k++){
										ImageView imagecoin;
										imagecoin = new ImageView(context);
										RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,high);
										lp.leftMargin = 10*k;
										imagecoin.setImageResource(R.drawable.xrp);
										rl.addView(imagecoin, lp);
									}

									break;
							}
							RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
							lpt.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
							tvy.setLayoutParams(lpt);
							rl.addView(tvy, RelativeLayout.LayoutParams.MATCH_PARENT);
							lincoin.addView(rl);
						}

					}
*/

				}
				//текущая цена

			};
			// создаем фильтр для BroadcastReceiver
			IntentFilter intFilt = new IntentFilter("CAT");
			// регистрируем (включаем) BroadcastReceiver
			registerReceiver(br, intFilt);
		}

		//запуск службы
		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			tv1.setText(pref.getString("widjetmin", "0.00"));
			tv2.setText(pref.getString("widjetmax", "999.9"));
			//Toast.makeText(this, "Служба запущена", Toast.LENGTH_SHORT).show();

			return Service.START_STICKY;
		}
		//остановка службы
		@Override
		public void onDestroy() {


			Toast.makeText(this, "Служба остановлена",
						   Toast.LENGTH_SHORT).show();
			super.onDestroy();

		}


	}
