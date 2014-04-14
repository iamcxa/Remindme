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

package me.iamcxa.remindme.cardset;

import me.iamcxa.remindme.CommonUtils;
import me.iamcxa.remindme.R;
import me.iamcxa.remindme.CommonUtils.RemindmeTaskCursor;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardCursorAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
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

	@Override
	public int getTitleResourceId() {
		return R.string.carddemo_title_list_cursor;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.demo_fragment_list_cursor, container,false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getLoaderManager().initLoader(0, null, this);
		init();
	}

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
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Loader<Cursor> loader = null;
		loader = new CursorLoader(getActivity(), CommonUtils.CONTENT_URI,
				RemindmeTaskCursor.PROJECTION, null, null,
				CommonUtils.DEFAULT_SORT_ORDER);
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

			CardThumbnail thumb = new CardThumbnail(getActivity());
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
			card.mainTitle = cursor.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.Tittle+1);
			card.secondaryTitle = cursor.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.CONTENT+1);
			card.mainHeader = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.KEY_ID);
			card.setId("" + cursor.getInt(CommonUtils.RemindmeTaskCursor.IndexColumns.EndDate+1));

			int thumb = cursor
					.getInt(CommonUtils.RemindmeTaskCursor.IndexColumns.KEY_ID);
			switch (thumb/2) {
			case 0:
				card.resourceIdThumb = R.drawable.bubble;
				break;
			case 1:
				card.resourceIdThumb = R.drawable.act_browser;
				break;
			case 2:
				card.resourceIdThumb = R.drawable.ic_play_store;
				break;
			case 3:
				card.resourceIdThumb = R.drawable.arrow;
				break;
			case 4:
				card.resourceIdThumb = R.drawable.ws_icon_large;
				break;
			}

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

	public class MyCursorCard extends Card {

		String mainTitle;
		String secondaryTitle;
		String mainHeader;
		int resourceIdThumb;

		public MyCursorCard(Context context) {
			super(context, R.layout.carddemo_cursor_inner_content);
		}

		@Override
		public void setupInnerViewElements(ViewGroup parent, View view) {
			// Retrieve elements
			TextView mTitleTextView = (TextView) parent
					.findViewById(R.id.carddemo_cursor_main_inner_title);
			TextView mSecondaryTitleTextView = (TextView) parent
					.findViewById(R.id.carddemo_cursor_main_inner_subtitle);

			if (mTitleTextView != null)
				mTitleTextView.setText(mainTitle);

			if (mSecondaryTitleTextView != null)
				mSecondaryTitleTextView.setText(secondaryTitle);

		}
	}
}
