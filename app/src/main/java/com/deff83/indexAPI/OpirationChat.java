package com.deff83.indexAPI;
import android.content.*;
import android.app.*;
import java.security.*;
import android.util.*;
import android.util.Base64;
import java.io.*;
import okhttp3.*;
import org.json.*;
import android.widget.*;
import java.util.*;
import android.preference.*;
import java.text.*;



public class OpirationChat  {
	private OkHttpClient clientz =  new OkHttpClient();
	
	private static OpirationChat opiration = new OpirationChat();
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private Context context;
	private Parametr param = Parametr.getParametr();
	
	private OpirationChat(){
	}
	public static OpirationChat getOpiration(){
		
		
		return opiration;
		
	}
	public void inicialisation (Context context){
		this.context = context;
		pref = context.getSharedPreferences("CAT", Context.MODE_PRIVATE);
		editor = pref.edit();
		
		
	}
	public String getlasthundrid(int pageSize){
		String lastid = null;
		try{
			String urlmeshund = "http://events.webmoney.ru/api/discuss/Paging?eventId=268270102&direction=0&pageSize="+pageSize+"";
		//RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
		Request requesturlhund = new Request.Builder()
			.url(urlmeshund)

			.build();
		Response responseurlhund = null;
		responseurlhund = clientz.newCall(requesturlhund).execute();
			String answirmesrivhund = responseurlhund.body().string();
			try{
				JSONArray jsonArrayhund;
				jsonArrayhund = new JSONArray(answirmesrivhund);
				JSONObject jsonobj = jsonArrayhund.getJSONObject(0);
				lastid = jsonobj.getString("id");
		}catch(JSONException e){}
		}catch(Exception e){}
		return lastid;
	}
	public List<ChatMessage> mess(String lastId){
		List<ChatMessage> listMess = null;
		try{
			
			clientz = new OkHttpClient();
			
			String urlmes = "http://events.webmoney.ru/api/discuss/GetListPushes?eventId=268270102&groupUid=6be4dadf-c7ab-44e1-a1bc-b5ba4fa961c0&lastId="+lastId+"";
			Request requesturl = new Request.Builder()
				.url(urlmes)
				.build();
			Response responseurl = null;

			responseurl = clientz.newCall(requesturl).execute();
			String answirurl = responseurl.body().string(); 
			try{

				JSONArray jsonArray;
				jsonArray = new JSONArray(answirurl);
				int url_length = jsonArray.length();
				
				String newmes = "";
				String idmesstr = lastId;
				listMess = ParametrChat.getParametrChat().getListChatMessage();
				for (int j = 0; j<url_length; j++){
					
					JSONObject idmesobj = jsonArray.getJSONObject(j);
					Integer idmes = idmesobj.getInt("discusId");
					idmesstr = idmes+"";
					
					//запрос текста сообщения
					try {
						String urlmesriv = "http://events.webmoney.ru/api/discuss/get2?discussId="+idmesstr+"&groupUid=6be4dadf-c7ab-44e1-a1bc-b5ba4fa961c0";
						
						Request requestmesriv = new Request.Builder()
							.url(urlmesriv)
							.build();
						Response responsemesriv = null;



						responsemesriv = clientz.newCall(requestmesriv).execute();
						String answirmesriv = responsemesriv.body().string(); 
						try{
							JSONObject jsonObjectmes;
							jsonObjectmes = new JSONObject(answirmesriv);
							String textmes = jsonObjectmes.getString("message");
							String datecreated = jsonObjectmes.getString("datecreated");
							JSONObject author = jsonObjectmes.getJSONObject("author");
							String nickname = author.getString("nickname");
							String wmidmes = author.getString("wmid");
							String attestat = author.getString("attestat");
							JSONObject imgjson = author.getJSONObject("icon");
							String urlimg = imgjson.getString("normal");
							ChatMessage chatMessage = new ChatMessage(idmesstr,textmes, nickname, datecreated,urlimg, attestat, wmidmes);
							listMess.add(chatMessage);
							
							
						}
						catch (JSONException e){
							

						}
					
					} catch (Exception e){
					

					}
					


				}
				ParametrChat.getParametrChat().setLastId(idmesstr);

			}
			catch (JSONException e){
				
			}
			}catch(Exception e){}
		
		return listMess;
	}
		
	
}
