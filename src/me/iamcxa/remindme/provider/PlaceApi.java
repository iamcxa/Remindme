package me.iamcxa.remindme.provider;

import android.content.Context;
import android.util.Log;

public class PlaceApi {
	
	String key="AIzaSyBaGnGbSke5z04QDox_qXpLCxU6sbqkAMg";
	String lat;
	String lng;
	String keyword;
	String types;
	String Url="https://maps.googleapis.com/maps/api/place/search/json?";
	Context context;
	String Json;
	
	PlaceApi(Context context,String lat,String lng,String keyword,String types){
		this.lat=lat;
		this.lng=lng;
		this.keyword=keyword;
		this.types=types;
		this.context=context;
	}
	
	/*Api doc https://developers.google.com/places/documentation/search?hl=zh-TW
	 * types 如需多個能 用|例 accounting|airport|amusement_park|aquarium|art_gallery|atm|bakery|bank|bar*/
	public String PlaceApiGet(){
		String Url = "";
		Url+=this.Url;
		Url+="&location="+this.lat+","+this.lng;
		Url+="&rankby=distance";
		Url+="&keyword="+this.keyword;
		Url+="&types="+this.types;
		Url+="&language=zh-TW";
		Url+="&sensor=false";
		Url+="&key="+this.key;
		Log.i("Url", Url);
		
		String Json=HttpGetModule.GetPlace(Url);
		
//		Thread WaitGetJson = new Thread(GetJson);
//		WaitGetJson.start();
		PlaceApiJsonDecode JsonDecode =new PlaceApiJsonDecode(context,Json,lat,lng);
      	DbEdit DB = new DbEdit(context);
  		DB.openDB();
  		DB.add(Json,Float.parseFloat(lat), Float.parseFloat(lng),this.keyword,this.types);
  		DB.closeDB();
      	//JsonDecode.AllPlace();
  		String place=JsonDecode.RecentLocation();
  		return place;
	}
	
//	private Runnable GetJson = new Runnable() {
//		public void run() {
//			while(true){
//              try {
//                  Thread.sleep(1000);
//              } catch (InterruptedException e) {
//                  // TODO Auto-generated catch block
//                  e.printStackTrace();
//              }
//              if(HttpGetModule.isGetJson()){
//	              	//Log.i("json",HttpGetModule.GetJson());
//	              	JsonDecode JsonDecode =new JsonDecode(context,HttpGetModule.GetJson(),lat,lng);
//	              	DbEdit DB = new DbEdit(context);
//	          		DB.openDB();
//	          		DB.add(HttpGetModule.GetJson(),Float.parseFloat(lat), Float.parseFloat(lng));
//	          		DB.closeDB();
//	              	JsonDecode.AllPlace();
//	              	
//	                return;
//                }
//             }
//		}
//	};
}
