package mabo_com.timekeeper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class repeat_option extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeat_option);
    }
    public void close_repeat_option(View view) {
        finish();
    }

    public void ok_repeat_option(View view) {
        finish();
    }
}
