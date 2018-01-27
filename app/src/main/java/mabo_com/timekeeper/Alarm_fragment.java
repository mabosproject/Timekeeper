package mabo_com.timekeeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by masaki on 2018/01/01.
 */

public class Alarm_fragment extends android.support.v4.app.Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final String TAG = "AlarmTabFragment";
    static final int REQUEST_CODE_ALARM_CONFIG = 15;
    static final int REQUEST_CODE_ALARM_CONFIG_EXISTED = 16;

    private AlarmListAdapter alarmListAdapter;
    private Alarm_DB_adapter alarm_db_adapter;
    private List<Alarm_list_structure> items;
    private ListView listView;
    protected Alarm_list_structure alarm_list_structure;

    // 参照するDBのカラム：ID,品名,産地,個数,単価の全部なのでnullを指定
    private String[] columns = null;

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

        // DBAdapterのコンストラクタ呼び出し
        alarm_db_adapter = new Alarm_DB_adapter(getContext());

        // itemsのArrayList生成
        items = new ArrayList<>();

        alarmListAdapter = new AlarmListAdapter(getContext(),items);

        listView =view.findViewById(R.id.alarm_list);

        loadMyList();   // DBを読み込む＆更新する処理

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity().getApplication(),Alarm_config.class);
        Calendar cal = Calendar.getInstance();
        int alarm_hour = cal.get(Calendar.HOUR_OF_DAY);
        int alarm_minute = cal.get(Calendar.MINUTE);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        intent.putExtra("HOUR",alarm_hour);
        intent.putExtra("MINUTE",alarm_minute);
        intent.putExtra("REPEAT",0x00);
        intent.putExtra("COMMENT","");
        intent.putExtra("URI",uri.toString());
        intent.putExtra("VOLUME",8);
        intent.putExtra("SNOOZE",0);
        intent.putExtra("VIBRATION",0);
        startActivityForResult(intent,REQUEST_CODE_ALARM_CONFIG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case REQUEST_CODE_ALARM_CONFIG:

                if (resultCode == Activity.RESULT_OK) {
                    int alarm_hour = data.getIntExtra("RET_HOUR", 0);
                    int alarm_minute = data.getIntExtra("RET_MINUTE", 0);
                    int repeat = data.getIntExtra("RET_REPEAT", 0x00);
                    String comment = data.getStringExtra("RET_COMMENT");
                    String uri = data.getStringExtra("RET_URI");
                    int volume = data.getIntExtra("RET_VOLUME",8);
                    int snooze = data.getIntExtra("RET_SNOOZE", 0);
                    int vibration = data.getIntExtra("RET_VIBRATION", 0);
                    alarm_db_adapter.openDB();
                    alarm_db_adapter.saveDB(alarm_hour,alarm_minute,repeat,comment,uri,volume,snooze,vibration);
                    alarm_db_adapter.closeDB();
                    loadMyList();
                }
                break;

            case REQUEST_CODE_ALARM_CONFIG_EXISTED:

                if (resultCode == Activity.RESULT_OK) {
                    int listId = data.getIntExtra("RET_ID",0);
                    int alarm_hour = data.getIntExtra("RET_HOUR", 0);
                    int alarm_minute = data.getIntExtra("RET_MINUTE", 0);
                    String comment = data.getStringExtra("RET_COMMENT");
                    String uri = data.getStringExtra("RET_URI");
                    int volume = data.getIntExtra("RET_VOLUME",8);
                    int repeat = data.getIntExtra("RET_REPEAT", 0x00);
                    int snooze = data.getIntExtra("RET_SNOOZE", 0);
                    int vibration = data.getIntExtra("RET_VIBRATION", 0);
                    alarm_db_adapter.openDB();
                    alarm_db_adapter.updateDB(String.valueOf(listId),alarm_hour,alarm_minute,repeat,comment,uri,volume,snooze,vibration);
                    alarm_db_adapter.closeDB();
                    loadMyList();
                }
                break;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity().getApplication(),Alarm_config.class);
        alarm_list_structure = items.get(position);
        intent.putExtra("ID",alarm_list_structure.getId());
        intent.putExtra("HOUR",alarm_list_structure.getHour());
        intent.putExtra("MINUTE",alarm_list_structure.getMinute());
        intent.putExtra("REPEAT",alarm_list_structure.getRepeat());
        intent.putExtra("COMMENT",alarm_list_structure.getComment());
        intent.putExtra("URI",alarm_list_structure.getUri());
        intent.putExtra("VOLUME",alarm_list_structure.getVolume());
        intent.putExtra("SNOOZE",alarm_list_structure.getSnooze());
        intent.putExtra("VIBRATION",alarm_list_structure.getVibration());
        startActivityForResult(intent,REQUEST_CODE_ALARM_CONFIG_EXISTED);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        // アラートダイアログ表示
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("削除");
        builder.setMessage("このアラームを削除しますか？");
        // OKの時の処理
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // IDを取得する
                alarm_list_structure = items.get(position);
                int listId = alarm_list_structure.getId();

                alarm_db_adapter.openDB();     // DBの読み込み(読み書きの方)
                alarm_db_adapter.selectDelete(String.valueOf(listId));     // DBから取得したIDが入っているデータを削除する
                alarm_db_adapter.closeDB();    // DBを閉じる
                loadMyList();
            }
        });

        builder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // ダイアログの表示
        AlertDialog dialog = builder.create();
        dialog.show();

        return true;
    }


    private void loadMyList(){

        //リストの更新
        items.clear();

        alarm_db_adapter.openDB();

        Cursor c = alarm_db_adapter.getDB(columns);

        if(c.moveToFirst()){
            do{
                alarm_list_structure = new Alarm_list_structure(
                    c.getInt(0),
                    c.getInt(1),
                    c.getInt(2),
                    c.getInt(3),
                    c.getString(4),
                    c.getString(5),
                    c.getInt(6),
                    c.getInt(7),
                    c.getInt(8)
                );
                items.add(alarm_list_structure);
            }while(c.moveToNext());
        }
        c.close();
        alarm_db_adapter.closeDB();
        listView.setAdapter(alarmListAdapter);
        alarmListAdapter.notifyDataSetChanged();
    }

}

