package activity;

import ui.SinGraph;
import android.app.Activity;
import android.os.Bundle;

public class MindActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        setContentView(new SinGraph(this));
    }
}