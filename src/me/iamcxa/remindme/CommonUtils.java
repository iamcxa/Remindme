/**
 * 
 */
package me.iamcxa.remindme;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author cxa
 * 
 */
public class CommonUtils {

	// 授權常數
	public static final String AUTHORITY = "me.iamcxa.remindme";

	// URI常數
	public static final String TASKLIST = "remindmetasklist";

	// 存取Uri
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TASKLIST);
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.iamcxa"
			+ "." + TASKLIST;
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iamcxa"
			+ "." + TASKLIST;

	// 預設排序常數
	public static final String DEFAULT_SORT_ORDER = "created DESC";

	private CommonUtils() {
	}

	// 內部類別
	public static final class RemindmeTaskCursor implements BaseColumns {

		private RemindmeTaskCursor() {
		}

		// 查詢欄位陣列
		public static final String[] PROJECTION = new String[] {
				KeyColumns.KEY_ID, KeyColumns.GoogleCalSyncID,
				KeyColumns.CalendarID, KeyColumns.Tittle, KeyColumns.StartDate,
				KeyColumns.EndDate, KeyColumns.StartTime, KeyColumns.EndTime,
				KeyColumns.Is_Repeat, KeyColumns.Is_AllDay,
				KeyColumns.LocationName, KeyColumns.Distance,
				KeyColumns.CONTENT, KeyColumns.PriorityWeight,
				KeyColumns.Collaborators, KeyColumns.CREATED,
				KeyColumns.CONTENT, KeyColumns.Is_Hide_ON, KeyColumns.Is_PW_ON,
				KeyColumns.Password, KeyColumns.Is_Alarm_ON, KeyColumns.other };

		public static final String KEY_TITLE = "title";
		public static final String KEY_SUBTITLE = "subtitle";
		public static final String KEY_HEADER = "header";
		public static final String KEY_THUMBNAIL = "thumb";

		public static class IndexColumns {
			public static final int KEY_ID = 0;
			public static final int GoogleCalSyncID = 1;
			public static final int Tittle = 2;
			public static final int StartTime = 3;
			public static final int EndTime = 4;
			public static final int StartDate = 5;
			public static final int EndDate = 6;
			public static final int Is_Repeat = 7;
			public static final int Is_AllDay = 8;
			public static final int LocationName = 9;
			public static final int Coordinates = 10;
			public static final int Distance = 11;
			public static final int CalendarID = 12;
			public static final int CONTENT = 13;
			public static final int CREATED = 14;
			public static final int Is_Alarm_ON = 15;
			public static final int Is_Hide_ON = 16;
			public static final int Is_PW_ON = 17;
			public static final int Password = 18;
			public static final int PriorityWeight = 19;
			public static final int Collaborators = 20;
			public static final int other = 21;
			// public static final int AlarmSoundPath = "AlarmSoundPath";
		}

		// 其他欄位常數
		public static class KeyColumns {
			// 00 index ID
			public static final String KEY_ID = "_id";
			// 01 需要同步之 Google calender日曆ID
			public static final String GoogleCalSyncID = "GoogleCalSyncID";
			// 02 事件標題
			public static final String Tittle = "tittle";
			// 03 開始與結束時間
			public static final String StartTime = "StartTime";
			public static final String EndTime = "EndTime";
			// 04 開始與結束日期
			public static final String StartDate = "StartDate";
			public static final String EndDate = "EndDate";
			// 05 開關 - 事件是否重複
			public static final String Is_Repeat = "Is_Repeat";
			// 06 開關 - 是否為全天事件
			public static final String Is_AllDay = "Is_AllDay";
			// 07 事件地點（名稱）
			public static final String LocationName = "TaskLocationName";
			// 08 事件座標
			public static final String Coordinates = "Coordinates";
			// 09 事件與當下使用者地點之距離
			public static final String Distance = "Distance";
			// 10 日曆本身之ID(備用)
			public static final String CalendarID = "CalendarID";
			// 11 事件內容（文字）
			public static final String CONTENT = "content";
			// 12 建立時間
			public static final String CREATED = "created";
			// 13 開關 - 是否提醒
			public static final String Is_Alarm_ON = "Is_Alarm_ON";
			// 14 開關 - 事件是否隱藏
			public static final String Is_Hide_ON = "Is_Hide_ON";
			// 15 開關 - 是否加密
			public static final String Is_PW_ON = "Is_PW_ON";
			// 16 密碼
			public static final String Password = "password";
			// 17 即時權重
			public static final String PriorityWeight = "PriorityWeight";
			// 18 協作者GMAIL
			public static final String Collaborators = "Collaborators";
			// 19 備用
			public static final String other = "other";
			// 18 提醒之聲音檔案路徑
			// public static final String AlarmSoundPath = "AlarmSoundPath";
		}
		
		public static class GpsSetting{
			//GPS超時關閉改用wifi
			public static final int TIMEOUT_SEC = 5;
			//Gps狀態
			public static boolean GpsStatus=false;
			
			
		}
	}
}
