package com.deff83.indexAPI;

import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.view.inputmethod.*;
import android.view.ViewDebug.*;
import java.util.*;
import android.preference.*;
import okhttp3.*;
import org.json.*;

public class Sps_dialog2 extends Activity
{
	SharedPreferences pref;
	Context context = null;
	SharedPreferences.Editor editor = null;
	private LinearLayout interceptor;
	Button button2, buttoncancel;
	EditText editcode;
	TextView textsessia;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		//pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication());
		editor = pref.edit();

		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		//устанока титла окна

		setContentView(R.layout.sps2_dialog);
		button2 = (Button) findViewById(R.id.sps2dialogButton2);
		buttoncancel = (Button) findViewById(R.id.sps2dialogButtoncancel);
		editcode = (EditText) findViewById(R.id.sps2dialogEditCode);
		textsessia = (TextView) findViewById(R.id.sps2dialogSessia);
		Integer sessiasps = pref.getInt("sessiasps", 0);
		textsessia.setText(sessiasps.toString());
		//слушатель edit, установка акиивности вызов клавиатуры
		OnTouchListener on_touch_listener3 = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setFocusableInTouchMode(true);
				}
				return v.performClick();
			}
		};
		//вешаем на каждый edit один слушатель
		//wmidedit.setOnTouchListener(on_touch_listener3);
		//amountedit.setOnTouchListener(on_touch_listener3);

		OnClickListener listenerbutton = new OnClickListener(){
			@Override
			public void onClick(View v){
				switch(v.getId()){
					case R.id.sps1dialogButtonotpr: //кнопка отправить
						final Thread postthread;

						Runnable runnablex = new Runnable() {
							@Override
							public void run() {
								post2_sps();//функция первого post запроса
							};
						};

						postthread= new Thread(runnablex);
						postthread.start();
						break;
					case R.id.sps2dialogButtoncancel:
						finish();
						break;
				}
			}
		};
		button2.setOnClickListener(listenerbutton);
		buttoncancel.setOnClickListener(listenerbutton);
	}

	@Override
	public void finish()
	{

		// TODO: Implement this method
		super.finish();
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
	private OkHttpClient client;
	public void post2_sps(){
		//  "отправка пост запроса"
		//Toast.makeText(getBaseContext(), "подождите", Toast.LENGTH_SHORT).show();
		client = new OkHttpClient();
		
		OkHttpClient client = new OkHttpClient();

		//MediaType mediaType = MediaType.parse("application/json");

		//RequestBody body = RequestBody.create(mediaType, "{\"wmid\": \""+wmiddonat+"\",\"lmi_payee_purse\": \""+pursedonat+"\",\"lmi_payment_no\": "+nomer+",\"lmi_payment_amount\": "+amount+",\"lmi_payment_desc\": \""+desc+"\",\"lmi_clientnumber\": \""+clientnumber+"\",\"lmi_clientnumber_type\": "+clienttype+",\"lmi_sms_type\": "+smstype+",\"secret_key\": \""+secretcode+"\",\"emulated_flag\":"+emulated_flag+"}");

		/*Request request = new Request.Builder()
			.url("http://merchant.webmoney.ru/conf/xml/XMLTransRequest.asp")
			.post(body)
			.addHeader("content-type", "application/json")
			.build();
		try{
			Response response = client.newCall(request).execute();
			//ответ тела post запроса
			String answir = response.body().string(); */
			



		finish();
	}

}
