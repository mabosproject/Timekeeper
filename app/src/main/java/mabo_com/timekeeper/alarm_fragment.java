package mabo_com.timekeeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by masaki on 2018/01/01.
 */

public class alarm_fragment extends android.support.v4.app.Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "AlarmTabFragment";
    static final int REQUEST_CODE_ALARM_CONFIG = 15;
    static final int REQUEST_CODE_ALARM_CONFIG_EXISTED = 16;

    ArrayList<Alarm_list_structure> list;
    AlarmListAdapter alarmListAdapter;

    Cursor cursor = null;

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

        FloatingActionButton button = view.findViewById(R.id.button_add_alarm);
        button.setOnClickListener(this);

        list  = new ArrayList<>();
        alarmListAdapter = new AlarmListAdapter(getContext());
        ListView listView =view.findViewById(R.id.alarm_list);
        alarmListAdapter.setAlarm_list(list);
        listView.setAdapter(alarmListAdapter);
        listView.setOnItemClickListener(this);

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

    private void set_alarm_list(int alarm_hour, int alarm_minute, String comment, Uri uri, int repeat, int snooze, boolean vibration){
        Alarm_list_structure list_structure = new Alarm_list_structure();
        list_structure.setHour(alarm_hour);
        list_structure.setMinute(alarm_minute);
        list_structure.setComment(comment);
        list_structure.setUri(uri);
        list_structure.setRepeat(repeat);
        list_structure.setSnooze(snooze);
        list_structure.setVibration(vibration);
        list.add(list_structure);
        alarmListAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity().getApplication(),alarm_config.class);
        Calendar cal = Calendar.getInstance();
        int alarm_hour = cal.get(Calendar.HOUR_OF_DAY);
        int alarm_minute = cal.get(Calendar.MINUTE);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        intent.putExtra("HOUR",alarm_hour);
        intent.putExtra("MINUTE",alarm_minute);
        intent.putExtra("REPEAT",0x00);
        intent.putExtra("COMMENT","");
        intent.putExtra("URI",uri);
        intent.putExtra("VOLUME",8);
        intent.putExtra("SNOOZE",0);
        intent.putExtra("VIBRATION",false);
        startActivityForResult(intent,REQUEST_CODE_ALARM_CONFIG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case REQUEST_CODE_ALARM_CONFIG:

                if (resultCode == Activity.RESULT_OK) {
                    int alarm_hour = data.getIntExtra("RET_HOUR", 0);
                    int alarm_minute = data.getIntExtra("RET_MINUTE", 0);
                    String comment = data.getStringExtra("RET_COMMENT");
                    Uri uri = data.getParcelableExtra("RET_URI");
                    int repeat = data.getIntExtra("RET_REPEAT", 0x00);
                    int snooze = data.getIntExtra("RET_SNOOZE", 0);
                    Boolean vibration = data.getBooleanExtra("RET_VIBRATION", false);
                    set_alarm_list(alarm_hour, alarm_minute, comment, uri, repeat, snooze, vibration);
                }
                break;

            case REQUEST_CODE_ALARM_CONFIG_EXISTED:

                if (resultCode == Activity.RESULT_OK) {
                    int alarm_hour = data.getIntExtra("RET_HOUR", 0);
                    int alarm_minute = data.getIntExtra("RET_MINUTE", 0);
                    String comment = data.getStringExtra("RET_COMMENT");
                    Uri uri = data.getParcelableExtra("RET_URI");
                    int repeat = data.getIntExtra("RET_REPEAT", 0x00);
                    int snooze = data.getIntExtra("RET_SNOOZE", 0);
                    Boolean vibration = data.getBooleanExtra("RET_VIBRATION", false);
                    set_alarm_list(alarm_hour, alarm_minute, comment, uri, repeat, snooze, vibration);
                }
                break;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity().getApplication(),alarm_config.class);

        int alarm_hour = 1;
        int alarm_minute = 2;;
        intent.putExtra("HOUR",alarm_hour);
        intent.putExtra("MINUTE",alarm_minute);
        intent.putExtra("REPEAT",0x00);
        intent.putExtra("COMMENT","");
        intent.putExtra("URI","");
        intent.putExtra("VOLUME",8);
        intent.putExtra("SNOOZE",0);
        intent.putExtra("VIBRATION",false);
        startActivityForResult(intent,REQUEST_CODE_ALARM_CONFIG_EXISTED);
        Toast.makeText(getActivity().getApplication(),"タップ", Toast.LENGTH_SHORT).show();
    }
}

