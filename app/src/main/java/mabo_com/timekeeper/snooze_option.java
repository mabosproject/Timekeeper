package mabo_com.timekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class snooze_option extends Activity {

    Intent snooze_option_return_intent = new Intent();
    private int determine_snooze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snooze_option);
           }

    public void ok_snooze_option(View view) {
        snooze_option_return_intent.putExtra("SNOOZE_OPTION",determine_snooze);
        setResult(RESULT_OK,snooze_option_return_intent);
        finish();
    }

    public void close_snooze_option(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
