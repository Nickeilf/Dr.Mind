package menudrawer;

import cn.edu.cn.R;
import android.app.Activity;
import android.os.Bundle;
import net.simonvt.menudrawer.MenuDrawer;

public class SampleActivity extends Activity {

    private MenuDrawer mDrawer;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        mDrawer = MenuDrawer.attach(this);
        mDrawer.setContentView(R.layout.main);
//        mDrawer.setMenuView(R.layout.menu_main);
    }
}
