package me.iamcxa.remindme.provider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HttpGetModule {
	//透過Get 方法 取得資料
	//其中包含將中文字的部分自動進行 URL Encode
	//確保字符的正確
	
	private static Handler handler = new Handler();
	private static  String urlPath;
	private static boolean isGetJson;
	private static String Json;
	
	public static  String GetStringByURL() {
	    InputStream is = null;
	    String result = "";
	    try {
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(urlPath);
	        httpGet.setHeader("Content-type",
	        		"application/x-www-form-urlencoded; charset=utf-8");
	        HttpResponse response = httpclient.execute(httpGet);
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
	    } catch (Exception e) {
	        result = "";
	        Log.e("WebUtil", "取資料時發生問題:" + e.toString());
	    }
	    // convert response to string
	    try {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                is, "UTF-8"), 8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	        is.close();
	        result = sb.toString();
	    } catch (Exception e) {
	        result = "";
	        Log.e("WebUtil", "Error converting result " + e.toString());
	    }
	    Json = result;
	    return result;
	}

	public static String  GetPlace(String url){
		urlPath=url;
		Thread waitHttpGet = new Thread(HttpGet);
		waitHttpGet.start();
		try {
			waitHttpGet.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Json;
	}
	
	public static Runnable HttpGet = new Runnable(){
		public void run() {
			Message msg = new Message();
			Bundle data = new Bundle();
			isGetJson=false;
			data.putString("value",GetStringByURL());
			msg.setData(data);
	        handler2.sendMessage(msg);
		}
	};
	
	public static Handler handler2 = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String val = data.getString("value");
	        isGetJson=true;
	    }
	};
	
	public static boolean isGetJson()
	{
		return isGetJson;
	}
	
	public static String GetJson(){
		return Json;
	}
}
