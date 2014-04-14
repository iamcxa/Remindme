/*
 * 
 */
package me.iamcxa.remindme;

import java.security.PublicKey;
import java.util.Locale;

import me.iamcxa.remindme.cardset.BaseFragment;
import me.iamcxa.remindme.cardset.ListCursorCardFragment;
import me.iamcxa.remindme.fragment.CardViewFragment;
import me.iamcxa.remindme.fragment.ListviewFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;

/**
 * @author cxa Main Activity
 */
public class RemindmeMainActivity extends FragmentActivity {

	/*
	 * ********************************************************************************************** *
	 * *
	 * Variables * *
	 * ************************************************************
	 * **********************************
	 */
    //Used in savedInstanceState
    private static String BUNDLE_SELECTEDFRAGMENT = "BDL_SELFRG";
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	public MyPagerAdapter adapter;
	private ShareActionProvider mShareActionProvider;
	// private Intent mShareIntent;
	private SearchView mSearchView;

	/*
	 * ********************************************************************************************** *
	 * *
	 * onCreate 啟動區段 * *
	 * ********************************************************
	 * **************************************
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 設定：頁面元件
		setViewComponent();

		// -----------------------------------------------------------------
		// BaseFragment baseFragment = null;
		if (savedInstanceState != null) {
			replaceFragment();
		}

		// -----------------------------------------------------------------

	}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_SELECTEDFRAGMENT,pager.getCurrentItem());
		Toast.makeText(getApplication(), BUNDLE_SELECTEDFRAGMENT+","+pager.getCurrentItem()+adapter.toString(), 200).show();
    }
	/*
	 * ********************************************************************************************** *
	 * *
	 * setViewComponent 區段, 設定：頁面元件 * *
	 * *****************************************
	 * *****************************************************
	 */
	private void setViewComponent() {
		// 設定：layout視圖
		setContentView(R.layout.activity_main);

		// 定義：tabs
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());

		// 設定：頁面margin
		final int pageMargin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
						.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		// 設定：tab與page之adapter
		pager.setAdapter(adapter);
		tabs.setViewPager(pager);
	}

	/*
	 * ********************************************************************************************** *
	 * *
	 * onCreateOptionsMenu 區段, 設定：Action Bar上之按鈕 * *
	 * ****************************
	 * ******************************************************************
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 由main_activity_actionbar.xml讀取按鈕資訊
		getMenuInflater().inflate(R.menu.main_activity_actionbar, menu);

		// 定義：搜尋按鈕
		MenuItem actionSearch = menu.findItem(R.id.action_search);
		// 設定：點擊接收器
		mSearchView = (SearchView) actionSearch.getActionView();
		setupSearchView(actionSearch);

		// 定義：新增按鈕
		MenuItem actionAdd = menu.findItem(R.id.action_add);
		// 設定：點擊接收器
		actionAdd.setOnMenuItemClickListener(btnActionAddClick);

		// 定義：重整按鈕
		MenuItem actionRefresh = menu.findItem(R.id.action_refresh);
		// 設定：點擊接收器
		actionRefresh.setOnMenuItemClickListener(btnRefreshAddClick);

		// 定義：分享按鈕
		MenuItem actionShare = menu.findItem(R.id.action_share);
		// 設定：play上之app連結/分享給朋友的顯示字串
		String playStoreLink = " https://play.google.com/store/apps/details?id="
				+ getPackageName();
		String yourShareText = getString(R.string.share_this) + playStoreLink;
		// 設定：分享intent
		Intent shareIntent = ShareCompat.IntentBuilder.from(this)
				.setType("text/plain").setText(yourShareText).getIntent();
		// 設定：點擊提供者
		mShareActionProvider = (ShareActionProvider) actionShare
				.getActionProvider();
		mShareActionProvider.setShareIntent(shareIntent);

		// 定義：設置按鈕
		MenuItem actionPref = menu.findItem(R.id.action_settings);
		// 設定：點擊接收器
		actionPref.setOnMenuItemClickListener(menuActionPrefClick);

		return super.onCreateOptionsMenu(menu);
	}

	/*
	 * ********************************************************************************************** *
	 * *
	 * OnMenuItemClickListener:btnActionAddClick 區段, 設定ActionBar上新增按鈕動作 * *
	 * *****
	 * *********************************************************************
	 * ********************
	 */
	private MenuItem.OnMenuItemClickListener btnActionAddClick = new MenuItem.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			Toast.makeText(getApplication(), item.getTitle(),
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.setClass(getApplication(), RemindmeTaskEditor.class);
			startActivity(intent);
			return false;
		}
	};

	private MenuItem.OnMenuItemClickListener btnRefreshAddClick = new MenuItem.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			Toast.makeText(getApplication(), item.getTitle(),
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.setClass(getApplication(), testcard.class);
			startActivity(intent);
			return false;
		}
	};

	/*
	 * ********************************************************************************************** *
	 * *
	 * OnMenuItemClickListener:menuActionPrefClick區段, 設定ActionBar上設定按鈕動作 * *
	 * ****
	 * **********************************************************************
	 * ********************
	 */
	private MenuItem.OnMenuItemClickListener menuActionPrefClick = new MenuItem.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// Display the fragment as the main content.

			Toast.makeText(getApplication(), item.getTitle(),
					Toast.LENGTH_SHORT).show();

			Intent intent = new Intent();
			intent.setClass(getApplication(), RemindmePreference.class);
			startActivity(intent);

			return false;
		}
	};

	/*
	 * ********************************************************************************************** *
	 * *
	 * OsetupSearchView區段, 設定ActionBar上搜尋按鈕啟動後之動作 * *
	 * ***************************
	 * *******************************************************************
	 */
	private void setupSearchView(MenuItem searchItem) {
		// TODO Auto-generated method stub

	}

	private void replaceFragment() {
		android.app.FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		ListCursorCardFragment fragment11 = new ListCursorCardFragment();
		fragmentTransaction.replace(R.id.fragment_main, fragment11);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	/*
	 * ********************************************************************************************** *
	 * *
	 * MyPagerAdapter類別, 定義activiry上之tab/pager * *
	 * ******************************
	 * ****************************************************************
	 */
	public class MyPagerAdapter extends FragmentPagerAdapter {
		// 定義各tab名稱
		private final int[] TITLES = { R.string.tab1, R.string.tab2 };

		// private final int[] TITLES = { R.string.tab1, R.string.tab2,
		// R.string.tab3, R.string.tab4 };
		// private final int[] TITLES = { R.string.tab1, R.string.tab2,
		// R.string.tab3 ,655};

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		// This displays tab names and how to switch, if you remove a tab,
		// remove its case below but make sure to keep the order (0,1,2)
		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.tab1).toUpperCase(l);
			case 1:
				return getString(R.string.tab2).toUpperCase(l);
				/*
				 * case 2: return getString(R.string.tab3).toUpperCase(l); case
				 * 3: return getString(R.string.tab4).toUpperCase(l);
				 */
			}
			return null;
		}

		// These are the tabs in the main display, if you remove a tab
		// (fragment) you must remove it from below. also, ensure you keep the
		// cases in order or it will not know what to do
		@Override
		public Fragment getItem(int position) {
			Fragment f = new Fragment();
			switch (position) {
			case 0:
				f = new CardViewFragment();
				replaceFragment();
				break;

			case 1:
				f = new ListviewFragment();
				break;

			/*
			 * case 2: f= new DistanceFragment(); break; case 3: f= new
			 * OverallFragment(); break;
			 */
			}
			return f;
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}
		

		public int getPosion(Object object) {
			return getItemPosition(object);
			
		}


	}

}