/**
 * 
 */
package me.iamcxa.remindme;

import me.iamcxa.remindme.cardset.ListCursorCardFragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * @author cxa
 *
 */
public class testcard extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		

		  
	            FragmentManager fragmentManager = getFragmentManager();
	            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

	    		ListCursorCardFragment fragment11 = new ListCursorCardFragment();
	            fragmentTransaction.replace(R.id.fragment_main, fragment11);
	            fragmentTransaction.addToBackStack(null);
	            fragmentTransaction.commit();

	        
	}

}
