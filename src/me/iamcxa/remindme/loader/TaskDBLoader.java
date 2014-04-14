/**
 * 
 */
package me.iamcxa.remindme.loader;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.AsyncTaskLoader;

/**
 * @author cxa
 * 
 */
public class TaskDBLoader extends AsyncTaskLoader<TaskDBEntry> {

	private static final String TAG = "ADP_AppListLoader";
	private static final boolean DEBUG = true;
	final PackageManager mPm;

	// We hold a reference to the Loader's data here.
	private List<TaskDBEntry> mApps;

	public TaskDBLoader(Context ctx) {
		// Loaders may be used across multiple Activitys (assuming they aren't
		// bound to the LoaderManager), so NEVER hold a reference to the context
		// directly. Doing so will cause you to leak an entire Activity's
		// context.
		// The superclass constructor will store a reference to the Application
		// Context instead, and can be retrieved with a call to getContext().
		super(ctx);
		mPm = getContext().getPackageManager();
	}

	/****************************************************/
	/** (1) A task that performs the asynchronous load **/
	/****************************************************/

	/**
	 * This method is called on a background thread and generates a List of
	 * {@link AppEntry} objects. Each entry corresponds to a single installed
	 * application on the device.
	 */
	@Override
	public TaskDBEntry loadInBackground() {
		// TODO Auto-generated method stub
		return null;
	}

}
