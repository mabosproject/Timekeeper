package mabo_com.timekeeper;

import android.net.Uri;

/**
 * Created by masaki on 2018/01/20.
 */

public class Alarm_list_structure {

    long id;
    int hour,minute;
    String comment;
    Uri uri;
    int repeat,snooze;
    boolean vibration;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Uri getUri(){
        return uri;
    }

    public void setUri(Uri uri){
        this.uri = uri;
    }

    public int getRepeat(){
        return repeat;
    }

    public void setRepeat(int repeat){
        this.repeat = repeat;
    }

    public int getSnooze(){
        return snooze;
    }

    public void setSnooze(int snooze){
        this.snooze = snooze;
    }

    public boolean getVibration(){
        return vibration;
    }

    public void setVibration(Boolean vibration){
        this.vibration = vibration;
    }

}
