package mabo_com.timekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class alarm_config extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_config);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // MyApplication._callbacks に requestCode があったら、そのコールバックを呼び出す。
        if(requestCode == 1111 && requestCode == RESULT_OK){
            TextView textView = findViewById(R.id.alarm_time);
            String hour = data.getStringExtra("HOUR");
            String min = data.getStringExtra("MIN");
            String hourmin = hour+":"+min;
            textView.setText(hourmin);
        }
    }


    public void close(View view) {
        finish();
    }

    public void retention(View view) {
        finish();
    }

    public void time_pick(View view) {
        Intent intent = new Intent(getApplication(),time_picker.class);
        startActivityForResult(intent,1111);
    }

    public void touch_repeat_option(View view) {
        Intent intent = new Intent(getApplication(),repeat_option.class);
        startActivity(intent);
    }

    public void pick_sound(View view) {
    }

    public void touch_snooze_option(View view) {
    }
}
