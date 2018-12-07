package com.deff83.indexAPI;
import android.app.*;
import android.os.*;
import okhttp3.*;
import java.io.*;
import android.widget.*;

import org.w3c.dom.*;
import java.util.*;

import org.json.*;
import android.view.*;
import android.content.*;
import android.view.inputmethod.*;
import android.view.View.*;
import android.text.*;
import android.graphics.*;
import android.preference.*;

import android.view.ViewDebug.*;
import android.widget.Toolbar.*;
import android.support.v4.content.*;
import android.content.res.*;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.widget.*;
import android.transition.*;
import android.graphics.drawable.*;
public class ParametrChat extends Activity
{
	private List someModelListeners = Collections.synchronizedList(new ArrayList<IChatModel>());
	private static ParametrChat param = new ParametrChat();
	private String lastId;
	private List<ChatMessage> listChatMessage = new ArrayList<>();
	private ParametrChat(){
	}
	
	public static ParametrChat getParametrChat(){
		return param;
	}
	
	
	
	public String getLastId() {
		return lastId;
	}
	public void setLastId(String lastId) {
		this.lastId = lastId;
	}
	public List<ChatMessage>  getListChatMessage(){
		return listChatMessage;
	}
	public void setListChatMessage(List<ChatMessage>  listChatMessage){
		this.listChatMessage = listChatMessage;
	}
	
	
	public void addListener(IChatModel model){
		someModelListeners.add(model);
	}
	public void addSinglListener(IChatModel model){
		if(!someModelListeners.contains(model)){
			someModelListeners.add(model);
		}
	}
	public void removeListener(IChatModel model){
		
				someModelListeners.remove(model);
			
	}
	public void upDateChat(int argum){
		for(int j = 0; j<someModelListeners.size(); j++){
			IChatModel imodel = (IChatModel) someModelListeners.get(j);
			imodel.doUpdateChat(argum);
		}
	}
}