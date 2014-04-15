/**
 * 
 */
package me.iamcxa.remindme;

import java.util.Calendar;
import java.util.Date;

<<<<<<< HEAD
import me.iamcxa.remindme.CommonUtils.RemindmeTaskCursor;
=======
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import me.iamcxa.remindme.CommonUtils.RemindmeTaskCursor;
import me.iamcxa.remindme.provider.GPSCallback;
import me.iamcxa.remindme.provider.GPSManager;
import me.iamcxa.remindme.provider.GeocodingAPI;
>>>>>>> merageGps
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
<<<<<<< HEAD
import android.net.Uri;
import android.os.Bundle;
=======
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
>>>>>>> merageGps
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
<<<<<<< HEAD
=======
import android.widget.Button;
>>>>>>> merageGps
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
<<<<<<< HEAD
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
=======
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import me.iamcxa.remindme.provider.WorkaroundMapFragment;

>>>>>>> merageGps

/**
 * @author cxa
 * 
 */

<<<<<<< HEAD
public class RemindmeTaskEditor extends Activity {

=======
public class RemindmeTaskEditor extends FragmentActivity  implements  GPSCallback {

	//GPS模組
	private static  GPSManager gpsManager = null;
	
	//pick
	private static GoogleMap map;
	
>>>>>>> merageGps
	private static EditText tittlEditText;

	// 備忘錄訊息列表
	private ListView listView;
	// 提醒日期
	private static int mYear;
	private static int mMonth;
	private static int mDay;
	// 提醒時間
	private static int mHour;
	private static int mMinute;
	// 日期顯示TextView
	private static TextView dateTittle;

	private static TextView dateDesc, locationDesc;
	// 時間顯示TextView
	private static TextView timeTittle, timeDesc;
	// 提醒內容TextView
	private static TextView contentTittle;

	private static TextView contentDesc;

	private static TextView locationTittle;
<<<<<<< HEAD
=======
	

	private static Button Search;
	
	private static Button OK;
	
	private static EditText SearchText;
>>>>>>> merageGps
	// 是否開啟提醒
	private int on_off = 0;
	// 是否聲音警告
	private int alarm = 0;

	private static int target;

	private static EditText datePicker, timePicker, contentBox, locationBox;

	// 顯示日期、時間對話方塊常數
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;

	// 保存內容、日期與時間字串
	private static String content = null;

	private String switcher;

	private String date1;

	private String time1;

	private static String locationName;
<<<<<<< HEAD
=======
	
	//經緯度
	private static Double Latitude;
	private static Double Longitude;
	private static ScrollView main_scrollview;
>>>>>>> merageGps
	// 備忘錄ID
	private int id1;
	// 多選框
	private CheckedTextView ctv1, ctv2;
	// 存取佈局實例
	private static LayoutInflater li;

	// 初始化方法
	private void init(Intent intent) {
		Bundle b = intent.getBundleExtra("b");
		if (b != null) {
			id1 = b.getInt("id");
			content = b.getString("content");
			date1 = b.getString("date1");
			time1 = b.getString("time1");
			on_off = b.getInt("on_off");
			alarm = b.getInt("alarm");

			if (date1 != null && date1.length() > 0) {
				String[] strs = date1.split("/");
				mYear = Integer.parseInt(strs[0]);
				mMonth = Integer.parseInt(strs[1]);
				mDay = Integer.parseInt(strs[2]);
			}

			if (time1 != null && time1.length() > 0) {
				String[] strs = time1.split(":");
				mHour = Integer.parseInt(strs[0]);
				mMinute = Integer.parseInt(strs[1]);
			}
		}

	}

	public View vv;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_editor);
<<<<<<< HEAD

=======
		SearchText = (EditText)findViewById(R.id.SearchText);
		Search = (Button)findViewById(R.id.Search);
		OK = (Button)findViewById(R.id.OK);
		Search.setOnClickListener(SearchPlace);
		OK.setOnClickListener(SearchPlace);
		gpsManager = new GPSManager();
		gpsManager.startListening(getApplicationContext());
	    gpsManager.setGPSCallback(RemindmeTaskEditor.this);
	    
//	    map = ((MapFragment) getFragmentManager()
//				 .findFragmentById(R.id.map)).getMap();
	    
	    map = ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	    main_scrollview = (ScrollView) findViewById(R.id.main_scrollview);
 
       ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new WorkaroundMapFragment.OnTouchListener() {
          @Override
          public void onTouch() {
        	  main_scrollview.requestDisallowInterceptTouchEvent(true);
          }
       	});
		map.setMyLocationEnabled(true);
		
		LatLng nowLoacation = new LatLng(23.6978, 120.961);

        map.setMyLocationEnabled(true);
       
        map.clear();
        
        map.moveCamera((CameraUpdateFactory.newLatLngZoom(nowLoacation,map.getMinZoomLevel()+7)));

        map.addMarker(new MarkerOptions()
                .title("愛台灣啦!!")
                .position(nowLoacation));
        
        map.setOnCameraChangeListener(listener);
>>>>>>> merageGps
		// 取得Intent
		final Intent intent = getIntent();
		// 設定Uri
		if (intent.getData() == null) {
			intent.setData(CommonUtils.CONTENT_URI);
		}

		/*
		 * //String[] ops={"旅行","交易","購物","提醒","特急"};
		 * 
		 * // spinner spinnerTag=(Spinner)findViewById(R.id.spinnerTag);
		 * ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(this,
		 * android.R.layout.simple_spinner_item,ops);
		 * spinnerTag.setAdapter(spinnerAdapter); spinnerTag.setPrompt("選擇類型");
		 */

		// 標題輸入欄位
		tittlEditText = (EditText) findViewById(R.id.editTextTittle);
		tittlEditText.setHint("標題");
		tittlEditText.setHintTextColor(R.color.background_window);

		// 取得Calendar實例
		final Calendar c = Calendar.getInstance();

		// 取得目前日期、時間
		mYear = c.get(Calendar.YEAR);
		mMonth = (c.get(Calendar.MONTH)) + 1;
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);

		// 取得ListView
		listView = (ListView) (findViewById(R.id.listView1));
		// 實例化LayoutInflater
		li = getLayoutInflater();
		// 設定ListView Adapter
		try {
			listView.setAdapter(new ViewAdapter());
		} catch (Exception e) {
			// TODO: handle exception
		}
		// 可多選
		listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

		// 回應列表單擊事件
		listView.setOnItemClickListener(ViewAdapterClickListener);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 初始化列表
		init(getIntent());
	}

	// ListView Adatper，該類別實作列表的每一項透過自定視圖實現
	static class ViewAdapter extends BaseAdapter {
		// 列表內容
		String[] strs = { "截止日", "提醒時間", "備註", "地點" };

		// 取得列表數量
		// @Override
		@Override
		public int getCount() {
			return strs.length;
		}

		// 取得列表項目
		// @Override
		@Override
		public Object getItem(int position) {
			return position;
		}

		// 返回列表ID
		// @Override
		@Override
		public long getItemId(int position) {
			return position;
		}

		// 取得目前列表項目視圖
		// @Override
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 自定示圖layout
			View textView = li.inflate(
					R.layout.activity_event_editor_parts_textview, null);
			// View editView =
			// li.inflate(R.layout.activity_event_editor_parts_editview, null);
			switch (position) {
			// 是否開啟該筆備忘錄
			/*
			 * case 0: ctv1 = (CheckedTextView) li .inflate(
			 * android.R.layout.simple_list_item_multiple_choice, null);
			 * ctv1.setText(strs[position]); if (on_off == 0) {
			 * ctv1.setChecked(false); } else { ctv1.setChecked(true); } return
			 * ctv1;
			 */
			// 提醒日期
			case 0:
				// datePicker=(EditText)v.findViewById(R.id.editTextbox);
				// datePicker.setHint("輕觸以選擇日期");
				// datePicker.setHintTextColor(R.color.background_window);
				// datePicker.setText(mYear + "/" + mMonth + "/" + mDay);

				dateTittle = (TextView) textView.findViewById(R.id.name);
				dateDesc = (TextView) textView.findViewById(R.id.desc);
				dateTittle.setText(strs[position]);
				dateDesc.setText(mYear + "/" + mMonth + "/" + mDay);
				return textView;
				// 提醒時間
			case 1:
				// timePicker=(EditText)v.findViewById(R.id.editTextbox);
				// timePicker.setHint("輕觸以選擇時間");
				// timePicker.setHintTextColor(R.color.background_window);
				// timePicker.setText(mHour + ":" + mMinute);
				timeTittle = (TextView) textView.findViewById(R.id.name);
				timeDesc = (TextView) textView.findViewById(R.id.desc);
				timeTittle.setText(strs[position]);
				timeDesc.setText(mHour + ":" + mMinute);
				return textView;
				// 提醒內容
			case 2:
				// contentBox=(EditText)
				// editView.findViewById(R.id.editTextbox);
				// contentBox.setHint("輕觸以輸入內容");
				// contentBox.setHintTextColor(R.color.background_window);
				// contentBox.setText(content);

				contentTittle = (TextView) textView.findViewById(R.id.name);
				contentDesc = (TextView) textView.findViewById(R.id.desc);
				contentTittle.setText(strs[position]);
				contentDesc.setText(content);

				contentDesc.setTextColor(Color.GRAY);
				return textView;
				// 地點選擇與輸入
			case 3:
				/*
				 * locationBox=(EditText)
				 * editView.findViewById(R.id.editTextbox);
				 * locationBox.setHint("輕觸以輸入地點");
				 * locationBox.setHintTextColor(R.color.background_window);
				 * locationBox.setText(locationName); locationTittle =
				 * (TextView) editView.findViewById(R.id.name);
				 * locationTittle.setText(strs[position]);
				 */
				locationTittle = (TextView) textView.findViewById(R.id.name);
				locationDesc = (TextView) textView.findViewById(R.id.desc);
				locationTittle.setText(strs[position]);

				locationDesc.setText(locationName);
				contentDesc.setTextColor(Color.GRAY);

				return textView;
				// 是否開啟提醒
				/*
				 * case 5: ctv2 = (CheckedTextView) li .inflate(
				 * android.R.layout.simple_list_item_multiple_choice, null);
				 * ctv2.setText(strs[position]);
				 * 
				 * if (alarm == 0) { ctv2.setChecked(false); } else {
				 * ctv2.setChecked(true); } return ctv2;
				 */
			default:
				break;
			}

			return null;
		}
	}

	private OnItemClickListener ViewAdapterClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> av, View v, int position, long id) {

			switch (position) {
			// 設定是否開啟提醒
			/*
			 * case 0:
			 * 
			 * ctv1 = (CheckedTextView) v; if (ctv1.isChecked()) { on_off = 0; }
			 * else { on_off = 1; }
			 * 
			 * break;
			 */
			// 設定提醒日期
			case 0:
				showDialog(DATE_DIALOG_ID);
				break;
			// 設定提醒時間
			case 1:
				showDialog(TIME_DIALOG_ID);
				break;
			// 設定提醒內容
			case 2:
				showDialog1("請輸入內容：", "內容", position);
				break;

			// 設定是否開啟語音提醒
			case 3:
				/*
				 * ctv2 = (CheckedTextView) v; if (ctv2.isChecked()) { alarm =
				 * 0; setAlarm(false); } else { alarm = 1; setAlarm(true); }
				 */

				showDialog1("請輸入地點：", "地點", position);
				break;

			default:
				break;
			}
		}

	};

	// 顯示對話方塊
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		// 顯示日期對話方塊
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear,
					mMonth - 1, mDay);
			// 顯示時間對話方塊
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					false);
		}
		return null;
	}

	// 設定通知提示
	private void setAlarm(boolean flag) {
		final String BC_ACTION = "com.amaker.ch17.TaskReceiver";
		// 取得AlarmManager實例
		final AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		// 實例化Intent
		Intent intent = new Intent();
		// 設定Intent action屬性
		intent.setAction(BC_ACTION);
		intent.putExtra("msg", content);
		// 實例化PendingIntent
		final PendingIntent pi = PendingIntent.getBroadcast(
				getApplicationContext(), 0, intent, 0);
		// 取得系統時間
		final long time1 = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.set(mYear, mMonth, mDay, mHour, mMinute);
		long time2 = c.getTimeInMillis();
		if (flag && (time2 - time1) > 0 && on_off == 1) {
			am.set(AlarmManager.RTC_WAKEUP, time2, pi);
		} else {
			am.cancel(pi);
		}
	}

	/*
	 * 設定提示日期對話方塊
	 */
	private void showDialog1(String msg, String tittle, int target) {
		View v = li
				.inflate(R.layout.activity_event_editor_parts_editview, null);
		final TextView editTextTittle = (TextView) v.findViewById(R.id.name);
		final EditText editTextbox = (EditText) v
				.findViewById(R.id.editTextbox);
		editTextTittle.setText(tittle + target);

		switch (target) {
		case 2:
			switcher = content;
			break;
		case 3:
			switcher = locationName;
		default:
			break;
		}

		editTextbox.setText(switcher);

		new AlertDialog.Builder(this).setView(v).setMessage(msg)
				.setCancelable(false)
				.setPositiveButton("確定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						content = editTextbox.getText().toString();

						contentDesc.setText(switcher);
						locationDesc.setText(switcher);
					}
				}).show();

	}

	// 時間選擇對話方塊
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			timeDesc.setText(mHour + ":" + mMinute);
		}
	};
	// 日期選擇對話方塊
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			dateDesc.setText(mYear + "/" + mMonth + "/" + mDay);
		}
	};

	// 儲存或修改備忘錄資訊
	@Override
	protected void onPause() {
		super.onPause();
		locationName = null;
		content = null;
	};

	// 儲存或修改備忘錄資訊
	private boolean saveOrUpdate() {
		try {
			Date curDate = new Date(System.currentTimeMillis());

			ContentValues values = new ContentValues();

			values.clear();

			// 存入標題
			values.put(RemindmeTaskCursor.KeyColumns.Tittle, tittlEditText
					.getText().toString());
			// 存入日期
			values.put(RemindmeTaskCursor.KeyColumns.StartDate,
					curDate.toString());
			values.put(RemindmeTaskCursor.KeyColumns.EndDate, dateDesc
					.getText().toString());
			// save the selected value of time
			values.put(RemindmeTaskCursor.KeyColumns.StartTime,
					curDate.toString());
			values.put(RemindmeTaskCursor.KeyColumns.EndTime, timeDesc
					.getText().toString());
			// save contents
			values.put(RemindmeTaskCursor.KeyColumns.CONTENT, contentDesc
					.getText().toString());
			// save the name string of location
			values.put(RemindmeTaskCursor.KeyColumns.LocationName, locationDesc
					.getText().toString());
			// save the value of loaction picker
			/*
			 * values.put(RemindmeTasks.EndDate, dateDesc.getText().toString());
			 * values.put(RemindmeTasks.StartTime,
			 * timeDesc.getText().toString()); values.put(RemindmeTasks.EndTime,
			 * timeDesc.getText().toString());
			 * 
			 * values.put(RemindmeTasks.Is_Alarm_ON, ctv1.isChecked() ? 1 : 0);
			 * values.put(RemindmeTasks.Is_Hide_ON, ctv2.isChecked() ? 1 : 0);
			 */
			// 修改
			if (id1 != 0) {
				Uri uri = ContentUris.withAppendedId(CommonUtils.CONTENT_URI,
						id1);
				getContentResolver().update(uri, values, null, null);
				Toast.makeText(this, "事項更新成功！" + curDate.toString(),
						Toast.LENGTH_SHORT).show();
				// 儲存
			} else {
				Uri uri = CommonUtils.CONTENT_URI;
				getContentResolver().insert(uri, values);
				Toast.makeText(this, "新事項已經儲存" + curDate.toString(),
						Toast.LENGTH_SHORT).show();
			}
			setAlarm(true);
			return true;
		} catch (Exception e) {
			Toast.makeText(getApplication(), "儲存出錯！", Toast.LENGTH_SHORT)
					.show();
			return false;
		}

	}

	// This is the action bar menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 抓取editor_activity_actionbar.xml內容
		getMenuInflater().inflate(R.menu.editor_activity_actionbar, menu);

		// 啟用actionbar返回首頁箭頭
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("建立待辦事項");

		// actionAdd
		MenuItem actionAdd = menu.findItem(R.id.action_add);
		actionAdd.setOnMenuItemClickListener(btnActionAddClick);

		// actionCancel
		MenuItem actionCancel = menu.findItem(R.id.action_cancel);
		actionCancel.setOnMenuItemClickListener(btnActionCancelClick);

		return true;
	}

	// actionbar箭頭返回首頁動作
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * 
	 */
	private MenuItem.OnMenuItemClickListener btnActionAddClick = new MenuItem.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			saveOrUpdate();
			finish();
			return true;
		}

	};

	/*
	 * 
	 */
	private MenuItem.OnMenuItemClickListener btnActionCancelClick = new MenuItem.OnMenuItemClickListener() {

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// TODO Auto-generated method stub

			// Intent EventEditor = new Intent();
			// EventEditor.setClass(getApplication(),RemindmeTaskEditorActivity.class);
			// EventEditor.setClass(getApplication(), IconRequest.class);
			// startActivity(EventEditor);

			// saveOrUpdate();
			finish();

			return false;
		}

	};

<<<<<<< HEAD
=======
	@Override
	public void onGPSUpdate(Location location) {
		// TODO Auto-generated method stub
		Double Longitude = location.getLongitude();
		//緯度
		Double Latitude =  location.getLatitude();
		
		//textView1.setText("經緯度:"+Latitude+","+Longitude);
		//拿到經緯度後馬上關閉
		gpsManager.stopListening();
        gpsManager.setGPSCallback(null);
        gpsManager = null;
        
        LatLng nowLoacation = new LatLng(Latitude, Longitude);

        map.setMyLocationEnabled(true);
       
        map.clear();
        
        map.animateCamera((CameraUpdateFactory.newLatLngZoom(nowLoacation,map.getMaxZoomLevel()-4)));

        map.addMarker(new MarkerOptions()
                .title("目前位置")
                .position(nowLoacation));
        
        GeocodingAPI LoacationAddress = new GeocodingAPI(getApplicationContext(),Latitude+","+Longitude);
       // textView2.setText(textView2.getText()+" "+LoacationAddress.GeocodingApiAddressGet());
	}
	private Button.OnClickListener SearchPlace = new Button.OnClickListener(){
		public void onClick(View v){
			//宣告GPSManager

		    switch (v.getId()) {
			case R.id.OK:
		        //textView2.setText(textView2.getText()+"\n"+LoacationAddress.GeocodingApiAddressGet());  //獲取地址
		        //textView2.setText(textView2.getText()+"\n"+LoacationAddress.GeocodingApiLatLngGet());  //獲取經緯度
				GeocodingAPI LoacationAddress = new GeocodingAPI(getApplicationContext(),map.getCameraPosition().target.latitude+","+map.getCameraPosition().target.longitude);
				Longitude = LoacationAddress.GeocodingApiLatLngGet().longitude;
				Latitude = LoacationAddress.GeocodingApiLatLngGet().latitude;
				locationName = LoacationAddress.GeocodingApiAddressGet();
				Toast.makeText(getApplicationContext(), "獲取經緯度"+map.getCameraPosition().target.latitude+","+map.getCameraPosition().target.longitude+"\n地址:"+locationName, Toast.LENGTH_SHORT).show();

			break;
			case R.id.Search:
				//textView2.setText(map.getMyLocation().toString());  //可用網路抓到GPS位置
				if(!SearchText.getText().toString().equals("")){
					GeocodingAPI LoacationAddress2 = new GeocodingAPI(getApplicationContext(),SearchText.getText().toString());
					//textView2.setText("");
					//locationName=LoacationAddress2.GeocodingApiAddressGet();
			        //textView2.setText(textView2.getText()+"\n"+Address);
			        LatLng SearchLocation = LoacationAddress2.GeocodingApiLatLngGet();
			        //textView2.setText(textView2.getText()+"\n"+SearchLocation);
			        map.animateCamera((CameraUpdateFactory.newLatLngZoom(SearchLocation,map.getMaxZoomLevel()-4)));
			        map.addMarker(new MarkerOptions()
	                .title("搜尋的位置")
	                .snippet(locationName)
	                .position(SearchLocation));
					}
			break;

			default:
				break;
			}
		}
	};
	
	private GoogleMap.OnCameraChangeListener listener = new GoogleMap.OnCameraChangeListener() {
		
		@Override
		public void onCameraChange(CameraPosition position) {
			// TODO Auto-generated method stub
			map.clear();
			LatLng now = new LatLng(position.target.latitude, position.target.longitude);
			map.addMarker(new MarkerOptions()
            .title("目的地")
            .position(now));
		}
	};
	

>>>>>>> merageGps
}

// * CLASS JUST FOR THE CUSTOM ALERT DIALOG
class CustomAlertDialog extends AlertDialog {
	public CustomAlertDialog(Context context) {
		super(context);
	}
}
