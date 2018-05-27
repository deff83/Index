	package com.deff83.indexAPI;
import android.content.*;
import android.widget.*;
import android.app.*;
import android.preference.*;
import android.view.View.*;
import android.view.*;
import android.os.*;

	
	

public class Succefull_dialog extends Activity
	{
		SharedPreferences pref;
		Context context = null;
		SharedPreferences.Editor editor = null;
		//private LinearLayout interceptor;
		private Button button1;
		private TextView textsps;
		private Double amount;
		private Intent intent;
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			//pref = getSharedPreferences("CAT", Context.MODE_PRIVATE);
			pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication());
			editor = pref.edit();

			// TODO: Implement this method
			super.onCreate(savedInstanceState);
			//устанока титла окна

			setContentView(R.layout.suc_dialog);
			button1 = (Button) findViewById(R.id.sucdialogButtonOk);
			textsps = (TextView) findViewById(R.id.sucdialogSpasibo);
			intent = getIntent();
			amount = intent.getDoubleExtra("amount", 0.0);
			textsps.setText("Спасибо за помощь!\nВсего вам хорошего.\nНадеемся это не последний ваш платеж нам.\nИграйте на бирже и зарабатывайте\nУспехов вам, и побольше профита) \nВаша сумма "+amount+" успешно отправлена");
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
						case R.id.sucdialogButtonOk: //кнопка закрыть
							finish();
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
		
	}
