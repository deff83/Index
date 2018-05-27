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
import java.io.*;
import android.graphics.*;
import android.graphics.drawable.*;

public class Sps_dialog extends Activity
{
	SharedPreferences pref;
	Context context = null;
	SharedPreferences.Editor editor = null;
	private LinearLayout interceptor;
	private static Double minSps = 0.2;
	private Drawable originalBackground;
	EditText wmidedit, amountedit, editposhel; 
	Button button1;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		//pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication());
		editor = pref.edit();

		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		//устанока титла окна

		setContentView(R.layout.sps1_dialog);
		wmidedit = (EditText) findViewById(R.id.sps1dialogEditWmid);
		amountedit = (EditText) findViewById(R.id.sps1dialogEditAmount);
		editposhel = (EditText) findViewById(R.id.sps1dialogEditPoshel);
		button1 = (Button) findViewById(R.id.sps1dialogButtonotpr);
		
		String wmid = pref.getString("wmid", "");
		wmidedit.setText(wmid);
		

		originalBackground = amountedit.getBackground();

		//слушатель edit, установка акиивности вызов клавиатуры
		OnTouchListener on_touch_listener3 = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setFocusableInTouchMode(true);
					amountedit.setBackgroundDrawable(originalBackground);
				}
				return v.performClick();
			}
		};
		//вешаем на каждый edit один слушатель
		wmidedit.setOnTouchListener(on_touch_listener3);
		amountedit.setOnTouchListener(on_touch_listener3);

		OnClickListener listenerbutton = new OnClickListener(){
			@Override
			public void onClick(View v){
				switch(v.getId()){
					case R.id.sps1dialogButtonotpr: //кнопка отправить
						Double amountx = 0.0;
						try{
							amountx = Double.parseDouble(amountedit.getText().toString()); //сумма
						}catch(Exception e){}
							if(amountx < minSps){//проверка на минимальную цену
							amountedit.setBackgroundColor(R.color.colorRed);
						break;
						
						}
						final Thread postthread;

						Runnable runnablex = new Runnable() {
							@Override
							public void run() {
								post1_sps();//функция первого post запроса
							};
						};

						postthread= new Thread(runnablex);
						postthread.start();
						break;
				}
			}
		};
		button1.setOnClickListener(listenerbutton);
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
	private String descans;
private OkHttpClient client;
	public void post1_sps(){
		//установка нулевых значений
		descans = "null";
		editor.putInt("sessiasps", 0);
		editor.commit();
		//  "отправка пост запроса"
		//Toast.makeText(getBaseContext(), "подождите", Toast.LENGTH_SHORT).show();
		client = new OkHttpClient();
		Integer emulated_flag = 0; //флаг эмуляции
		String pursedonat = "Z197552073362";
		String wmiddonat = "280113070531";
		String poshel0 = editposhel.getText().toString();
		String poshel = poshel0.replaceAll("\\n", " ");
		String desc = "IndexAPI "+poshel;
		String base64 = "SW5kZXhBUEk="; //в случае ошибки байтового разбрра
		try{

			byte[] zl = desc.getBytes("UTF-8");

		 base64 = android.util.Base64.encodeToString(zl, android.util.Base64.NO_WRAP);
		}catch (UnsupportedEncodingException e){}
		Integer nomer = 79001;
		String secretcode = "92387804"; //секрет код

		Double amount;
		try{
		amount = Double.parseDouble(amountedit.getText().toString()); //сумма
		}catch(Exception e){
			amount = 0.0;
		}
		
		String clientnumber = "";//номер клиента
		try{
			clientnumber = wmidedit.getText().toString();
		}catch(Exception e){}
		
		Integer clienttype = 1; //тип набранного номера клиента телефон0, вмид1, емайл2
		Integer smstype = 1; //тип выбранного подтверждения 1смс, 2ussd, 3авто, 4вм свет

		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");

		RequestBody body = RequestBody.create(mediaType, "{\"wmid\": \""+wmiddonat+"\",\"lmi_payee_purse\": \""+pursedonat+"\",\"lmi_payment_no\": "+nomer+",\"lmi_payment_amount\": "+amount+",\"lmi_payment_desc_base64\": \""+base64+"\",\"lmi_clientnumber\": \""+clientnumber+"\",\"lmi_clientnumber_type\": "+clienttype+",\"lmi_sms_type\": "+smstype+",\"secret_key\": \""+secretcode+"\",\"emulated_flag\":"+emulated_flag+"}");

		Request request = new Request.Builder()
			.url("http://merchant.webmoney.ru/conf/xml/XMLTransRequest.asp")
			.post(body)
			.addHeader("content-type", "application/json")
			.build();
		try{
			Response response = client.newCall(request).execute();
			//ответ тела post запроса
			String answir = response.body().string(); 
			try{
				JSONObject jsonObject;
				jsonObject = new JSONObject(answir);
				Integer retval = jsonObject.getInt("retval");
				
				descans = jsonObject.getString("userdesc");
				
				if (retval == 0){
					
				JSONObject opiration = jsonObject.getJSONObject("operation");
				Integer wmtransid = opiration.getInt("wmtransid");
				Integer wminvoiceid = opiration.getInt("wminvoiceid");
				Integer realsmstype = opiration.getInt("realsmstype");
				editor.putInt("sessiasps", wminvoiceid);
				editor.putInt("realsmstype", realsmstype);
				editor.commit();
					runOnUiThread(new Runnable() {
							public void run() {
								//Toast.makeText(getApplicationContext(), "текст внутри потока", Toast.LENGTH_SHORT).show();
								Intent i = new Intent(getApplicationContext(), Sps_dialog2.class);
								startActivity(i);
							}
						});
				
				
				}else{
				getdialog(retval);
			}
			
			}catch(JSONException e){}
			//editor.putString("infor",answir);
			editor.putString("resptest", poshel);
		}catch(Exception e){//editor.putString("infor",e.toString());
		}
		//editor.putInt("flagtextinform", 1);
		editor.commit();
		


		finish();
	}
	private String textmesdialog;
	private void getdialog(Integer retval){
		
		textmesdialog = descans;
		switch(retval){
			case -4:
				textmesdialog = "ошибка ввода суммы";
				break;
		}
		editor.putInt("flagerrorsps", 1);
		editor.putString("textmesdialog", textmesdialog);
		editor.commit();
	}

}
