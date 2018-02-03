package mabo_com.timekeeper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by masaki on 2018/01/29.
 */

public class Alarm_BroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // アラームを受け取って起動するActivityを指定、起動
        Intent notification = new Intent(context, Alarm_NotificationActivity.class);
        notification.putExtra("ID",intent.getIntExtra("ID",0));
        notification.putExtra("HOUR", intent.getIntExtra("HOUR",0));
        notification.putExtra("MINUTE",intent.getIntExtra("MINUTE",0));
        notification.putExtra("COMMENT",intent.getStringExtra("COMMENT"));
        notification.putExtra("URISTRING",intent.getStringExtra("URISTRING"));
        notification.putExtra("VOLUME",intent.getIntExtra("VOLUME",8));
        notification.putExtra("REPEAT",intent.getIntExtra("REPEAT",0x00));
        notification.putExtra("SNOOZE",intent.getIntExtra("SNOOZE",0x00));
        notification.putExtra("VIBRATION",intent.getBooleanExtra("VIBRATION",false));
        // 画面起動に必要
        notification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(notification);
    }
}
