package me.iamcxa.remindme.cardfragment;

import it.gmariotti.cardslib.library.internal.CardCursorAdapter;
import me.iamcxa.remindme.CommonUtils;
import me.iamcxa.remindme.CommonUtils.RemindmeTaskCursor;
import me.iamcxa.remindme.R;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * 
 * @author 郭宏志 備忘錄列表類別，提供資料顯示
 */
public class ListviewFragment extends Fragment implements
		LoaderCallbacks<Cursor> {

	ListView listView;
	SimpleCursorAdapter adapter;
	it.gmariotti.cardslib.library.view.CardListView cardListView;
	CardCursorAdapter cAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_listview, container,
				false);

		listView = (ListView) view.findViewById(R.id.ListView);

		adapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_2, null,
				new String[] { RemindmeTaskCursor.KeyColumns.KEY_ID,
						RemindmeTaskCursor.KeyColumns.Tittle }, new int[] {
						android.R.id.text1, android.R.id.text2 }, 0);

		// 將備忘錄資訊顯示到ListView
		listView.setAdapter(adapter);

		/** Creating a loader for populating listview from sqlite database */
		/** This statement, invokes the method onCreatedLoader() */
		getLoaderManager().initLoader(0, null, this);
		return view;
	}

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
