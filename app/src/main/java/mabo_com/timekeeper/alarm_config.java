package mabo_com.timekeeper;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;


public class alarm_config extends Activity {

    static final int REQUEST_CODE_TIME_PICKER = 11;
    static final int REQUEST_CODE_REPEAT_OPTION = 12;
    static final int REQUEST_CODE_SOUND_PICKER = 13;
    static final int REQUEST_CODE_SNOOZE_OPTION = 14;
    static final int SNOOZE_NON = 0x00;
    private static int hour,minute;
    private static String hour_min,sound_name;
    private static int determine_repeat,determine_snooze;
    private static boolean vibration_on_off;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_config);
        TextView alarm_time_text = findViewById(R.id.alarm_time);
        TextView repeat_sun = findViewById(R.id.string_sun);
        TextView repeat_mon = findViewById(R.id.string_mon);
        TextView repeat_tue = findViewById(R.id.string_tue);
        TextView repeat_wed = findViewById(R.id.string_wed);
        TextView repeat_thu = findViewById(R.id.string_thu);
        TextView repeat_fri = findViewById(R.id.string_fri);
        TextView repeat_sat = findViewById(R.id.string_sat);
        TextView sound_text = findViewById(R.id.alarm_sound);
        TextView snooze_text = findViewById(R.id.snooze_text);
        Switch vibration_switch = findViewById(R.id.vibration_switch);
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        set_up_alarm_time_string();
        alarm_time_text.setText(hour_min);
        determine_repeat = 0x00;
        determine_repeat_color(repeat_sun,repeat_mon,repeat_tue,repeat_wed,repeat_thu,repeat_fri,repeat_sat);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),uri);
        sound_name = ringtone.getTitle(getApplicationContext());
        sound_text.setText(sound_name);
        determine_snooze = 0;
        determine_snooze_text(snooze_text);
        vibration_on_off = false;
        vibration_switch.setChecked(vibration_on_off);
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
                    set_up_alarm_time_string();
                    alarm_time_text.setText(hour_min);
                }

            case REQUEST_CODE_REPEAT_OPTION:
                if(resultCode == RESULT_OK){
                    determine_repeat = data.getIntExtra("REPEAT_OPTION",0x00);
                    TextView repeat_sun = findViewById(R.id.string_sun);
                    TextView repeat_mon = findViewById(R.id.string_mon);
                    TextView repeat_tue = findViewById(R.id.string_tue);
                    TextView repeat_wed = findViewById(R.id.string_wed);
                    TextView repeat_thu = findViewById(R.id.string_thu);
                    TextView repeat_fri = findViewById(R.id.string_fri);
                    TextView repeat_sat = findViewById(R.id.string_sat);
                    determine_repeat_color(repeat_sun,repeat_mon,repeat_tue,repeat_wed,repeat_thu,repeat_fri,repeat_sat);
                }

            case REQUEST_CODE_SOUND_PICKER:
                if(resultCode == RESULT_OK){
                    TextView sound_text = findViewById(R.id.alarm_sound);
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),uri);
                    sound_name = ringtone.getTitle(getApplicationContext());
                    sound_text.setText(sound_name);
                }

            case REQUEST_CODE_SNOOZE_OPTION:
                if(resultCode == RESULT_OK){
                    determine_snooze = data.getIntExtra("SNOOZE_OPTION",0);
                    TextView snooze_text = findViewById(R.id.snooze_text);
                    determine_snooze_text(snooze_text);
                }

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

    private void set_up_alarm_time_string(){
        hour_min =String.format("%02d",hour)+":"+String.format("%02d",minute);
    }

    public void touch_repeat_option(View view) {
        Intent intent_repeat_option = new Intent(getApplication(),repeat_option.class);
        intent_repeat_option.putExtra("DETERMINE_REPEAT",determine_repeat);
        startActivityForResult(intent_repeat_option,REQUEST_CODE_REPEAT_OPTION);
    }

    private void determine_repeat_color(TextView repeat_sun,TextView repeat_mon,TextView repeat_tue,TextView repeat_wed,TextView repeat_thu,TextView repeat_fri,TextView repeat_sat){
        if((determine_repeat & 0x40) == 0x40){
            repeat_sun.setTextColor(0xff333333);
        }else{
            repeat_sun.setTextColor(0x55535353);
        }
        if((determine_repeat & 0x20) == 0x20){
            repeat_mon.setTextColor(0xff333333);
        }else{
            repeat_mon.setTextColor(0x55535353);
        }
        if((determine_repeat & 0x10) == 0x10){
            repeat_tue.setTextColor(0xff333333);
        }else{
            repeat_tue.setTextColor(0x55535353);
        }
        if((determine_repeat & 0x08) == 0x08){
            repeat_wed.setTextColor(0xff333333);
        }else{
            repeat_wed.setTextColor(0x55535353);
        }
        if((determine_repeat & 0x04) == 0x04){
            repeat_thu.setTextColor(0xff333333);
        }else{
            repeat_thu.setTextColor(0x55535353);
        }
        if((determine_repeat & 0x02) == 0x02){
            repeat_fri.setTextColor(0xff333333);
        }else{
            repeat_fri.setTextColor(0x55535353);
        }
        if((determine_repeat & 0x01) == 0x01){
            repeat_sat.setTextColor(0xff333333);
        }else{
            repeat_sat.setTextColor(0x55535353);
        }
    }

    public void pick_sound(View view) {
        Intent intent_sound_picker = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent_sound_picker.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT,true);
        startActivityForResult(intent_sound_picker,REQUEST_CODE_SOUND_PICKER);
    }

    public void touch_snooze_option(View view) {
        Intent intent_snooze_option = new Intent(getApplication(),snooze_option.class);
        intent_snooze_option.putExtra("DETERMINE_SNOOZE",determine_snooze);
        startActivityForResult(intent_snooze_option,REQUEST_CODE_SNOOZE_OPTION);
    }

    private void determine_snooze_text(TextView snooze_text) {
        switch (determine_snooze){
            case 0:
                snooze_text.setText(R.string.non_snooze);
                break;
            case 5:
                snooze_text.setText(R.string.minutes_5);
                break;
            case 10:
                snooze_text.setText(R.string.minutes_10);
                break;
            case 15:
                snooze_text.setText(R.string.minutes_15);
                break;
            case 20:
                snooze_text.setText(R.string.minutes_20);
                break;
            case 25:
                snooze_text.setText(R.string.minutes_25);
                break;
            case 30:
                snooze_text.setText(R.string.minutes_30);
                break;
        }
    }

    public void touch_vibration_option(View view) {
        Switch vibration_switch = findViewById(R.id.vibration_switch);
        vibration_switch.setChecked(!vibration_on_off);
        vibration_on_off = !vibration_on_off;
    }

}

