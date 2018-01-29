package mabo_com.timekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class Alarm_RepeatOption extends Activity {

    Intent repeat_option_return_intent = new Intent();
    private int determine_repeat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_repeat_option);
        Intent intent_repeat_option = getIntent();
        CheckBox checkBox_sun = findViewById(R.id.checkBox_sun);
        CheckBox checkBox_mon = findViewById(R.id.checkBox_mon);
        CheckBox checkBox_tue = findViewById(R.id.checkBox_tue);
        CheckBox checkBox_wed = findViewById(R.id.checkBox_wed);
        CheckBox checkBox_thu = findViewById(R.id.checkBox_thu);
        CheckBox checkBox_fri = findViewById(R.id.checkBox_fri);
        CheckBox checkBox_sat = findViewById(R.id.checkBox_sat);
        determine_repeat = intent_repeat_option.getIntExtra("DETERMINE_REPEAT",0x00);
        check_box_initialize(checkBox_sun,checkBox_mon,checkBox_tue,checkBox_wed,checkBox_thu,checkBox_fri,checkBox_sat);
    }
    public void close_repeat_option(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void ok_repeat_option(View view) {
        CheckBox checkBox_sun = findViewById(R.id.checkBox_sun);
        CheckBox checkBox_mon = findViewById(R.id.checkBox_mon);
        CheckBox checkBox_tue = findViewById(R.id.checkBox_tue);
        CheckBox checkBox_wed = findViewById(R.id.checkBox_wed);
        CheckBox checkBox_thu = findViewById(R.id.checkBox_thu);
        CheckBox checkBox_fri = findViewById(R.id.checkBox_fri);
        CheckBox checkBox_sat = findViewById(R.id.checkBox_sat);
        repeat_option_from_check_box(checkBox_sun,checkBox_mon,checkBox_tue,checkBox_wed,checkBox_thu,checkBox_fri,checkBox_sat);
        repeat_option_return_intent.putExtra("REPEAT_OPTION",determine_repeat);
        setResult(RESULT_OK, repeat_option_return_intent);
        finish();
    }

    private void check_box_initialize(CheckBox checkBox_sun,CheckBox checkBox_mon,CheckBox checkBox_tue,CheckBox checkBox_wed,CheckBox checkBox_thu,CheckBox checkBox_fri,CheckBox checkBox_sat){
        if((determine_repeat & 0x40) == 0x40){
            checkBox_sun.setChecked(true);
        }
        if((determine_repeat & 0x20) == 0x20){
            checkBox_mon.setChecked(true);
        }
        if((determine_repeat & 0x10) == 0x10){
            checkBox_tue.setChecked(true);
        }
        if((determine_repeat & 0x08) == 0x08){
            checkBox_wed.setChecked(true);
        }
        if((determine_repeat & 0x04) == 0x04){
            checkBox_thu.setChecked(true);
        }
        if((determine_repeat & 0x02) == 0x02){
            checkBox_fri.setChecked(true);
        }
        if((determine_repeat & 0x01) == 0x01){
            checkBox_sat.setChecked(true);
        }
    }

    private void repeat_option_from_check_box(CheckBox checkBox_sun,CheckBox checkBox_mon,CheckBox checkBox_tue,CheckBox checkBox_wed,CheckBox checkBox_thu,CheckBox checkBox_fri,CheckBox checkBox_sat){
        determine_repeat = 0x00;
        if(checkBox_sun.isChecked()) {
            determine_repeat = determine_repeat | 0x40;
        }
        if(checkBox_mon.isChecked()) {
            determine_repeat = determine_repeat | 0x20;
        }
        if(checkBox_tue.isChecked()) {
            determine_repeat = determine_repeat | 0x10;
        }
        if(checkBox_wed.isChecked()) {
            determine_repeat = determine_repeat | 0x08;
        }
        if(checkBox_thu.isChecked()) {
            determine_repeat = determine_repeat | 0x04;
        }
        if(checkBox_fri.isChecked()) {
            determine_repeat = determine_repeat | 0x02;
        }
        if(checkBox_sat.isChecked()) {
            determine_repeat = determine_repeat | 0x01;
        }
    }

}
