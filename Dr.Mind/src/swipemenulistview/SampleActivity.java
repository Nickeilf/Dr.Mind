package swipemenulistview;

import net.simonvt.menudrawer.MenuDrawer;
import android.app.Activity;
import android.os.Bundle;

public class SampleActivity extends Activity {

	   private MenuDrawer mDrawer;

	    @Override
	    protected void onCreate(Bundle state) {
	        super.onCreate(state);
	        mDrawer = MenuDrawer.attach(this);
	    
	    }
	
}
