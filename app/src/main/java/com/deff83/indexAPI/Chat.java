package com.deff83.indexAPI;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.LinearLayout.*;
import java.util.*;
import android.text.*;

import javax.xml.parsers.*;
import org.xml.sax.*;
import java.io.*;
import org.jsoup.select.*;
import org.jsoup.nodes.*;
import org.jsoup.*;
import android.graphics.*;
import android.text.method.*;
import android.preference.*;
import java.net.*;
import android.net.*;
import com.squareup.picasso.*;
import java.text.*;

public class Chat extends Activity implements IChatModel
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
	OnTouchListener rellaymes;
	private Integer lastcolmes;
	
	ScrollView scrollchat;
	//шрифт
	Float floarsize;
	//лайаут
	RelativeLayout relmes;
	LinearLayout linerLayout2;
	LinearLayout linmes;
	//слушатель 
	OnClickListener gettablz;
	OnClickListener imglistener;
	//текст загрузка
	TextView load;
	private int col_mes = 0;
	private String rab;
	private List<ChatMessage> listChatMessage = new ArrayList<>();
	private List<ChatMessage> newlistChatMessage = new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		
		editor = pref.edit();
		rab = "";

		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		//лайаут
		linerLayout2 = (LinearLayout) findViewById(R.id.layoutlinmes);
		load = (TextView) findViewById(R.id.downloadmes);
		
		//список в выдвижной панели
		mCatTitles = getResources().getStringArray(R.array.chat);
		mDrawerListView = (ListView) findViewById(R.id.left_drawermes);
		// подключим адаптер для списка
		mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
															R.layout.draw_list_item, mCatTitles));
		mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());
		scrollchat =(ScrollView) findViewById(R.id.scrollvertmes);
		
		//слушатель картпнок
		imglistener = new OnClickListener() {
            @Override
            public void onClick(View v) {
				int i = v.getId()-88008;
				ChatMessage chatMessage = listChatMessage.get(i);
				
				String wmid = chatMessage.getWmid();
                Intent link=new Intent(Intent.ACTION_VIEW, Uri.parse("https://events.webmoney.ru/user/"+wmid));
                startActivity(link);    
            }
        };
		try{
			tabl2();
			int x = scrollchat.getScrollY();
			int heiscrol = scrollchat.getHeight();
			int sodtextscroll = scrollchat.getChildAt(0).getHeight();
			int razn = sodtextscroll - heiscrol;
			//Toast.makeText(Chat.this, x+ ":" +razn, Toast.LENGTH_SHORT).show();
			if (x>razn -80){
				scrollchat.post(new Runnable() {
						@Override
						public void run() {
							scrollchat.fullScroll(ScrollView.FOCUS_DOWN);
						}
					});
			}
		}
		catch(Exception e){}
		//relmes = (RelativeLayout) findViewById(R.id.layoutrelpostsend);
		Button butstopmes = (Button) findViewById(R.id.buttonstopmesschat);
		EditText editstop = (EditText) findViewById(R.id.editstopmesschat);
		
		
		
		rellaymes = new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event){
						Intent i= new Intent(Chat.this, Oproecte.class);
						startActivity(i);
				return true;
			}
		};
		butstopmes.setOnTouchListener(rellaymes);
		editstop.setOnTouchListener(rellaymes);
		ParametrChat.getParametrChat().addSinglListener(this);

		
	}
	@Override
	public void doUpdateChat(int arg){
		switch (arg){
			case 0:
				runOnUiThread(new Runnable(){
					@Override
					public void run(){
						load.setText("Чат выключен в настройках!");
					}
				});
				break;
			case 1:
				runOnUiThread(new Runnable(){
					@Override
					public void run(){
					try{
								tabl2();
								int x = scrollchat.getScrollY();
								int heiscrol = scrollchat.getHeight();
								int sodtextscroll = scrollchat.getChildAt(0).getHeight();
								int razn = sodtextscroll - heiscrol;
								//Toast.makeText(Chat.this, x+ ":" +razn, Toast.LENGTH_SHORT).show();
								if (x>razn -80){
								scrollchat.post(new Runnable() {
										@Override
										public void run() {
											scrollchat.fullScroll(ScrollView.FOCUS_DOWN);
										}
									});
									}
								
							}
					catch(Exception e){};
					}
				});
			break;
		}
	}
	
	public void tabl2(){
		listChatMessage = ParametrChat.getParametrChat().getListChatMessage();
		newlistChatMessage = listChatMessage.subList(col_mes, listChatMessage.size());
		
		
		
		if (rab.equals("► ► ► ")){
			rab = "";
		}
		else{
			rab = rab + "► ";
		}
		load.setText(rab);
		
		floarsize = Float.parseFloat(pref.getString("sizeSh", "20"));

		

		try{
			for (int i=0; i<newlistChatMessage.size(); i++){
				ChatMessage chatMessage = newlistChatMessage.get(i);
				LayoutInflater inflate = LayoutInflater.from(this);
				linmes = (LinearLayout) inflate.inflate(R.layout.line_mess, null);
				
				TextView namemes;
				TextView wmidmes;
				TextView datecreatedmes;
				//TextView attestat;
				ImageView imgmes, attestimg;
				namemes = (TextView) linmes.findViewById(R.id.namemes);
				wmidmes = (TextView) linmes.findViewById(R.id.wmidmes);
				datecreatedmes = (TextView) linmes.findViewById(R.id.datecreatedmes);
				//attestat = (TextView) linmes.findViewById(R.id.attestat);
				imgmes = (ImageView) linmes.findViewById(R.id.iconmes);
				attestimg = (ImageView) linmes.findViewById(R.id.attestimg);
				imgmes.setId(88008+i+col_mes);
				imgmes.setOnClickListener(imglistener);
				
				String idmesstr = chatMessage.getId();
				String nickname = chatMessage.getShort_nickname();
				String wmidmesstr = chatMessage.getWmid();
				String mess = chatMessage.getMess();
				String urlimg = chatMessage.getUrlimg();
				String datecreated = chatMessage.getMyFormatDate();
				String attestats = chatMessage.getAttestats();
				//картинка аттестата

				attestimg.setImageBitmap(getimgattestat(attestats));
				//ник в сообщении
				namemes.setText(nickname);
				//дата
				datecreatedmes.setText(datecreated);
				//attestat.setText("  " + attestats);
				wmidmes.setLinkTextColor(Color.RED);
				wmidmes.setLinksClickable(true);
				wmidmes.setMovementMethod(LinkMovementMethod.getInstance());
				try{
					Document doc = Jsoup.parse(mess);
					Elements as = doc.select("a.user-link");
					for (Element a: as){
						String texta = a.attr("nickname");
						a.text(texta);
						//a.attr("style", "color: #ffffff");
						a.attr("href", "");
					}
					wmidmes.setText(Html.fromHtml(doc.toString()));
					//аватарка
					Picasso.with(this)
							.load(urlimg)
							.transform(new CircularTransform())
							.into(imgmes);

				}catch (Exception e){
					wmidmes.setText(e.toString());
				}
				linerLayout2.addView(linmes);
			}
		} catch (Exception e){}
		col_mes = listChatMessage.size();
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		switch(id){
			case R.id.menuPurchasesListSortOrderCategory:
				Intent intent = new Intent(Chat.this, Oproecte.class);
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
	
	private Bitmap getimgattestat(String s){
		
		Bitmap bmp=BitmapFactory.decodeResource(getResources(), R.drawable.attestatimg);
		int widthcrat = bmp.getWidth()/14+1;
		int x = widthcrat*10;
		switch(s){
			case "продавец":
				x=widthcrat*4;
				break;
			case "персональный":
				x=widthcrat*3;
				break;
			case "начальный":
				x=widthcrat*2;
				break;
			case "псевдоним":
				x=widthcrat*0;
				break;
			case "формальный":
				x=widthcrat*1;
				break;

		}
		Bitmap resizedbitmap=Bitmap.createBitmap(bmp,x,0, widthcrat, bmp.getHeight());
		return resizedbitmap;
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
		ParametrChat.getParametrChat().removeListener(this);
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
			Intent intent = new Intent(Chat.this, MainActivity.class);
			switch (position){
				case 0:
					
					intent = new Intent(Chat.this, MainActivity.class);
					break;
				case 1:
					intent = new Intent(Chat.this, HistoryActivity.class);
					break;
				case 2:
					intent = new Intent(Chat.this, Userfunction.class);
					break;
				case 3:
					intent = new Intent(Chat.this, Myzayvk_act.class);
					break;
				case 4:
					intent = new Intent(Chat.this, Information.class);
					break;
				case 5:
					
					intent = new Intent(Chat.this, Setting.class);
					break;

				case 6:
					intent = new Intent(Chat.this, LoginActivity.class);
					editor.putInt("verification", 0);
				
					editor.commit();
					Intent i = new Intent(Chat.this, ServicePOST.class);
					stopService(i);
					break;
			}
			
			startActivity(intent);
			
		
		}
	}
}
