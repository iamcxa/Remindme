package me.iamcxa.remindme.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DbEdit {
	private Context context;
	private DBHelper DH = null;
	private SQLiteDatabase db;
	DbEdit(Context context){
		this.context=context;
	}
	
	public void openDB(){
    	DH = new DBHelper(context);  
    }
	
    public void closeDB(){
    	DH.close();    	
    }
    
    public void add(String Json,float lat,float lng,String keyword,String type){
        db = DH.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_lat", lat);
        values.put("_lng", lng);
        values.put("_keyword", keyword);
        values.put("_type", type);
        values.put("_ApiJson", Json);
        db.insert(DBHelper._TableName, null, values);
    }
    
    public Cursor getAll() {
    	db = DH.getWritableDatabase();
		return db.query(DBHelper._TableName,		//資料表名稱
		new String[] {"_ID", "_lat", "_lng","_keyword","_type","_ApiJson"},	//欄位名稱
		null, // WHERE
		null, // WHERE 的參數
		null, // GROUP BY
		null, // HAVING
		null  // ORDOR BY
		);
	}
    
    public Cursor get(long rowId) {
    	db = DH.getWritableDatabase();
		Cursor cursor = db.query(true,
		DBHelper._TableName,				//資料表名稱
		new String[] {"_ID", "_lat", "_lng","_keyword","_type","_ApiJson"},	//欄位名稱
		"_ID="+rowId,				//WHERE
		null, // WHERE 的參數
		null, // GROUP BY
		null, // HAVING
		null, // ORDOR BY
		null  // 限制回傳的rows數量
		);
 
		// 注意：不寫會出錯
		if (cursor != null) {
			cursor.moveToFirst();	//將指標移到第一筆資料
		}
		return cursor;
	}
   
    public Cursor getKeywordLocation(String keyword) {
    	db = DH.getWritableDatabase();
		Cursor cursor = db.query(true,
		DBHelper._TableName,				//資料表名稱
		new String[] {"_ID", "_lat", "_lng","_keyword","_type","_ApiJson"},	//欄位名稱
		"_keyword=?",				//WHERE
		new String[] {keyword}, // WHERE 的參數
		null, // GROUP BY
		null, // HAVING
		null, // ORDOR BY
		null  // 限制回傳的rows數量
		);
 
		// 注意：不寫會出錯
		if (cursor != null) {
			cursor.moveToFirst();	//將指標移到第一筆資料
		}
		return cursor;
	}
    
    public Cursor getTypedLocation(String type) {
    	db = DH.getWritableDatabase();
		Cursor cursor = db.query(true,
		DBHelper._TableName,				//資料表名稱
		new String[] {"_ID", "_lat", "_lng","_keyword","_type","_ApiJson"},	//欄位名稱
		"_type=?",				//WHERE
		new String[] {type}, // WHERE 的參數
		null, // GROUP BY
		null, // HAVING
		null, // ORDOR BY
		null  // 限制回傳的rows數量
		);
 
		// 注意：不寫會出錯
		if (cursor != null) {
			cursor.moveToFirst();	//將指標移到第一筆資料
		}
		return cursor;
	}
   
}
