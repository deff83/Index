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
	private Integer sessiasps;
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
		sessiasps = pref.getInt("sessiasps", 0);
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
		editcode.setOnTouchListener(on_touch_listener3);
		//amountedit.setOnTouchListener(on_touch_listener3);

		OnClickListener listenerbutton = new OnClickListener(){
			@Override
			public void onClick(View v){
				switch(v.getId()){
					case R.id.sps2dialogButton2: //кнопка отправить
						final Thread postthread;

						Runnable runnablex = new Runnable() {
							@Override
							public void run() {
								String codeenter = editcode.getText().toString();
								if(codeenter.equals("")){codeenter="0";}
								post2_sps(codeenter);//функция первого post запроса 
								//передается код, в случае оплаты через счет ВМ - 0, 
							};
						};

						postthread= new Thread(runnablex);
						postthread.start();
						break;
					case R.id.sps2dialogButtoncancel:
						final Thread postthreadcancel;

						Runnable runnablexcancel = new Runnable() {
							@Override
							public void run() {
								post2_sps("-1");//функция первого post запроса
							};
						};

						postthreadcancel= new Thread(runnablexcancel);
						postthreadcancel.start();
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
	private String descans; //сообщение ошибки
	public void post2_sps(String code){
		 client = new OkHttpClient();
		//Integer emulated_flag = 0; //флаг эмуляции
		String pursedonat = "Z197552073362";
		String wmiddonat = "280113070531";
		//String desc = "IndexAPI";
		String lang = "ru-RU";
		//Integer nomer = 79001;
		String secretcode = "92387804";
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"wmid\":\""+wmiddonat+"\",\"lmi_payee_purse\":\""+pursedonat+"\",\"lmi_wminvoiceid\": "+sessiasps+",\"lmi_clientnumber_code\": "+code+",\"secret_key\":\""+secretcode+"\",\"sign\":\"\",\"sha256\":\"\",\"md5\":\"\",\"lang\":\""+lang+"\"}");
		Request request = new Request.Builder()
			.url("https://merchant.webmoney.ru/conf/xml/XMLTransConfirm.asp")
			.post(body)
			//.addHeader("cookie", "ASPSESSIONIDCESSSDCR=AAAIMHIDGEJEOFGNDCGKNILI")
			.addHeader("content-type", "application/json")
			.build();
		try{
		Response response = client.newCall(request).execute();
			String answir = response.body().string(); 
			editor.putString("resptest", answir);
			try{
			JSONObject jsonObject;
			jsonObject = new JSONObject(answir);
			Integer retval = jsonObject.getInt("retval");

			descans = jsonObject.getString("userdesc");
				
			if (retval == 0){
				JSONObject operation = jsonObject.getJSONObject("operation");
				Integer wmtransid = operation.getInt("wmtransid");
				Double amount = operation.getDouble("amount");
				if (wmtransid > 0){//если транзакция уже имеет идентификатор
					getsuccefuldialog(amount);
				}
			}else{getdialog(retval);}
			}catch(JSONException e){
				editor.putString("resptest", e.toString());
			}
			
		}catch(Exception e){
			editor.putString("resptest", e.toString());
			
		}
		editor.commit();
		finish();
	}
	private void getdialog(Integer retval){
		String textmesdialog = descans;
		switch(retval){//обработка ошибок по retval не показанных в desc
			case 557:
				textmesdialog = descans + "\nСессия "+sessiasps+" отменена и недействительна!";
				break;
		}
		editor.putInt("flagerrorsps", 1);
		editor.putString("textmesdialog", textmesdialog);
		editor.commit();
	}
	private void getsuccefuldialog(Double amount){
		Intent i = new Intent(Sps_dialog2.this, Succefull_dialog.class);
		i.putExtra("amount", amount);
		startActivity(i);
	}
	

}
