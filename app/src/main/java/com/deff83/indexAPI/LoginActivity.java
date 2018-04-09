package com.deff83.indexAPI;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.view.inputmethod.*;


public class LoginActivity extends Activity
{
	SharedPreferences pref;
	Context context = null;
	SharedPreferences.Editor editor = null;
	EditText login;
	EditText pass;
	EditText wmid;
	RelativeLayout rel_log;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		
		pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
		editor = pref.edit();
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activ);
		login = (EditText) findViewById(R.id.edit_login);
		pass = (EditText) findViewById(R.id.edit_pass);
		wmid = (EditText) findViewById(R.id.edit_wmid);
		//запись при старте в edit логин
		try {
			if(pref.contains("login")){
				login.setText(pref.getString("login", ""));
			}
		} catch (Exception e){}
		//запись при старте в edit пароль
		try {
			if(pref.contains("password")){
				pass.setText(pref.getString("password", ""));
			} 
		} catch (Exception e){}
		//запись в edit wmid
		try {
			if(pref.contains("wmid")){
				wmid.setText(pref.getString("wmid", ""));
			} 
		} catch (Exception e){}
		//слушатель,  layot
		rel_log = (RelativeLayout) findViewById(R.id.rel_login);
		rel_log.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
						login.setFocusable(false);
						pass.setFocusable(false);
						wmid.setFocusable(false);
					}
					return v.performClick();
				}
			});
		//слушатель edit, установка акиивности вызов клавиатуры
		OnTouchListener on_touch_listener_log = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setFocusableInTouchMode(true);
				}
				return v.performClick();
			}
		};
		//вешаем на каждый edit один слушатель
		login.setOnTouchListener(on_touch_listener_log);
		pass.setOnTouchListener(on_touch_listener_log);
		wmid.setOnTouchListener(on_touch_listener_log);
		//кнопка вход
		Button but_input = (Button) findViewById(R.id.input);
		but_input.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					editor.putString("login", login.getText().toString());
					editor.putString("password", pass.getText().toString());
					editor.putString("wmid", wmid.getText().toString());
					editor.putInt("verification", 1);
					editor.commit();
					Intent i = new Intent(getApplication(), PlayService.class);
					startService(i);
					Intent iyu =  new Intent(getApplication(), MainActivity.class);
					startActivity(iyu);
				}
			});
	}
	
	
}
