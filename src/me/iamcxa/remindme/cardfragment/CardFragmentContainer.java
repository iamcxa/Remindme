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

/**
 * @author cxa
 * 
 */
public class CardFragmentContainer extends Fragment {
	
	/**********************/
	/** onCreate LOCALE **/
	/**********************/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_main, container, false);

		
		return view;
	}

}