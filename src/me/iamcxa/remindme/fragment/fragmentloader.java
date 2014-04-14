/**
 * 
 */
package me.iamcxa.remindme.fragment;

import me.iamcxa.remindme.R;
import me.iamcxa.remindme.cardset.BaseFragment;
import me.iamcxa.remindme.cardset.ListCursorCardFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @author cxa
 * 
 */
public class fragmentloader extends Fragment {

	private ListView mDrawerList;
	private DrawerLayout mDrawer;
	private int mSelectedFragment;
	private BaseFragment mBaseFragment;
	private int mCurrentTitle = R.string.app_name;

	/*
	 * ***********************************************************************
	 * *
	 * onCreateView°Ï¬q * *
	 * ***********************************************************************
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_main, container, false);

		mBaseFragment = new ListCursorCardFragment();
		openFragment(mBaseFragment);
		// Highlight the selected item, update the title, and close the drawer
		// update selected item and title, then close the drawer
		/*
		 * mDrawerList.setItemChecked(position, true); mBaseFragment =
		 * selectFragment(position); mSelectedFragment = position;
		 * 
		 * if (mBaseFragment != null) openFragment(mBaseFragment);
		 * mDrawer.closeDrawer(mDrawerList);
		 * 
		 * 
		 * FragmentTabHost tabHost =
		 * (FragmentTabHost)findViewById(android.R.id.tabhost);
		 * tabHost.setup(this, getChildFragmentManager(), R.id.realtabcontent);
		 * //1 tabHost.addTab(tabHost.newTabSpec("Apple")
		 * .setIndicator("Apple"), ListCursorCardFragment.class, null);
		 */

		return view;
	}

	private void openFragment(BaseFragment mBaseFragment2) {
		if (mBaseFragment2 != null) {
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();

			fragmentTransaction.replace(R.id.fragment_main, mBaseFragment2);
			// fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
			// if (baseFragment.getTitleResourceId()>0)
			// mCurrentTitle = baseFragment.getTitleResourceId();

		}
	}

	private BaseFragment selectFragment(int position) {
		BaseFragment baseFragment = null;

		switch (position) {
		case 0:
			baseFragment = new ListCursorCardFragment();
		default:
			break;
		}

		return baseFragment;
	}

}
