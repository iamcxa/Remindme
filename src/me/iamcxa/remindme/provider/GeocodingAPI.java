package me.iamcxa.remindme.provider;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.util.Log;

public class GeocodingAPI {
	Context context;
	String address;
	String key="AIzaSyBaGnGbSke5z04QDox_qXpLCxU6sbqkAMg";
	String Json;
	String Url="https://maps.googleapis.com/maps/api/geocode/json?";
	public GeocodingAPI(Context context, String address){
		this.context= context;
		this.address=address;
	}
	
	
	public String GeocodingApiAddressGet(){
		String Url = "";
		Url+=this.Url;
		try {
			Url+="address="+URLEncoder.encode(address,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Url+="&language=zh-TW";
		Url+="&sensor=false";
		Url+="&key="+this.key;
		Log.i("Url", Url);
		
		String Json=HttpGetModule.GetPlace(Url);

		GeocodingAPIJsonDecode JsonDecode =new GeocodingAPIJsonDecode(context,Json);
  		String place=JsonDecode.GetAddress();
  		return place;
	}
	
	public LatLng GeocodingApiLatLngGet(){
		String Url = "";
		Url+=this.Url;
		try {
			Url+="address="+URLEncoder.encode(address,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Url+="&language=zh-TW";
		Url+="&sensor=false";
		Url+="&key="+this.key;
		Log.i("Url", Url);
		
		String Json=HttpGetModule.GetPlace(Url);

		GeocodingAPIJsonDecode JsonDecode =new GeocodingAPIJsonDecode(context,Json);
		LatLng LatLng=JsonDecode.GetLatLng();
  		return LatLng;
	}
}
