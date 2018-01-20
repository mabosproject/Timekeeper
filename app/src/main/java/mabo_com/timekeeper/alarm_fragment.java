package mabo_com.timekeeper;

import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by masaki on 2018/01/01.
 */

public class alarm_fragment extends android.support.v4.app.Fragment {

    private static final String TAG = "AlarmTabFragment";

    Timer timer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.alarm_tab,container,false);

        final TextView date = view.findViewById(R.id.DateText);
        final TextView time = view.findViewById(R.id.TimeText);
        final ImageView hourPointer = view.findViewById(R.id.hourPointer);
        final ImageView minutePointer = view.findViewById(R.id.minutePointer);
        final ImageView secondPointer = view.findViewById(R.id.secondPointer);

        final Runnable setDateRunnable = new Runnable() {
            public void run() {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                int second = cal.get(Calendar.SECOND);

                if(hour<10 && minute <10){
                    time.setText("0" + hour + ":" + "0" + minute + ":" + second);
                }else if(hour<10){
                    time.setText("0" + hour + ":" + minute + ":" + second);
                }else if(minute<10){
                    time.setText(hour + ":" + "0" + minute + ":" + second);
                }else{
                    time.setText(hour + ":" + minute + ":" + second);
                }
                date.setText(year + "." + (month + 1) + "." + day + "\n" + getDayOfTheWeek());

                hourPointer.setRotation(getRad(1,hour,minute,second));
                minutePointer.setRotation(getRad(2,hour,minute,second));
                secondPointer.setRotation(getRad(3,hour,minute,second));
            }
        };

        timer =new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run () {
                    if(getActivity()!=null){
                        getActivity().runOnUiThread(setDateRunnable);
                    }
                }
        },0,1000);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 定期実行をcancelする
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public static String getDayOfTheWeek() {
        Calendar cal = Calendar.getInstance();
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY: return "Sun.";
            case Calendar.MONDAY: return "Mon.";
            case Calendar.TUESDAY: return "Tue.";
            case Calendar.WEDNESDAY: return "Wed.";
            case Calendar.THURSDAY: return "Thu.";
            case Calendar.FRIDAY: return "Fri.";
            case Calendar.SATURDAY: return "Sat.";
        }
        throw new IllegalStateException();
    }


    private float getRad(int flag,int hour, int minute, int second ){
        float hour_radian, minute_radian, second_radian;
        switch (flag){
            case 1:
                if(hour >= 12){
                    hour_radian = (hour-12f)*30f+minute*0.5f;
                }else{
                    hour_radian = hour*30f+minute*0.5f;
                }
                return hour_radian;
            case 2:
                minute_radian = minute*6;
                return minute_radian;
            case 3:
                second_radian = second*6;
                return second_radian;
            default: return 0;
        }
    }

}

