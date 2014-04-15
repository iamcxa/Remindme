/*
 * 
 */
package me.iamcxa.remindme;

import java.util.Locale;

import me.iamcxa.remindme.cardfragment.CardFragmentLoader;
import me.iamcxa.remindme.cardfragment.CopyOfListCursorCardFragment;
import me.iamcxa.remindme.cardfragment.ListCursorCardFragment;
import me.iamcxa.remindme.cardfragment.ListviewFragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;

/**
 * @author cxa Main Activity
 */
public class RemindmeMainActivity extends FragmentActivity {
	/**********************/
	/** Variables LOCALE **/
	/**********************/
	// Used in savedInstanceState
	private static String BUNDLE_SELECTEDFRAGMENT = null;
	private static PagerSlidingTabStrip tabs;
	private static ViewPager pager;
	public static MyPagerAdapter adapter;
	private static ShareActionProvider mShareActionProvider;
	// private Intent mShareIntent;
	// private static SearchView mSearchView;
	ProgressBar pBar;
	ProgressDialog psDialog;

	/**********************/
	/** onCreate LOCALE **/
	/**********************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 設定：layout視圖
		setContentView(R.layout.activity_main);
		CommonUtils.debugMsg(1, "0 onCreate");
		pBar = (ProgressBar) findViewById(R.id.progressBar1);
		pBar.setVisibility(View.VISIBLE);
		psDialog = ProgressDialog.show(this, "", "......");

		new Thread(new Runnable() {
			@Override
			public void run() {
				CommonUtils.debugMsg(1, "0 pre-setViewComponent");
				// 設定：頁面元件
				setViewComponent();
			}
		}).start();

		if (savedInstanceState != null) {
			// setFragment();
			setLodingOff();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(BUNDLE_SELECTEDFRAGMENT, pager.getCurrentItem());
		CommonUtils.debugMsg(0,""+pager.getCurrentItem());
	}

	/**********************/
	/** onCreateOptionsMenu **/
	/**********************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 由main_activity_actionbar.xml讀取按鈕資訊
		getMenuInflater().inflate(R.menu.main_activity_actionbar, menu);

		// 定義：搜尋按鈕
		 MenuItem actionSearch = menu.findItem(R.id.action_search);
		 actionSearch.setVisible(false);
		// 設定：點擊接收器
		// mSearchView = (SearchView) actionSearch.getActionView();
		// setSearchView(actionSearch);

		// 定義：新增按鈕
		MenuItem actionAdd = menu.findItem(R.id.action_add);
		// 設定：點擊接收器
		actionAdd.setOnMenuItemClickListener(btnActionAddClick);

		// 定義：重整按鈕
		MenuItem actionRefresh = menu.findItem(R.id.action_refresh);
		// 設定：點擊接收器
		actionRefresh.setOnMenuItemClickListener(btnRefreshAddClick);

		/*
		 * // 定義：分享按鈕 MenuItem actionShare = menu.findItem(R.id.action_share);
		 * 設定：play上之app連結/分享給朋友的顯示字串 String playStoreLink =
		 * " https://play.google.com/store/apps/details?id=" + getPackageName();
		 * String yourShareText = getString(R.string.share_this) +
		 * playStoreLink; // 設定：分享intent Intent shareIntent =
		 * ShareCompat.IntentBuilder.from(this)
		 * .setType("text/plain").setText(yourShareText).getIntent(); //
		 * 設定：點擊提供者 mShareActionProvider = (ShareActionProvider) actionShare
		 * .getActionProvider();
		 * mShareActionProvider.setShareIntent(shareIntent);
		 */

		// 定義：設置按鈕
		MenuItem actionPref = menu.findItem(R.id.action_settings);
		// 設定：點擊接收器
		actionPref.setOnMenuItemClickListener(menuActionPrefClick);

		return super.onCreateOptionsMenu(menu);
	}

	/**********************/
	/** setViewComponent **/
	/**********************/
	private void setViewComponent() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CommonUtils.debugMsg(1,"1 setViewComponent");
				// 定義：tabs
				tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
				pager = (ViewPager) findViewById(R.id.pager);
				adapter = new MyPagerAdapter(getSupportFragmentManager());

				// 設定：頁面margin
				final int pageMargin = (int) TypedValue.applyDimension(
						TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
								.getDisplayMetrics());
				pager.setPageMargin(pageMargin);
				// 確保不會頁面不會被意外清除
				pager.setOffscreenPageLimit(3);

				// 設定：tab與page之adapter
				pager.setAdapter(adapter);
				tabs.setViewPager(pager);
			}
		}).start();

	}

	/**********************/
	/** setFragment **/
	/**********************/
	// To ensure the target frame will show the right fragment.
	private void setFragment(final int FragmentPosition) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				CommonUtils.debugMsg(1, "2 setFragment");
				android.app.FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				switch (FragmentPosition) {
				case 0:
					ListCursorCardFragment cardview = new ListCursorCardFragment();
					fragmentTransaction.replace(R.id.fragment_main, cardview);
					break;
				case 1:
					ListCursorCardFragment cardview1 = new ListCursorCardFragment();
					fragmentTransaction.replace(R.id.fragment_main, cardview1);
				default:
					break;
				}

				fragmentTransaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				fragmentTransaction.commit();
				setLodingOff();			
			}
		}).start();
		

	}

	/**********************/
	/** OsetupSearchView **/
	/**********************/
	private void setSearchView(MenuItem searchItem) {
		// TODO Auto-generated method stub

	}
	
	/**********************/
	/** setLodingOff **/
	/**********************/
	private void setLodingOff() {
		psDialog.dismiss();
		pBar.setVisibility(View.GONE);
	}
	/**********************/
	/** btnActionAddClick **/
	/**********************/
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

	/************************/
	/** btnRefreshAddClick **/
	/************************/
	private MenuItem.OnMenuItemClickListener btnRefreshAddClick = new MenuItem.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			Toast.makeText(getApplication(), item.getTitle(),
					Toast.LENGTH_SHORT).show();
			// Intent intent = new Intent();
			// intent.setClass(getApplication(), testcard.class);
			// startActivity(intent);
			// setFragment();
			return false;
		}
	};

	/**********************/
	/** menuActionPrefClick **/
	/**********************/
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

	/**************************/
	/** Class MyPagerAdapter **/
	/**************************/
	public class MyPagerAdapter extends FragmentPagerAdapter {
		// 定義各tab名稱
		private final int[] TITLES = { R.string.tab1, R.string.tab2,
				R.string.tab3 };

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
			case 2:
				return getString(R.string.tab3).toUpperCase(l);
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
				f = new CardFragmentLoader();
				setFragment(0);
				break;
			case 1:
				f = new CardFragmentLoader();
				//setFragment(1);
				break;
			case 2:
				f = new ListviewFragment();
				break;
			}
			return f;
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}
	};
}