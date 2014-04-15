/*
 * 
 */
package me.iamcxa.remindme.cardfragment;

import me.iamcxa.remindme.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * @author cxa
 * 
 */
public class CardFragmentLoader extends Fragment {
	//public  ProgressBar pBar;
	
	/**********************/
	/** onCreate LOCALE **/
	/**********************/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_main, container, false);
		
		//pBar = (ProgressBar) findViewById(R.id.progressBar1);
		//pBar.setVisibility(View.INVISIBLE);
		
		return view;
	}

	/**********************/
	/**		@param progressbar1
	/**		@return		 **/
	/**********************/
	private ProgressBar findViewById(int progressbar1) {
		// TODO Auto-generated method stub
		return null;
	}

}