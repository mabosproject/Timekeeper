package mabo_com.timekeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by masaki on 2018/01/20.
 */

public class AlarmListAdapter extends BaseAdapter {

    private Context context;
    private List<Alarm_list_structure> items;
    Alarm_list_structure alarm_list_structure;

    public AlarmListAdapter(Context context, List<Alarm_list_structure> items) {
        this.context = context;
        this.items = items;
    }

    // Listの要素数を返す
    @Override
    public int getCount() {
        return items.size();
    }

    // indexやオブジェクトを返す
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    // IDを他のindexに返す
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.alarm_list_row, parent, false);

        alarm_list_structure = items.get(position);

        // 取得した各データを各TextViewにセット
        int hour = alarm_list_structure.getHour();
        int minute = alarm_list_structure.getMinute();
        String hour_min =String.format("%02d",hour)+":"+String.format("%02d",minute);
        ((TextView)convertView.findViewById(R.id.alarm_list_time)).setText(hour_min);

        int repeat_option = alarm_list_structure.getRepeat();
        setRepeatText(convertView,repeat_option);

        String comment = alarm_list_structure.getComment();
        ((TextView)convertView.findViewById(R.id.alarm_list_comment)).setText(comment);

        return convertView;
    }

    private void setRepeatText(View convertView,int repeat_option){
        if((repeat_option & 0x40) == 0x40){
            ((TextView)convertView.findViewById(R.id.sun)).setText(R.string.sun);
            (convertView.findViewById(R.id.sun)).setPadding(0,0,10,0);
        }else{
            ((TextView)convertView.findViewById(R.id.sun)).setText("");
            (convertView.findViewById(R.id.sun)).setPadding(0,0,0,0);
        }
        if((repeat_option & 0x20) == 0x20){
            ((TextView)convertView.findViewById(R.id.mon)).setText(R.string.mon);
            (convertView.findViewById(R.id.mon)).setPadding(0,0,10,0);
        }else{
            ((TextView)convertView.findViewById(R.id.mon)).setText("");
            (convertView.findViewById(R.id.mon)).setPadding(0,0,0,0);
        }
        if((repeat_option & 0x10) == 0x10){
            ((TextView)convertView.findViewById(R.id.tue)).setText(R.string.tue);
            (convertView.findViewById(R.id.tue)).setPadding(0,0,10,0);
        }else{
            ((TextView)convertView.findViewById(R.id.tue)).setText("");
            (convertView.findViewById(R.id.tue)).setPadding(0,0,0,0);
        }
        if((repeat_option & 0x08) == 0x08){
            ((TextView)convertView.findViewById(R.id.wed)).setText(R.string.wed);
            (convertView.findViewById(R.id.wed)).setPadding(0,0,10,0);
        }else{
            ((TextView)convertView.findViewById(R.id.wed)).setText("");
            (convertView.findViewById(R.id.wed)).setPadding(0,0,0,0);
        }
        if((repeat_option & 0x04) == 0x04){
            ((TextView)convertView.findViewById(R.id.thu)).setText(R.string.thu);
            (convertView.findViewById(R.id.thu)).setPadding(0,0,10,0);
        }else{
            ((TextView)convertView.findViewById(R.id.thu)).setText("");
            (convertView.findViewById(R.id.thu)).setPadding(0,0,0,0);
        }
        if((repeat_option & 0x02) == 0x02){
            ((TextView)convertView.findViewById(R.id.fri)).setText(R.string.fri);
            (convertView.findViewById(R.id.fri)).setPadding(0,0,10,0);
        }else{
            ((TextView)convertView.findViewById(R.id.fri)).setText("");
            (convertView.findViewById(R.id.fri)).setPadding(0,0,0,0);
        }
        if((repeat_option & 0x01) == 0x01){
            ((TextView)convertView.findViewById(R.id.sat)).setText(R.string.sat);
        }else{
            ((TextView)convertView.findViewById(R.id.sat)).setText("");
        }
        if(repeat_option == 0x00){
            ((TextView)convertView.findViewById(R.id.no_repeat_text)).setText(R.string.no_repeat);
        }else{
            ((TextView)convertView.findViewById(R.id.no_repeat_text)).setText("");
        }

    }
}