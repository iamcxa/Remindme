/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package me.iamcxa.remindme.cardfragment;

import java.util.Date;

import com.google.android.gms.internal.el;

import me.iamcxa.remindme.CommonUtils;
import me.iamcxa.remindme.R;
import me.iamcxa.remindme.RemindmeTaskEditor;
import me.iamcxa.remindme.CommonUtils.RemindmeTaskCursor;
import me.iamcxa.remindme.service.TaskSortingService;
import android.R.integer;
import android.R.string;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.Card.OnLongCardClickListener;
import it.gmariotti.cardslib.library.internal.CardCursorAdapter;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * List with Cursor Example
 * 
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ListCursorCardFragmentTime extends BaseFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {

	private MyCursorCardAdapter mAdapter;
	private CardListView mListView;

	public static String[] projection = RemindmeTaskCursor.PROJECTION;
	public static String selection = null;
	public static String sortOrder = CommonUtils.DEFAULT_SORT_ORDER;
	public static String[] selectionArgs;

	/**********************/
	/** Initialization **/
	/**********************/
	private void init() {
		mAdapter = new MyCursorCardAdapter(getActivity());
		mListView = (CardListView) getActivity().findViewById(
				R.id.carddemo_list_cursor);

		if (mListView != null) {
			mListView.setAdapter(mAdapter);
		}

getLoaderManager();
LoaderManager.enableDebugLogging(true);
		// Force start background query to load sessions
		getLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public int getTitleResourceId() {
		return R.string.app_name;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceStat) {

		View root = inflater.inflate(R.layout.card_fragment_list_cursor,
				container, false);
		// mScrollView = (ScrollView) root.findViewById(R.id.card_scrollview);

		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getLoaderManager().initLoader(0, null, this);
		init();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		Loader<Cursor> loader = null;
		loader = new CursorLoader(getActivity(), CommonUtils.CONTENT_URI,
				projection, selection, selectionArgs,
				sortOrder);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (getActivity() == null) {
			return;
		}
		mAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

	/*******************************/
	/** Class MyCursorCardAdapter **/
	/*******************************/
	public class MyCursorCardAdapter extends CardCursorAdapter {

		public MyCursorCardAdapter(Context context) {
			super(context);
		}

		@Override
		protected Card getCardFromCursor(final Cursor cursor) {
			MyCursorCard card = new MyCursorCard(super.getContext());
			setCardFromCursor(card, cursor);

			// Create a CardHeader
			CardHeader header = new CardHeader(getActivity());

			// Set the header title
			header.setTitle(card.mainHeader);
			header.setPopupMenu(R.menu.editor_activity_actionbar,
					new CardHeader.OnClickCardHeaderPopupMenuListener() {
						@Override
						public void onMenuItemClick(BaseCard card, MenuItem item) {
							Toast.makeText(
									getContext(),
									"Click on card=" + card.getId() + " item="
											+ item.getTitle(),
									Toast.LENGTH_SHORT).show();
						}
					});

			// Add Header to card
			card.addCardHeader(header);

			final MyCardThumbnail thumb = new MyCardThumbnail(getActivity());
			thumb.setDrawableResource(card.resourceIdThumb);
			card.addCardThumbnail(thumb);

			// Set onClick listener
			card.addPartialOnClickListener(Card.CLICK_LISTENER_CONTENT_VIEW,
					new Card.OnLongCardClickListener() {
						@Override
						public boolean onLongClick(Card card, View view) {
							Toast.makeText(
									getContext(),
									"Card id=" + card.getId()
											+ "LONG Click on Content Area",
									Toast.LENGTH_SHORT).show();
							return true;
						}
					});

			// Set a clickListener on ContentArea
			card.addPartialOnClickListener(Card.CLICK_LISTENER_CONTENT_VIEW,
					new Card.OnCardClickListener() {
						@Override
						public void onClick(Card card, View view) {
							Toast.makeText(
									getContext(),
									"Card id=" + card.getId()
											+ "Click on Content Area",
									Toast.LENGTH_SHORT).show();
							
							
							int id1 = Integer.parseInt(card.getId());
							String date1 =cursor.getString(2) ;
							String time1 = cursor.getString(2);
							String content = cursor.getString(3);			
							String endTime = cursor
									.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.EndTime);
							String endDate = cursor
									.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.EndDate);
							String LocationName = cursor
									.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.LocationName);

							Bundle b = new Bundle();
							b.putInt("_id", id1);
							b.putString("date1", date1);
							b.putString("time1", time1);
							b.putString("content", content);
							b.putString("LocationName", LocationName);
							b.putString("endDate", endDate);
							b.putString("endTime", endTime);
							
							Intent intent = new Intent();
							intent.setClass(getActivity(), RemindmeTaskEditor.class);
							startActivity(intent);
						}
					});

			// Set a clickListener on Header Area
			card.addPartialOnClickListener(Card.CLICK_LISTENER_HEADER_VIEW,
					new Card.OnCardClickListener() {
						@Override
						public void onClick(Card card, View view) {
							Toast.makeText(
									getActivity(),
									"Card id=" + card.getId()
											+ "Click on Header Area",
									Toast.LENGTH_LONG).show();
						}
					});

			return card;
		}

		private  void setCardFromCursor(MyCursorCard card, Cursor cursor) {
			// 準備常數
			CommonUtils.debugMsg(0, "prepare data from cursor...");
			boolean Extrainfo = cursor
					.isNull(CommonUtils.RemindmeTaskCursor.IndexColumns.other);
			int CID = cursor
					.getInt(CommonUtils.RemindmeTaskCursor.IndexColumns.KEY_ID);
			String dintence = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.Distance);
			String startTime = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.StartTime);
			String endTime = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.EndTime);
			String endDate = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.EndDate);
			String LocationName = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.LocationName);
			String extraInfo = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.other);
			long dayLeft = CommonUtils.getDaysLeft(endDate);
			// int dayLeft = Integer.parseInt("" + dayLeftLong);

			// give a ID.
			CommonUtils.debugMsg(0, "set ID...Card id=" + CID);
			card.setId("" + CID);

			// 卡片標題 - first line
			CommonUtils.debugMsg(0, CID + " set Tittle...");
			card.mainHeader = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.Tittle);

			// 時間日期 - sec line
			CommonUtils.debugMsg(0, CID + " set Date/Time...");
			CommonUtils.debugMsg(0, CID + " dayleft=" + dayLeft);
			if ((180 > dayLeft) && (dayLeft > 14)) {
				card.DateTime = "再 " + (int) Math.floor(dayLeft) / 30
						+ " 個月 - " + endDate;
			} else if ((14 > dayLeft) && (dayLeft > 0)) {
				card.DateTime = "再 " + dayLeft + " 天 - " + endDate;
			} else if ((2 > dayLeft) && (dayLeft > 0)) {
				card.DateTime = "再 " + (int) Math.floor(dayLeft * 24)
						+ "小時後 - " + endDate;
			} else if (dayLeft == 0) {
				card.DateTime = "今天 - " + endDate;
			} else {
				card.DateTime = endDate;
			}

			// 小圖標顯示 - 判斷是否存有地點資訊
			CommonUtils.debugMsg(0, "Location=\"" + LocationName
					+ "\"");
			if ((LocationName.length()) > 1) {
				card.resourceIdThumb = R.drawable.map_marker;
					} else {
				card.resourceIdThumb = R.drawable.tear_of_calendar;
				card.LocationName = null;
			}
			
			// 距離與地點資訊
			CommonUtils.debugMsg(0, "dintence=" + dintence);
			if (dintence == null) {
				card.LocationName = LocationName;
			} else {
				card.LocationName = "距離 " + dintence + " 公里 - "
						+ LocationName;
			}

			// 可展開額外資訊欄位
			CommonUtils.debugMsg(0, "isExtrainfo=" + Extrainfo);
			card.Notifications = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.CalendarID);
			if (!Extrainfo) {
				card.resourceIdThumb = R.drawable.outline_star_act;
				// 額外資訊提示 - 第四行
				

				// This provides a simple (and useless) expand area
				CardExpand expand = new CardExpand(getActivity());
				// Set inner title in Expand Area
				expand.setTitle(getString(R.string.app_name));
				card.addCardExpand(expand);
			}
			card.Notifications = cursor
					.getString(0);

			// 依照權重給予卡片顏色
			if (cursor
					.getInt(CommonUtils.RemindmeTaskCursor.IndexColumns.PriorityWeight) > 200) {
				card.setBackgroundResourceId(R.drawable.demo_card_selector_color5);
			} else if (cursor
					.getInt(CommonUtils.RemindmeTaskCursor.IndexColumns.PriorityWeight) > 100) {
				card.setBackgroundResourceId(R.drawable.demo_card_selector_color3);
			}

		}
	}

	/*
	 * private void removeCard(Card card) {
	 * 
	 * // Use this code to delete items on DB ContentResolver resolver =
	 * getActivity().getContentResolver(); long noDeleted =
	 * resolver.delete(CommonUtils.CONTENT_URI,
	 * CommonUtils.RemindmeTaskCursor.KeyColumns.KEY_ID + " = ? ", new String[]
	 * { card.getId() });
	 * 
	 * // mAdapter.notifyDataSetChanged();
	 * 
	 * }
	 */

	/***********************/
	/** Class MyThumbnail **/
	/***********************/
	// implment the clickable card thumbnail.
	class MyCardThumbnail extends CardThumbnail {

		public MyCardThumbnail(Context context) {
			super(context);
		}

		@Override
		public void setupInnerViewElements(ViewGroup parent, View imageView) {
			ViewToClickToExpand viewToClickToExpand = ViewToClickToExpand
					.builder().highlightView(true).setupView(imageView);
			getParentCard().setViewToClickToExpand(viewToClickToExpand);
		}
	}

	/************************/
	/** Class MyCursorCard **/
	/************************/
	public class MyCursorCard extends Card {

		 String DateTime;
		 String LocationName;
		 String Notifications;
		 String mainHeader;

		int resourceIdThumb;

		public MyCursorCard(Context context) {
			super(context, R.layout.card_cursor_inner_content);
		}

		/**********************/
		/**
		 * @param clickListenerContentView
		 *            /** @param onLongCardClickListener
		 **/
		/**********************/
		public void addPartialOnClickListener(int clickListenerContentView,
				OnLongCardClickListener onLongCardClickListener) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setupInnerViewElements(ViewGroup parent, View view) {
			// Retrieve elements
			TextView mTitleTextView = (TextView) parent
					.findViewById(R.id.card_cursor_main_inner_title);
			TextView mSecondaryTitleTextView = (TextView) parent
					.findViewById(R.id.card_cursor_main_inner_subtitle);
			TextView mThirdTitleTextView = (TextView) parent
					.findViewById(R.id.card_cursor_main_inner_thirdtitle);

			if (mTitleTextView != null)
				mTitleTextView.setText(DateTime);

			if (mSecondaryTitleTextView != null)
				mSecondaryTitleTextView.setText(LocationName);

			if (mThirdTitleTextView != null)
				mThirdTitleTextView.setText(Notifications);

		}
	}

}
