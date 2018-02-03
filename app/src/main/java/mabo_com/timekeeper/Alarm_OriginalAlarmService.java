package mabo_com.timekeeper;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by masaki on 2018/01/29.
 */

public class Alarm_OriginalAlarmService extends Service{

    private int id,alarm_hour,alarm_minute,volume,repeat,snooze,vibration;
    private String comment,uri_string;
    private Boolean vibration_on_off;
    @Override
    public IBinder onBind(Intent intent) {
        Bundle extras = intent.getExtras();

        return null;
    }

    @Override
    public void onCreate() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        id = intent.getIntExtra("ID",0);
        alarm_hour = intent.getIntExtra("HOUR",0);
        alarm_minute = intent.getIntExtra("MINUTE",0);
        comment = intent.getStringExtra("COMMENT");
        uri_string = intent.getStringExtra("URISTRING");
        volume = intent.getIntExtra("VOLUME",8);
        repeat = intent.getIntExtra("REPEAT",0x00);
        snooze = intent.getIntExtra("SNOOZE",0x00);
        vibration_on_off = intent.getBooleanExtra("VIBRATION",false);
        Thread thr = new Thread(null, mTask, "AlarmServiceThread");
        thr.start();
        // 強制終了時、システムによる再起動を求める場合はSTART_STICKYを利用
        // 再起動が不要な場合はSTART_NOT_STICKYを利用する
        return START_NOT_STICKY;
    }
    Runnable mTask = new Runnable() {
        public void run() {
            // アラームを受け取るActivityを指定
            Intent alarmBroadcast = new Intent();
            alarmBroadcast.putExtra("ID",id);
            alarmBroadcast.putExtra("HOUR",alarm_hour);
            alarmBroadcast.putExtra("MINUTE",alarm_minute);
            alarmBroadcast.putExtra("COMMENT",comment);
            alarmBroadcast.putExtra("URISTRING",uri_string);
            alarmBroadcast.putExtra("VOLUME",volume);
            alarmBroadcast.putExtra("REPEAT",repeat);
            alarmBroadcast.putExtra("SNOOZE",snooze);
            alarmBroadcast.putExtra("VIBRATION",vibration_on_off);
            // ここでActionをセットする(Manifestに書いたものと同じであれば何でもよい)
            alarmBroadcast.setAction("Alarm_Action");
            // レシーバーへ渡す
            sendBroadcast(alarmBroadcast);
            // 役目を終えたサービスを止める
            Alarm_OriginalAlarmService.this.stopSelf();
        }
    };
}
