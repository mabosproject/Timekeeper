package mabo_com.timekeeper;

import android.database.Cursor;
import android.net.Uri;

/**
 * Created by masaki on 2018/01/20.
 */

public class Alarm_ListStructure {

    private int id,hour,minute,volume,repeat,snooze,vibration;
    private String comment,uri;

    public Alarm_ListStructure(int id, int hour, int minute, int repeat, String comment, String uri, int volume, int snooze, int vibration){
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.repeat = repeat;
        this.comment = comment;
        this.uri = uri;
        this.volume = volume;
        this.snooze = snooze;
        this.vibration = vibration;
    }

    public int getId() {
        return id;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getRepeat(){
        return repeat;
    }

    public String getComment() {
    return comment;
    }

    public String getUri(){
    return uri;
    }

    public int getVolume(){
        return volume;
    }

    public int getSnooze(){
        return snooze;
    }

    public int getVibration(){
        return vibration;
    }


}
