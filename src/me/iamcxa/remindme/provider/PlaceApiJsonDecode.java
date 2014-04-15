package me.iamcxa.remindme.provider;

import java.text.NumberFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class PlaceApiJsonDecode {
	String Json;
	String lat1;
	String lng1;
	JSONObject j;
	Context context;
	PlaceApiJsonDecode(Context context,String Json,String lat,String lng){
		this.Json=Json;
		this.lat1=lat;
		this.lng1=lng;
		this.context=context;
		try {
			j= new JSONObject(Json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("JsonPaser錯誤", e.toString());
		}
	}
	
	public void AllPlace(){
		try {
			if(j.get("status").toString().equals("OK"))
			{
				JSONArray PlaceArray = j.getJSONArray("results");
				for(int a =0;a<PlaceArray.length();a++)
				{
					Object lat2 = PlaceArray.getJSONObject(a).getJSONObject("geometry").getJSONObject("location").get("lat");
					Object lng2 = PlaceArray.getJSONObject(a).getJSONObject("geometry").getJSONObject("location").get("lng");
					Object name = PlaceArray.getJSONObject(a).get("name");
					//Log.i("lat","經緯度:"+lat2.toString()+","+lng2.toString()+"\n名稱:"+name.toString()+"\n距離:"+Haversine.haversine(Double.parseDouble(lat1), Double.parseDouble(lng1), Double.parseDouble(lat2.toString()), Double.parseDouble(lng2.toString()))+"公里\n  ");
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*RecentLocation
	 * return 位置名稱,距離(公里),地址*/
	
	public String RecentLocation(){
		String LocationName = null;
		String Address=null;
		double minDistance = 0;
		try {
			if(j.get("status").toString().equals("OK"))
			{
				JSONArray PlaceArray = j.getJSONArray("results");
				Object lat2 = PlaceArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat");
				Object lng2 = PlaceArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng");
				Object name = PlaceArray.getJSONObject(0).get("name");
				Object address = PlaceArray.getJSONObject(0).get("vicinity");
				minDistance=Haversine.haversine(Double.parseDouble(lat1), Double.parseDouble(lng1), Double.parseDouble(lat2.toString()), Double.parseDouble(lng2.toString()));
				LocationName=name.toString();
				Address=address.toString();
				for(int a =1;a<PlaceArray.length();a++)
				{
					lat2 = PlaceArray.getJSONObject(a).getJSONObject("geometry").getJSONObject("location").get("lat");
					lng2 = PlaceArray.getJSONObject(a).getJSONObject("geometry").getJSONObject("location").get("lng");
					name = PlaceArray.getJSONObject(a).get("name");
					double Distance= Haversine.haversine(Double.parseDouble(lat1), Double.parseDouble(lng1), Double.parseDouble(lat2.toString()), Double.parseDouble(lng2.toString()));
					//Log.i("lat","經緯度:"+lat2.toString()+","+lng2.toString()+"\n名稱:"+name.toString()+"\n距離:"+Haversine.haversine(Double.parseDouble(lat1), Double.parseDouble(lng1), Double.parseDouble(lat2.toString()), Double.parseDouble(lng2.toString()))+"公里\n  ");
					if(Distance<minDistance)
					{
						minDistance=Distance;
						LocationName=name.toString();
						
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2); 
		Log.i("最近地點",LocationName+" "+nf.format(minDistance)+"公里");
		return LocationName+","+nf.format(minDistance)+","+Address;
	}
}
