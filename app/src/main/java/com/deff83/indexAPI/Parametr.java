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
public class Parametr extends Activity
{
	private List someModelListeners = Collections.synchronizedList(new ArrayList<ISomeModel>());
	private static Parametr param = new Parametr();
	private int zCoinMain = 60;
	private int colTableOffer = 20;
	private int chastota = 2;
	private int i = 0;
	private String iStr = "";
	private OrdersPriceList ordersPriceList;
	private List<MyZayvkiForTable> listMyZayvkiForTable;
	private List<CoinTool> listCoinTool;
	private List<MyHistoryTrade> listMyHistoryTrade;
	private String balance = "";
	private String svobprice = "";
	private List<MyCoin> listmyCoins;
	private List<Deystvie> listDeystvie = new ArrayList<>();
	private int buysell = 0;
	private boolean booladdOrder = false;
	private AddOrder addOrder;
	private boolean booldelOrder = false;
	private String answerOrderAdd = "";
	private int delOrder;
	private boolean choiseclick = false;
	private String answerOrderDell = "";
	private boolean redaction = false;
	private boolean perestanov = false;
	private MyZayvkiForTable redmyZayvkiForTable;
	
	private Parametr(){
	}
	public int getcolTableOffer() {
		return colTableOffer;
	}
	public void setcolTableOffer(int colTableOffer) {
		this.colTableOffer = colTableOffer;
	}
	public int getChastota() {
		return chastota;
	}
	public void setChastota(int chastota) {
		this.chastota = chastota;
	}
	public int getzCoinMain() {
		return zCoinMain;
	}
	public void setzCoinMain(int zCoinMain) {
		this.zCoinMain = zCoinMain;
	}
	public static Parametr getParametr(){
		return param;
	}
	public MyZayvkiForTable getRedMyZayvkiForTable(){
		return redmyZayvkiForTable;
	}
	public void setRedMyZayvkiForTable(MyZayvkiForTable redmyZayvkiForTable){
		this.redmyZayvkiForTable = redmyZayvkiForTable;
	}
	public String getAnswerOrderAdd(){
		return answerOrderAdd;
	}
	public void setAnswerOrderAdd(String answerOrderAdd){
		this.answerOrderAdd = answerOrderAdd;
	}
	public String getAnswerOrderDell(){
		return answerOrderDell;
	}
	public void setAnswerOrderDell(String answerOrderDell){
		this.answerOrderDell = answerOrderDell;
	}
	public boolean isChoiseclick() {
		return choiseclick;
	}
	public void setChoiseclick(boolean choiseclick) {
		this.choiseclick = choiseclick;
	}
	public boolean isRedaction() {
		return redaction;
	}
	public void setRedaction(boolean redaction) {
		this.redaction = redaction;
	}
	public boolean isPerestanov() {
		return perestanov;
	}
	public void setPerestanov(boolean perestanov) {
		this.perestanov = perestanov;
	}
	public boolean isBoolAddOrder() {
		return booladdOrder;
	}
	public void setBoolAddOrder(boolean addOrder) {
		this.booladdOrder = addOrder;
	}
	public AddOrder getOrderadd() {
		return addOrder;
	}
	public void setOrderadd(AddOrder orderadd) {
		this.addOrder = orderadd;
	}
	public boolean isBooldelOrder() {
		return booldelOrder;
	}
	public void setBooldelOrder(boolean booldelOrder) {
		this.booldelOrder = booldelOrder;
	}
	public int getDelOrder() {
		return delOrder;
	}
	public void setDelOrder(int delOrder) {
		this.delOrder = delOrder;
	}
	public void setOrdersPriceList(OrdersPriceList ordersPriceList){
		this.ordersPriceList = ordersPriceList;
	}
	public OrdersPriceList getOrdersPriceList(){
		return ordersPriceList;
	}
	public int getI(){
		return i;
	}
	public void setI(int i){
		this.i = i;
	}
	public int getBuysell(){
		return buysell;
	}
	public void setBuysell(int buysell){
		this.buysell = buysell;
	}
	public String getIStr(){
		return iStr;
	}
	public void setIStr(String iStr){
		this.iStr = iStr;
	}
	public String getBalance(){
		return balance;
	}
	public void setBalance(String balance){
		this.balance = balance;
	}
	public String getSvobprice(){
		return svobprice;
	}
	public void setSvobprice(String svobprice){
		this.svobprice = svobprice;
	}
	public List<CoinTool>  getListCoinTool(){
		return listCoinTool;
	}
	public void setListCoinTool(List<CoinTool>  listCoinTool){
		this.listCoinTool = listCoinTool;
	}
	public List<MyHistoryTrade>  getListHistoryTrades(){
		return listMyHistoryTrade;
	}
	public void setListHistoryTrades(List<MyHistoryTrade>  listMyHistoryTrade){
		this.listMyHistoryTrade = listMyHistoryTrade;
	}
	public List<MyCoin>  getListMyCoins(){
		return listmyCoins;
	}
	public void setListMyCoins(List<MyCoin>  listmyCoins){
		this.listmyCoins = listmyCoins;
	}
	public List<Deystvie>  getListDeystvie(){
		return listDeystvie;
	}
	public void setListDeystvie(List<Deystvie>  listDeystvie){
		this.listDeystvie = listDeystvie;
	}
	public void addDeystvie(Deystvie deysd){
		listDeystvie.add(deysd);
	}
	public void removeDeystvie(){
		listDeystvie.remove(0);
	}
	public List<MyZayvkiForTable>  getMyZayvkiForTable(){
		return listMyZayvkiForTable;
	}
	public void setMyZayvkiForTable(List<MyZayvkiForTable>  listMyZayvkiForTable){
		this.listMyZayvkiForTable = listMyZayvkiForTable;
	}
	
	public List<ISomeModel> getListModel(){
		return someModelListeners;
	}
	public void setListModel(List<ISomeModel> someModelListeners){
		this.someModelListeners = someModelListeners;
	}
	public void addListener(ISomeModel model){
		someModelListeners.add(model);
	}
	public void addSinglListener(ISomeModel model){
		if(!someModelListeners.contains(model)){
			someModelListeners.add(model);
		}
	}
	public void removeListener(ISomeModel model){
		
				someModelListeners.remove(model);
			
	}
	public void upDate(int argum){
		for(int j = 0; j<someModelListeners.size(); j++){
			ISomeModel imodel = (ISomeModel) someModelListeners.get(j);
			imodel.doUpdate(argum);
		}
	}
}