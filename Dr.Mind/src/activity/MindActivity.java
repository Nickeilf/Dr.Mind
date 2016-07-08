package activity;

import android.app.Activity;
import android.os.Bundle;
import cn.edu.cn.R;

public class MindActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< Updated upstream
        //setContentView(R.layout.main);
        setContentView(R.layout.main);
=======
        setContentView(R.layout.main);
//        setContentView(new SinGraph(this));
>>>>>>> Stashed changes
    }
}