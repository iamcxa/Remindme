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

import me.iamcxa.remindme.CommonUtils;
import me.iamcxa.remindme.R;
import me.iamcxa.remindme.CommonUtils.RemindmeTaskCursor;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
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
public class ListCursorCardFragment extends BaseFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {

	MyCursorCardAdapter mAdapter;
	CardListView mListView;
	ScrollView mScrollView;

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
				RemindmeTaskCursor.PROJECTION, selection, selectionArgs,
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
		protected Card getCardFromCursor(Cursor cursor) {
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

			MyThumbnail thumb = new MyThumbnail(getActivity());
			thumb.setDrawableResource(card.resourceIdThumb);
			card.addCardThumbnail(thumb);

			card.setOnClickListener(new Card.OnCardClickListener() {
				@Override
				public void onClick(Card card, View view) {
					Toast.makeText(
							getContext(),
							"Card id=" + card.getId() + " Title="
									+ card.getTitle(), Toast.LENGTH_SHORT)
							.show();
				}

			});
			return card;
		}

		private void setCardFromCursor(MyCursorCard card, Cursor cursor) {
			// give a ID.
			card.setId(""
					+ cursor.getInt(CommonUtils.RemindmeTaskCursor.IndexColumns.KEY_ID));

			// 卡片標題 - first line
			card.mainHeader = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.Tittle + 1);

			// 時間日期 - sec line
			card.DateTime = cursor.getString(3);
			card.secondaryTitle = cursor.getString(3);
			card.ThirdTitle = cursor.getString(0);

			// This provides a simple (and useless) expand area
			CardExpand expand = new CardExpand(getActivity());
			// Set inner title in Expand Area
			expand.setTitle(getString(R.string.app_name));
			card.addCardExpand(expand);

			boolean isLocation = cursor
					.isNull(CommonUtils.RemindmeTaskCursor.IndexColumns.LocationName);
			boolean isExtrainfo = cursor
					.isNull(CommonUtils.RemindmeTaskCursor.IndexColumns.other);

			// 給予地圖/月曆/有新提示之小圖標
			if (!isLocation) {
				card.resourceIdThumb = R.drawable.map_marker;
			} else if (!isExtrainfo) {
				card.resourceIdThumb = R.drawable.outline_star_act;
			} else {
				card.resourceIdThumb = R.drawable.tear_of_calendar;
			}

			// 依照權重給予卡片顏色
			if (cursor
					.getInt(CommonUtils.RemindmeTaskCursor.IndexColumns.PriorityWeight) > 200) {
				card.setBackgroundResourceId(R.drawable.demo_card_selector_color5);
			} else if (cursor
					.getInt(CommonUtils.RemindmeTaskCursor.IndexColumns.PriorityWeight) > 100) {
				card.setBackgroundResourceId(R.drawable.demo_card_selector_color3);
			}

			// 依照權重給予卡片顏色
			if (cursor
					.getInt(CommonUtils.RemindmeTaskCursor.IndexColumns.PriorityWeight) > 200) {
				card.setBackgroundResourceId(R.drawable.demo_card_selector_color5);
			} else if (cursor
					.getInt(CommonUtils.RemindmeTaskCursor.IndexColumns.PriorityWeight) > 100) {
				card.setBackgroundResourceId(R.drawable.demo_card_selector_color3);
			}

			/*
			 * int thumb = cursor
			 * .getInt(CommonUtils.RemindmeTaskCursor.IndexColumns.KEY_ID);
			 * switch (thumb / 2) { case 0: card.resourceIdThumb =
			 * R.drawable.bubble;
			 * card.setBackgroundResourceId(R.drawable.demo_card_selector_color5
			 * ); break; case 1: card.resourceIdThumb = R.drawable.act_browser;
			 * card
			 * .setBackgroundResourceId(R.drawable.demo_card_selector_color5);
			 * break; case 2: card.resourceIdThumb = R.drawable.ic_play_store;
			 * card
			 * .setBackgroundResourceId(R.drawable.demo_card_selector_color3);
			 * break; case 3: card.resourceIdThumb = R.drawable.arrow;
			 * card.setBackgroundResourceId
			 * (R.drawable.demo_card_selector_color2); break; case 4:
			 * card.resourceIdThumb = R.drawable.ws_icon_large;
			 * card.setBackgroundResourceId
			 * (R.drawable.demo_card_selector_color4); break;
			 * 
			 * }
			 */
		}
	}

	private void removeCard(Card card) {

		// Use this code to delete items on DB
		ContentResolver resolver = getActivity().getContentResolver();
		long noDeleted = resolver.delete(CommonUtils.CONTENT_URI,
				CommonUtils.RemindmeTaskCursor.KeyColumns.KEY_ID + " = ? ",
				new String[] { card.getId() });

		// mAdapter.notifyDataSetChanged();

	}

	/***********************/
	/** Class MyThumbnail **/
	/***********************/
	// implment the clickable card thumbnail.
	class MyThumbnail extends CardThumbnail {

		public MyThumbnail(Context context) {
			super(context);
		}

		@Override
		public void setupInnerViewElements(ViewGroup parent, View imageView) {
			ViewToClickToExpand viewToClickToExpand = ViewToClickToExpand
					.builder().highlightView(false).setupView(imageView);
			getParentCard().setViewToClickToExpand(viewToClickToExpand);
		}
	}

	/************************/
	/** Class MyCursorCard **/
	/************************/
	public class MyCursorCard extends Card {

		String DateTime;
		String secondaryTitle;
		String ThirdTitle;
		String mainHeader;

		int resourceIdThumb;

		public MyCursorCard(Context context) {
			super(context, R.layout.card_cursor_inner_content);
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
				mSecondaryTitleTextView.setText(secondaryTitle);

			if (mThirdTitleTextView != null)
				mThirdTitleTextView.setText(ThirdTitle);

		}
	}

}
