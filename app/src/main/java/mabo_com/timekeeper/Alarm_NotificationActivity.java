package mabo_com.timekeeper;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by masaki on 2018/01/29.
 */

public class Alarm_NotificationActivity extends Activity {

    private MediaPlayer mediaPlayer;
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

        int ALARM_TIME = 1000 * 60 * 30;
        vibrator.vibrate(ALARM_TIME);
        Toast.makeText(getApplicationContext(),String.format("%d,%d,%d",id,alarm_hour,alarm_minute),Toast.LENGTH_SHORT).show();
        if (mediaPlayer == null)
            mediaPlayer = MediaPlayer.create(getApplication(), RingtoneManager.getValidRingtoneUri(getApplication()));
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAndRelease();
    }

    private void stopAndRelease() {

        vibrator.cancel();

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
