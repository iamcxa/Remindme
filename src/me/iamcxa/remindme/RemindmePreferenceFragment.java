package me.iamcxa.remindme;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * @author cxa
 * 
 */
public class RemindmePreferenceFragment extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);
	}

}
