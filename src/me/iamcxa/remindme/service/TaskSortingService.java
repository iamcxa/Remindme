/**
 * 
 */
package me.iamcxa.remindme.service;

import com.google.android.gms.drive.internal.r;

import me.iamcxa.remindme.CommonUtils;
import me.iamcxa.remindme.R;
import me.iamcxa.remindme.RemindmeMainActivity;
import me.iamcxa.remindme.provider.GPSCallback;
import android.R.integer;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.IBinder;

/**
 * @author cxa
 * 
 */
public class TaskSortingService extends Service implements GPSCallback {

	private static final int notifyID = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * me.iamcxa.remindme.provider.GPSCallback#onGPSUpdate(android.location.
	 * Location)
	 */
	@Override
	public void onGPSUpdate(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreate() {
		super.onCreate();
		CommonUtils.debugMsg(0,"service pre-start");
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager nNotificationManager = (NotificationManager) getSystemService(ns);
		CharSequence tickertextr = "remindme is running";
		long when = System.currentTimeMillis();
		Intent intent = new Intent(this, RemindmeMainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, 0);
		Bitmap bm = BitmapFactory.decodeResource(getResources(),
				R.drawable.empty_flag);
		Notification noti = new Notification.Builder(getApplicationContext())
				.setContentTitle("remindme Task shorting service")
				.setContentText("remindme is running")
				.setSmallIcon(R.drawable.outline_star_act).setLargeIcon(bm)
				.setNumber(notifyID).setSubText("subtext").setWhen(when)
				.setContentIntent(contentIntent).build();

		
		try {
			nNotificationManager.notify(notifyID,noti);
			CommonUtils.debugMsg(0,"service started");
		} catch (Exception e) {
			CommonUtils.debugMsg(0,"service start error="+e.toString()
					);
		}

	}

}
