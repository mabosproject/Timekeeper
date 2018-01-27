package mabo_com.timekeeper;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


public class Alarm_config extends Activity {

    static final int REQUEST_CODE_TIME_PICKER = 11;
    static final int REQUEST_CODE_REPEAT_OPTION = 12;
    static final int REQUEST_CODE_SOUND_PICKER = 13;
    static final int REQUEST_CODE_SNOOZE_OPTION = 14;
    private static int hour,minute,id;
    private static String hour_min,comment,sound_name;
    Uri uri;
    SeekBar volume_bar;
    private static int determine_repeat,determine_snooze;
    private static boolean vibration_on_off;
    Intent return_intent = new Intent();
    EditText alarm_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_config);

        Intent intent_alarm_fragment = getIntent();

        TextView alarm_time_text = findViewById(R.id.alarm_time);

        TextView repeat_sun = findViewById(R.id.string_sun);
        TextView repeat_mon = findViewById(R.id.string_mon);
        TextView repeat_tue = findViewById(R.id.string_tue);
        TextView repeat_wed = findViewById(R.id.string_wed);
        TextView repeat_thu = findViewById(R.id.string_thu);
        TextView repeat_fri = findViewById(R.id.string_fri);
        TextView repeat_sat = findViewById(R.id.string_sat);

        alarm_comment = findViewById(R.id.editText);

        TextView sound_text = findViewById(R.id.alarm_sound);

        volume_bar = findViewById(R.id.seekBar);

        TextView snooze_text = findViewById(R.id.snooze_text);

        Switch vibration_switch = findViewById(R.id.vibration_switch);

        id = intent_alarm_fragment.getIntExtra("ID",0);

        hour = intent_alarm_fragment.getIntExtra("HOUR",0);
        minute = intent_alarm_fragment.getIntExtra("MINUTE",0);
        set_up_alarm_time_string();
        alarm_time_text.setText(hour_min);

        determine_repeat = intent_alarm_fragment.getIntExtra("REPEAT",0x00);
        determine_repeat_color(repeat_sun,repeat_mon,repeat_tue,repeat_wed,repeat_thu,repeat_fri,repeat_sat);

        comment = intent_alarm_fragment.getStringExtra("COMMENT");
        alarm_comment.setText(comment);

        uri = Uri.parse(intent_alarm_fragment.getStringExtra("URI"));
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),uri);
        sound_name = ringtone.getTitle(getApplicationContext());
        sound_text.setText(sound_name);

        volume_bar.setProgress(intent_alarm_fragment.getIntExtra("VOLUME",8));

        determine_snooze = intent_alarm_fragment.getIntExtra("SNOOZE",0);
        determine_snooze_text(snooze_text);

        if(intent_alarm_fragment.getIntExtra("VIBRATION",0) == 0){
            vibration_on_off = false;
        }else{
            vibration_on_off = true;
        }
        vibration_switch.setChecked(vibration_on_off);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            //TimePickerのコールバック処理
            case REQUEST_CODE_TIME_PICKER:
                if(resultCode == RESULT_OK){
                    TextView alarm_time_text = findViewById(R.id.alarm_time);
                    hour = data.getIntExtra("HOUR",hour);
                    minute = data.getIntExtra("MINUTE",minute);
                    set_up_alarm_time_string();
                    alarm_time_text.setText(hour_min);
                }
                break;
            //repeat_optionのコールバック処理
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
                break;

            case REQUEST_CODE_SOUND_PICKER:
                if(resultCode == RESULT_OK){
                    TextView sound_text = findViewById(R.id.alarm_sound);
                    uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    if(uri == null){
                        sound_text.setText(R.string.non_option);
                        break;
                    }
                    Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),uri);
                    sound_name = ringtone.getTitle(getApplicationContext());
                    sound_text.setText(sound_name);
                }
                break;

            case REQUEST_CODE_SNOOZE_OPTION:
                if(resultCode == RESULT_OK){
                    determine_snooze = data.getIntExtra("SNOOZE_OPTION",0);
                    TextView snooze_text = findViewById(R.id.snooze_text);
                    determine_snooze_text(snooze_text);
                }
                break;

        }

    }

    public void close(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void retention(View view) {
        comment = alarm_comment.getText().toString();
        return_intent.putExtra("RET_ID",id);
        return_intent.putExtra("RET_HOUR",hour);
        return_intent.putExtra("RET_MINUTE",minute);
        return_intent.putExtra("RET_COMMENT",comment);
        return_intent.putExtra("RET_URI",uri.toString());
        return_intent.putExtra("RET_VOLUME",volume_bar.getProgress());
        return_intent.putExtra("RET_REPEAT",determine_repeat);
        return_intent.putExtra("RET_SNOOZE",determine_snooze);
        if(vibration_on_off == false){
            return_intent.putExtra("RET_VIBRATION",0);
        }else{
            return_intent.putExtra("RET_VIBRATION",1);
        }
        setResult(RESULT_OK,return_intent);
        finish();
    }

    public void time_pick(View view) {
        Intent intent_time_picker = new Intent(getApplication(),Time_picker.class);
        intent_time_picker.putExtra("CURRENT_HOUR",hour);
        intent_time_picker.putExtra("CURRENT_MINUTE",minute);
        startActivityForResult(intent_time_picker,REQUEST_CODE_TIME_PICKER);
    }

    private void set_up_alarm_time_string(){
        hour_min =String.format("%02d",hour)+":"+String.format("%02d",minute);
    }

    public void touch_repeat_option(View view) {
        Intent intent_repeat_option = new Intent(getApplication(),Repeat_option.class);
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
        Intent intent_snooze_option = new Intent(getApplication(),Snooze_option.class);
        intent_snooze_option.putExtra("DETERMINE_SNOOZE",determine_snooze);
        startActivityForResult(intent_snooze_option,REQUEST_CODE_SNOOZE_OPTION);
    }

    private void determine_snooze_text(TextView snooze_text) {
        switch (determine_snooze){
            case 0:
                snooze_text.setText(R.string.non_option);
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