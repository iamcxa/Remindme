/**
 * 
 */
package me.iamcxa.remindme;

import java.io.OptionalDataException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.R.integer;
import android.R.string;
import android.annotation.SuppressLint;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

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

	// debug msg TAG
	public static final String debugMsgTag = "debugmsg";
	// debug msg on/off
	public static final int debugMsg = 1;

	private CommonUtils() {
	}

	/***********************/
	/** debug msg section **/
	/***********************/
	public static final void debugMsg(int section, String msgs) {
		if (debugMsg == 1) {
			switch (section) {
			case 0:
				Log.w(debugMsgTag, " " + msgs);
				break;
			case 1:
				Log.w(debugMsgTag, "thread " + msgs);
				break;
			case 999:
				Log.w(debugMsgTag, "時間計算失敗!," + msgs);
				break;
			default:
				break;
			}
		}

	}

	/***********************/
	/** getDaysLeft **/
	/***********************/
	@SuppressLint("SimpleDateFormat")
	public static long getDaysLeft(String TaskDate) {

		// 定義時間格式
		// java.text.SimpleDateFormat sdf = new
		// java.text.SimpleDateFormat("yyyy/MM/dd HH:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		// 取得現在時間
		Date now = new Date();
		String nowDate = sdf.format(now);
		debugMsg(0, "now:" + nowDate + ", task:" + TaskDate);
		try {
			// 取得事件時間與現在時間
			Date dt1 = sdf.parse(nowDate);
			Date dt2 = sdf.parse(TaskDate);

			// 取得兩個時間的Unix時間
			Long ut1 = dt1.getTime();
			Long ut2 = dt2.getTime();

			Long timeP = ut2 - ut1;// 毫秒差
			// 相減獲得兩個時間差距的毫秒
			// Long sec = timeP / 1000;// 秒差
			// Long min = timeP / 1000 * 60;// 分差
			// Long hr = timeP / 1000 * 60 * 60;// 時差
			Long day = timeP / (1000 * 60 * 60 * 24);// 日差
			debugMsg(0, "Get days left Sucessed! " + day);
			return day;
		} catch (Exception e) {
			// TODO: handle exception
			debugMsg(999, e.toString());
			return -1;
		}

	}

	// 內部類別
	public static final class RemindmeTaskCursor implements BaseColumns {

		private RemindmeTaskCursor() {
		}

		// 查詢欄位陣列
		public static final String[] PROJECTION = new String[] {
				KeyColumns.KEY_ID, // 0
				KeyColumns.Tittle, // 1
				KeyColumns.StartDate, // 2
				KeyColumns.EndDate,// 3
				KeyColumns.StartTime, // 4
				KeyColumns.EndTime,// 5
				KeyColumns.Is_Repeat, // 6
				KeyColumns.Is_AllDay,// 7
				KeyColumns.LocationName, // 8
				KeyColumns.Coordinates,// 9
				KeyColumns.Distance,// 10
				KeyColumns.CONTENT,// 11
				KeyColumns.CREATED,// 12
				KeyColumns.Is_Alarm_ON, // 13
				KeyColumns.Is_Hide_ON,// 14
				KeyColumns.Is_PW_ON,// 15
				KeyColumns.Password,// 16
				KeyColumns.PriorityWeight,// 17
				KeyColumns.Collaborators,// 18
				KeyColumns.CalendarID,// 19
				KeyColumns.GoogleCalSyncID,// 20
				KeyColumns.other // 21
		};

		public static class IndexColumns {
			public static final int KEY_ID = 0;
			public static final int Tittle = 1;
			public static final int StartDate = 2;
			public static final int EndDate = 3;
			public static final int StartTime = 4;
			public static final int EndTime = 5;
			public static final int Is_Repeat = 6;
			public static final int Is_AllDay = 7;
			public static final int LocationName = 8;
			public static final int Coordinates = 9;
			public static final int Distance = 10;
			public static final int CalendarID = 11;
			public static final int CONTENT = 12;
			public static final int CREATED = 13;
			public static final int Is_Alarm_ON = 14;
			public static final int Is_Hide_ON = 15;
			public static final int Is_PW_ON = 16;
			public static final int Password = 17;
			public static final int PriorityWeight = 18;
			public static final int Collaborators = 19;
			public static final int GoogleCalSyncID = 20;
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
	}
}
