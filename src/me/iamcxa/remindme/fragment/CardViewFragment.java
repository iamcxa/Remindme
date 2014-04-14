/*
 * 
 */
package me.iamcxa.remindme.fragment;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardCursorAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import me.iamcxa.remindme.CommonUtils;
import me.iamcxa.remindme.R;
import me.iamcxa.remindme.CommonUtils.RemindmeTaskCursor;
import me.iamcxa.remindme.cardset.ListCursorCardFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author cxa
 * 
 */
public class CardViewFragment extends Fragment implements
		LoaderCallbacks<Cursor> {
	/*
	 * **********************************************************************
	 * *
	 * Variables * *
	 * ***********************************************************************
	 */
	// ListView listView;
	SimpleCursorAdapter adapter;
	CardListView cardListView;
	CardCursorAdapter cAdapter;

	/*
	 * **********************************************************************
	 * *
	 * onCreateView區段 * *
	 * ***********************************************************************
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_main, container,
				false);

		//cardListView = (CardListView) view.findViewById(R.id.cardListView1);

		adapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_2, null,
				new String[] { BaseColumns._ID,
						RemindmeTaskCursor.KeyColumns.CONTENT }, new int[] {
						android.R.id.text1, android.R.id.text2 }, 0);

		// 將備忘錄資訊顯示到ListView
		// cardListView.setAdapter(adapter);
		/*
		 * MyCursorCardAdapter mAdapter = new MyCursorCardAdapter(getActivity(),
		 * null, true); if (cardListView != null) {
		 * cardListView.setAdapter(mAdapter); }
		 */
		/** Creating a loader for populating listview from sqlite database */
		/** This statement, invokes the method onCreatedLoader() */
		//getLoaderManager().initLoader(0, null, this);
		
		  
		return view;
	}

	/*
	 * **********************************************************************
	 * *
	 * 卡片UI定義定義區段 * *
	 * ***********************************************************************
	 */

	/*
	 * MyCursorCard
	 */
	public class MyCursorCard extends Card {

		public MyCursorCard(Context context, int innerLayout) {
			super(context, innerLayout);
			// TODO Auto-generated constructor stub
		}

	}

	/*
	 * MyCursorCardAdapter
	 */
	/*
	 * public class MyCursorCardAdapter extends CardCursorAdapter { protected
	 * MyCursorCardAdapter(Context context, Cursor c, boolean autoRequery) {
	 * super(context, c, autoRequery); // TODO Auto-generated constructor stub }
	 * 
	 * protected Card getCardFromCursor(Cursor cursor) { Card card = new
	 * MyCursorCard(super.getContext(), 2); setCardFromCursor(card,cursor);
	 * 
	 * //Create a CardHeader CardHeader header = new CardHeader(getActivity());
	 * //Set the header title header.setTitle(card.mainHeader);
	 * 
	 * //Add Header to card card.addCardHeader(header);
	 * 
	 * return card; }
	 * 
	 * private void setCardFromCursor(MyCursorCard card,Cursor cursor) {
	 * 
	 * card.mainTitle=cursor.getString(CardCursorContract.CardCursor.IndexColumns
	 * .TITLE_COLUMN);
	 * card.secondaryTitle=cursor.getString(CardCursorContract.CardCursor
	 * .IndexColumns.SUBTITLE_COLUMN);
	 * card.mainHeader=cursor.getString(CardCursorContract
	 * .CardCursor.IndexColumns.HEADER_COLUMN);
	 * card.setId(""+cursor.getInt(CardCursorContract
	 * .CardCursor.IndexColumns.ID_COLUMN)); }
	 * 
	 * }
	 */
	/*
	 * **********************************************************************
	 * *
	 * CursorLoader 動作定義區段 * *
	 * ***********************************************************************
	 */
	/** A callback method invoked by the loader when initLoader() is called */
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		Uri uri = CommonUtils.CONTENT_URI;
		return new CursorLoader(getActivity(), uri, null, null, null, null);
	}

	/**
	 * A callback method, invoked after the requested content provider returned
	 * all the data
	 */
	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		adapter.swapCursor(arg1);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		adapter.swapCursor(null);
	}
}