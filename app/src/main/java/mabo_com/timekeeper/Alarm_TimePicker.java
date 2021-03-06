package mabo_com.timekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

public class Alarm_TimePicker extends Activity {

    Intent time_picker_return_intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_timepicker);
        Intent intent_alarm_config = getIntent();
        TimePicker timePicker = (TimePicker)this.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setHour(intent_alarm_config.getIntExtra("CURRENT_HOUR",0));
        timePicker.setMinute(intent_alarm_config.getIntExtra("CURRENT_MINUTE",0));
    }

    public void close_time_picker(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void ok_time_picker(View view) {
        // TimePickerインスタンスを取得
        TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
        // 設定時刻の時間を取得
        int hour = timePicker.getHour();
        // 設定時刻の分を取得
        int min = timePicker.getMinute();
        time_picker_return_intent.putExtra("HOUR",hour);
        time_picker_return_intent.putExtra("MINUTE",min);

        setResult(RESULT_OK, time_picker_return_intent);
        finish();
    }
}
