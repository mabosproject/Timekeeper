package mabo_com.timekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class Alarm_SnoozeOption extends Activity {

    Intent snooze_option_return_intent = new Intent();
    private int determine_snooze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_snooze_option);
        Intent intent_alarm_config = getIntent();
        RadioGroup radioGroup = findViewById(R.id.RadioGroup_snooze);
        determine_snooze = intent_alarm_config.getIntExtra("DETERMINE_SNOOZE",0);
        check_current_select(radioGroup);

    }

    public void ok_snooze_option(View view) {
        RadioGroup radioGroup = findViewById(R.id.RadioGroup_snooze);
        check_button_id(radioGroup.getCheckedRadioButtonId());
        snooze_option_return_intent.putExtra("SNOOZE_OPTION",determine_snooze);
        setResult(RESULT_OK,snooze_option_return_intent);
        finish();
    }

    public void close_snooze_option(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void check_current_select(RadioGroup radioGroup) {
        switch (determine_snooze){
            case 0:
                radioGroup.check(R.id.radioButton_non);
                break;
            case 5:
                radioGroup.check(R.id.radioButton_5minutes);
                break;
            case 10:
                radioGroup.check(R.id.radioButton_10minutes);
                break;
            case 15:
                radioGroup.check(R.id.radioButton_15minutes);
                break;
            case 20:
                radioGroup.check(R.id.radioButton_20minutes);
                break;
            case 25:
                radioGroup.check(R.id.radioButton_25minutes);
                break;
            case 30:
                radioGroup.check(R.id.radioButton_30minutes);
                break;
        }
    }

    private void check_button_id(int checkedRadioButtonId) {
        switch (checkedRadioButtonId){
            case R.id.radioButton_non:
                determine_snooze = 0;
                break;
            case R.id.radioButton_5minutes:
                determine_snooze = 5;
                break;
            case R.id.radioButton_10minutes:
                determine_snooze = 10;
                break;
            case R.id.radioButton_15minutes:
                determine_snooze = 15;
                break;
            case R.id.radioButton_20minutes:
                determine_snooze = 20;
                break;
            case R.id.radioButton_25minutes:
                determine_snooze = 25;
                break;
            case R.id.radioButton_30minutes:
                determine_snooze = 30;
                break;
        }
    }

}
