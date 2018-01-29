package mabo_com.timekeeper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by masaki on 2018/01/29.
 */

public class Alarm_OriginalAlarmManager {

    Context context;
    AlarmManager alarmManager;
    private PendingIntent mAlarmSender;

    private static final String TAG = Alarm_OriginalAlarmManager.class.getSimpleName();

    public Alarm_OriginalAlarmManager(Context c,int id){
        this.context = c;
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        mAlarmSender = this.getPendingIntent(id);
    }

    public void addAlarm(int alarm_hour, int alarm_minute){

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, alarm_hour);
        cal.set(Calendar.MINUTE, alarm_minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        if(cal.getTimeInMillis() < System.currentTimeMillis()){
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        int diffHour = (int)((cal.getTimeInMillis() - System.currentTimeMillis())/(60*60*1000)%24);
        int diffMinute = (int)(((cal.getTimeInMillis() - System.currentTimeMillis())/(60*1000))%60);

        Toast.makeText(context,String.format("アラームは%02d時間%02d分後に設定されました",diffHour,diffMinute),Toast.LENGTH_SHORT).show();
        alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(cal.getTimeInMillis(),null),mAlarmSender);
    }

    public void stopAlarm(){
        alarmManager.cancel(mAlarmSender);
        mAlarmSender.cancel();
    }

    private PendingIntent getPendingIntent(int id) {
        // アラーム時に起動するアプリケーションを登録
        Intent intent = new Intent(context,Alarm_OriginalAlarmService.class);
        intent.setType(String.valueOf(id));
        PendingIntent pendingIntent = PendingIntent.getService(context,PendingIntent.FLAG_ONE_SHOT,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}
