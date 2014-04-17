/**
 * 
 */
package me.iamcxa.remindme.provider;

import java.util.HashMap;

import me.iamcxa.remindme.CommonUtils;
import me.iamcxa.remindme.CommonUtils.RemindmeTaskCursor;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

/**
 * @author cxa 資料庫操作方法
 * 
 */

public class TaskDBProvider extends ContentProvider {
	// 資料庫名稱常數
	private static final String DATABASE_NAME = "Remindme_Task.db";
	// 資料庫版本常數
	private static final int DATABASE_VERSION = 1;
	// 資料表名稱常數
	private static final String TASK_LIST_TABLE_NAME = "RemindmeTask";
	// 查詢欄位集合
	private static HashMap<String, String> sTaskListProjectionMap;
	// 查詢、更新條件
	private static final int TASKS = 1;
	private static final int TASK_ID = 2;
	// Uri工具類別
	private static final UriMatcher sUriMatcher;
	// 資料庫工具類別實例
	private DatabaseHelper mOpenHelper;

	// 內部工具類別，建立或開啟資料庫、建立或刪除資料表
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		// 建立資料表
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE "
					+ TASK_LIST_TABLE_NAME
					+ " ("
					+ RemindmeTaskCursor.KeyColumns.KEY_ID // 0
					+ " INTEGER PRIMARY KEY autoincrement,"
					+ RemindmeTaskCursor.KeyColumns.Tittle // 2
					+ " TEXT," // 2
					+ RemindmeTaskCursor.KeyColumns.StartDate
					+ " TEXT," 
					+ RemindmeTaskCursor.KeyColumns.EndDate
					+ " TEXT," 
					+ RemindmeTaskCursor.KeyColumns.StartTime // 3
					+ " TEXT,"
					+ RemindmeTaskCursor.KeyColumns.EndTime
					+ " TEXT," 
					+ RemindmeTaskCursor.KeyColumns.Is_Repeat
					+ " INTEGER,"
					+ RemindmeTaskCursor.KeyColumns.Is_AllDay
					+ " INTEGER," 
					+ RemindmeTaskCursor.KeyColumns.LocationName
					+ " TEXT," 
					+ RemindmeTaskCursor.KeyColumns.Coordinates
					+ " TEXT," 
					+ RemindmeTaskCursor.KeyColumns.Distance
					+ " TEXT," 
					+ RemindmeTaskCursor.KeyColumns.CONTENT
					+ " TEXT," // 13
					+ RemindmeTaskCursor.KeyColumns.CREATED
					+ " TEXT," // 14
					+ RemindmeTaskCursor.KeyColumns.Is_Alarm_ON
					+ " INTEGER,"// 15
					+ RemindmeTaskCursor.KeyColumns.Is_Hide_ON
					+ " INTEGER," // 16
					+ RemindmeTaskCursor.KeyColumns.Is_PW_ON
					+ " INTEGER," // 17
					+ RemindmeTaskCursor.KeyColumns.Password
					+ " TEXT," // 18
					+ RemindmeTaskCursor.KeyColumns.PriorityWeight
					+ " INTEGER," // 19
					+ RemindmeTaskCursor.KeyColumns.Collaborators + " TEXT," // 20
					+ RemindmeTaskCursor.KeyColumns.GoogleCalSyncID 
					+ " TEXT,"
					+ RemindmeTaskCursor.KeyColumns.CalendarID
					+ " TEXT," // 12
					+ RemindmeTaskCursor.KeyColumns.other + " TEXT" // 21
					+ ");");
		}

		// 刪除資料表
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS" + TASK_LIST_TABLE_NAME);
			onCreate(db);
		}
	}

	// 建立或開啟資料庫
	@Override
	public boolean onCreate() {
		mOpenHelper = new DatabaseHelper(getContext());
		return true;
	}

	// 查詢
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		switch (sUriMatcher.match(uri)) {
		// 查詢所有工作
		case TASKS:
			qb.setTables(TASK_LIST_TABLE_NAME);
			qb.setProjectionMap(sTaskListProjectionMap);
			break;
		// 根據ID查詢
		case TASK_ID:
			qb.setTables(TASK_LIST_TABLE_NAME);
			qb.setProjectionMap(sTaskListProjectionMap);
			qb.appendWhere(BaseColumns._ID + "=" + uri.getPathSegments().get(1));
			break;
		default:
			throw new IllegalArgumentException("Uri錯誤！ " + uri + "/"
					+ sUriMatcher.match(uri));
		}

		// 使用預設排序
		String orderBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orderBy = CommonUtils.DEFAULT_SORT_ORDER;
		} else {
			orderBy = sortOrder;
		}

		// 取得資料庫實例
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		// 返回游標集合
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	// 取得類型
	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case TASKS:
			return CommonUtils.CONTENT_TYPE;
		case TASK_ID:
			return CommonUtils.CONTENT_ITEM_TYPE;

		default:
			throw new IllegalArgumentException("錯誤的 URI！ " + uri);
		}
	}

	// 保存資料
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		if (sUriMatcher.match(uri) != TASKS) {
			throw new IllegalArgumentException("錯誤的 URI！ " + uri);
		}
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}
		// 取得資料庫實例
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		// 保存資料返回欄位ID
		long rowId = db.insert(TASK_LIST_TABLE_NAME,
				RemindmeTaskCursor.KeyColumns.CONTENT, values);
		if (rowId > 0) {
			Uri taskUri = ContentUris.withAppendedId(CommonUtils.CONTENT_URI,
					rowId);
			getContext().getContentResolver().notifyChange(taskUri, null);
			return taskUri;
		}
		throw new SQLException("插入資料失敗 " + uri);
	}

	// 刪除資料
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		// 取得資料庫實例
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
		// 根據指定條件刪除
		case TASKS:
			count = db.delete(TASK_LIST_TABLE_NAME, where, whereArgs);
			break;
		// 根據指定條件和ID刪除
		case TASK_ID:
			String noteId = uri.getPathSegments().get(1);
			count = db.delete(TASK_LIST_TABLE_NAME,
					BaseColumns._ID
							+ "="
							+ noteId
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("錯誤的 URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	// 更新資料
	@Override
	public int update(Uri uri, ContentValues values, String where,
			String[] whereArgs) {
		// 取得資料庫實例
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
		// 根據指定條件更新
		case TASKS:
			count = db.update(TASK_LIST_TABLE_NAME, values, where, whereArgs);
			break;
		// 根據指定條件和ID更新
		case TASK_ID:
			String noteId = uri.getPathSegments().get(1);
			count = db.update(TASK_LIST_TABLE_NAME, values,
					BaseColumns._ID
							+ "="
							+ noteId
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		default:
			throw new IllegalArgumentException("錯誤的 URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	static {
		// Uriぁ匹配工具類別
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(CommonUtils.AUTHORITY, CommonUtils.TASKLIST, TASKS);
		sUriMatcher.addURI(CommonUtils.AUTHORITY, CommonUtils.TASKLIST + "/#",
				TASK_ID);

		// 實例化查詢欄位集合
		sTaskListProjectionMap = new HashMap<String, String>();
		// 添加查詢欄位
		sTaskListProjectionMap.put(BaseColumns._ID, BaseColumns._ID);
		sTaskListProjectionMap.put(
				RemindmeTaskCursor.KeyColumns.GoogleCalSyncID,
				RemindmeTaskCursor.KeyColumns.GoogleCalSyncID);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.CalendarID,
				RemindmeTaskCursor.KeyColumns.CalendarID);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.Tittle,
				RemindmeTaskCursor.KeyColumns.Tittle);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.StartDate,
				RemindmeTaskCursor.KeyColumns.StartDate);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.EndDate,
				RemindmeTaskCursor.KeyColumns.EndDate);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.StartTime,
				RemindmeTaskCursor.KeyColumns.StartTime);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.EndTime,
				RemindmeTaskCursor.KeyColumns.EndDate);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.LocationName,
				RemindmeTaskCursor.KeyColumns.LocationName);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.Distance,
				RemindmeTaskCursor.KeyColumns.Distance);
		sTaskListProjectionMap.put(
				RemindmeTaskCursor.KeyColumns.PriorityWeight,
				RemindmeTaskCursor.KeyColumns.PriorityWeight);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.Is_Repeat,
				RemindmeTaskCursor.KeyColumns.Is_Repeat);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.Is_AllDay,
				RemindmeTaskCursor.KeyColumns.Is_AllDay);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.CONTENT,
				RemindmeTaskCursor.KeyColumns.CONTENT);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.CREATED,
				RemindmeTaskCursor.KeyColumns.CREATED);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.Is_Alarm_ON,
				RemindmeTaskCursor.KeyColumns.Is_Alarm_ON);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.Is_Hide_ON,
				RemindmeTaskCursor.KeyColumns.Is_Hide_ON);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.Is_PW_ON,
				RemindmeTaskCursor.KeyColumns.Is_PW_ON);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.Password,
				RemindmeTaskCursor.KeyColumns.Password);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.other,
				RemindmeTaskCursor.KeyColumns.other);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.Collaborators,
				RemindmeTaskCursor.KeyColumns.Collaborators);
		sTaskListProjectionMap.put(RemindmeTaskCursor.KeyColumns.Coordinates,
				RemindmeTaskCursor.KeyColumns.Coordinates);

	}
}
