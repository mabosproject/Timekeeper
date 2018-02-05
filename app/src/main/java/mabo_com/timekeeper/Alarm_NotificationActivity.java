package mabo_com.timekeeper;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by masaki on 2018/01/29.
 */

public class Alarm_NotificationActivity extends Activity {
    AudioManager audioManager;
    private Ringtone alarm_ringtone;
    private Vibrator vibrator;
    private int id,alarm_hour,alarm_minute,volume,repeat,snooze,vibration;
    private String comment,uri_string;
    private Boolean vibration_on_off;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_notification);

        Intent extras = getIntent();
        id = extras.getIntExtra("ID",0);
        alarm_hour = extras.getIntExtra("HOUR",0);
        alarm_minute = extras.getIntExtra("MINUTE",0);
        comment = extras.getStringExtra("COMMENT");
        uri_string = extras.getStringExtra("URISTRING");
        volume = extras.getIntExtra("VOLUME",0);
        repeat = extras.getIntExtra("REPEAT",0);
        snooze = extras.getIntExtra("SNOOZE",0);
        vibration_on_off = extras.getBooleanExtra("VIBRATION",false);

        //アラームの音量設定
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM,volume,0);

        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);


        // スクリーンロックを解除する
        // 権限が必要
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    public void onStart() {
        super.onStart();

        long[] vibration_pattern = {2000,3000,2000,1000};
        //バイブレーションの有無に応じた処理
        if(vibration_on_off){
            vibrator.vibrate(vibration_pattern,0);
        }
        alarm_ringtone = RingtoneManager.getRingtone(getApplicationContext(), Uri.parse(uri_string));
        AudioAttributes attr = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        alarm_ringtone.setAudioAttributes(attr);
        alarm_ringtone.play();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAndRelease();
    }

    private void stopAndRelease() {

        vibrator.cancel();

        if (alarm_ringtone.isPlaying()) {
            alarm_ringtone.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
