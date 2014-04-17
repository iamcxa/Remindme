package me.iamcxa.remindme;

import me.iamcxa.remindme.service.TaskSortingService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author iamcxa 定時提醒廣播
 */
public class RemindmeBootReciver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		intent.setClass(context, TaskSortingService.class);
		
		CommonUtils.debugMsg(0,"準備啟動TaskSortingService");
		try {
			context.startActivity(intent);

			CommonUtils.debugMsg(0,"TaskSortingService 啟動完成");
		} catch (Exception e) {
			// TODO: handle exception
			CommonUtils.debugMsg(0,"啟動TaskSortingService失敗！error="+e.toString());
		}
	}
}