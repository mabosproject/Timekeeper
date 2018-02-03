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

    public Alarm_OriginalAlarmManager(Context c, int id,int alarm_hour,int alarm_minute, String comment, String uri_string, int volume, int determine_repeat, int determine_snooze, boolean vibration_on_off){
        this.context = c;
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        mAlarmSender = this.getPendingIntent(id,alarm_hour,alarm_minute, comment, uri_string,volume, determine_repeat,determine_snooze, vibration_on_off);
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
        int diffDay = (int)((cal.getTimeInMillis() - System.currentTimeMillis() + 59999)/(60*60*24*1000)%365);
        int diffHour = (int)((cal.getTimeInMillis() - System.currentTimeMillis() + 59999)/(60*60*1000)%24);
        int diffMinute = (int)((cal.getTimeInMillis() - System.currentTimeMillis() + 59999)/(60*1000)%60);
        if(diffDay == 0){
            Toast.makeText(context,String.format("アラームは%d時間%d分後に設定されました",diffHour,diffMinute),Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,String.format("アラームは%d日%d時間%d分後に設定されました",diffDay,diffHour,diffMinute),Toast.LENGTH_SHORT).show();
        }

        alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(cal.getTimeInMillis(),null),mAlarmSender);
    }

    public void stopAlarm(){
        mAlarmSender.cancel();
        alarmManager.cancel(mAlarmSender);
    }

    private PendingIntent getPendingIntent(int id,int alarm_hour,int alarm_minute, String comment, String uri_string, int volume, int determine_repeat, int determine_snooze, boolean vibration_on_off) {
        // アラーム時に起動するアプリケーションを登録
        Intent intent = new Intent(context,Alarm_OriginalAlarmService.class);
        intent.putExtra("ID",id);
        intent.putExtra("HOUR",alarm_hour);
        intent.putExtra("MINUTE",alarm_minute);
        intent.putExtra("COMMENT",comment);
        intent.putExtra("URISTRING",uri_string);
        intent.putExtra("VOLUME",volume);
        intent.putExtra("REPEAT",determine_repeat);
        intent.putExtra("SNOOZE",determine_snooze);
        intent.putExtra("VIBRATION",vibration_on_off);
        PendingIntent pendingIntent = PendingIntent.getService(context,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}
