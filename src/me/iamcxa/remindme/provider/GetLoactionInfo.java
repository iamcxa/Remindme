package me.iamcxa.remindme.provider;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class GetLoactionInfo {
	//設定容忍偏移距離 單位 公里
	public static float Spacing=1;
	
	public static int isKeywordDataExist(Context context,double latitude,double longitude,String keyword,String type){
		int Locationid=-1;
		DbEdit DB = new DbEdit(context);
		DB.openDB();
		//Cursor cursor = DB.getAll();
		Cursor cursor = DB.getKeywordLocation(keyword);
		int rows_num = cursor.getCount();
		if(rows_num!=0){
			cursor.moveToFirst();
			for(int a=0;a<rows_num;a++){
				int id = cursor.getInt(0);
				float lat2= cursor.getFloat(1);
				float lng2= cursor.getFloat(2);
				if(Haversine.haversine(latitude,longitude,lat2,lng2)<Spacing)
				{
					Locationid = id;
					Log.i("資料庫內最近資料",Haversine.haversine(latitude,longitude,lat2,lng2)+" 公里");
					Log.i("內容","id:"+id+"+\n經緯度"+lat2+"+"+lng2);
				}
				cursor.moveToNext();
			}
		}
		DB.closeDB();
		return Locationid;
	}
	
	public static String GetSQLRecentLocation(Context context,double latitude,double longitude,long id){
		DbEdit GetSQLData = new DbEdit(context);
		GetSQLData.openDB();
		Cursor cursor = GetSQLData.get(id);
		GetSQLData.closeDB();
		String Json = cursor.getString(5);
		PlaceApiJsonDecode Data = new PlaceApiJsonDecode(context, Json, String.valueOf(latitude), String.valueOf(longitude));
		return Data.RecentLocation();
	}
	
}
