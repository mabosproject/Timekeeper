package mabo_com.timekeeper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by masaki on 2018/01/29.
 */

public class Alarm_OriginalAlarmService extends Service{

    @Override
        public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
                Thread thr = new Thread(null, mTask, "MyAlarmServiceThread");
        thr.start();
    }
    Runnable mTask = new Runnable() {
        public void run() {
            // アラームを受け取るActivityを指定
            Intent alarmBroadcast = new Intent();
            // ここでActionをセットする(Manifestに書いたものと同じであれば何でもよい)
            alarmBroadcast.setAction("Alarm_Action");
            // レシーバーへ渡す
            sendBroadcast(alarmBroadcast);
            // 役目を終えたサービスを止める
            Alarm_OriginalAlarmService.this.stopSelf();
        }
    };
}
