package me.iamcxa.remindme;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author cxa
 * 
 */
public class RemindmePreference extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preference);

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		RemindmePreferenceFragment fragment11 = new RemindmePreferenceFragment();
		fragmentTransaction.replace(android.R.id.content, fragment11);
		fragmentTransaction.addToBackStack(null);
		// fragmentTransaction.setTransition(4097);
		fragmentTransaction.commit();
	}

	// This is the action bar menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.share_menu, menu);
		// getMenuInflater().inflate(R.menu.actionbar, menu);
		// MenuItem item = menu.findItem(R.id.menu_item_share);
		// getMenuInflater().inflate(R.menu.editor_activity_actionbar, menu);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
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
}