package mabo_com.timekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;


public class alarm_config extends Activity {

    static final int REQUEST_CODE_TIME_PICKER = 11;
    static final int REQUEST_CODE_REPEAT_OPTION = 12;
    static final int REQUEST_CODE_SOUND_PICKER = 13;
    static final int REQUEST_CODE_SNOOZE_OPTION = 14;
    private static int hour,minute;
    private static String hour_min;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_config);
        TextView alarm_time_text = findViewById(R.id.alarm_time);
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        hour_min =String.format("%02d",hour)+":"+String.format("%02d",minute);
        alarm_time_text.setText(hour_min);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TimePickerのコールバック処理
        switch (requestCode){
            case REQUEST_CODE_TIME_PICKER:
                if(resultCode == RESULT_OK){
                    TextView alarm_time_text = findViewById(R.id.alarm_time);
                    hour = data.getIntExtra("HOUR",hour);
                    minute = data.getIntExtra("MINUTE",minute);
                    hour_min = String.format("%02d",hour)+":"+String.format("%02d",minute);
                    alarm_time_text.setText(hour_min);
                }

            case REQUEST_CODE_REPEAT_OPTION:

            case REQUEST_CODE_SOUND_PICKER:

            case REQUEST_CODE_SNOOZE_OPTION:

        }


    }


    public void close(View view) {
        finish();
    }

    public void retention(View view) {
        finish();
    }

    public void time_pick(View view) {
        Intent intent_time_picker = new Intent(getApplication(),time_picker.class);
        intent_time_picker.putExtra("CURRENT_HOUR",hour);
        intent_time_picker.putExtra("CURRENT_MINUTE",minute);
        startActivityForResult(intent_time_picker,REQUEST_CODE_TIME_PICKER);
    }

    public void touch_repeat_option(View view) {
        Intent intent_repeat_option = new Intent(getApplication(),repeat_option.class);
        startActivityForResult(intent_repeat_option,REQUEST_CODE_REPEAT_OPTION);
    }

    public void pick_sound(View view) {
    }

    public void touch_snooze_option(View view) {
    }
}
