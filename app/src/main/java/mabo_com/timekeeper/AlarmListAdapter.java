package mabo_com.timekeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by masaki on 2018/01/20.
 */

public class AlarmListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<Alarm_list_structure> alarm_list;

    public AlarmListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setAlarm_list(ArrayList<Alarm_list_structure> alarm_list) {
        this.alarm_list = alarm_list;
    }

    @Override
    public int getCount() {
        return alarm_list.size();
    }

    @Override
    public Object getItem(int position) {
        return alarm_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alarm_list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.alarm_list_row,parent,false);

        int hour = alarm_list.get(position).getHour();
        int minute = alarm_list.get(position).getMinute();
        String hour_min =String.format("%02d",hour)+":"+String.format("%02d",minute);
        ((TextView)convertView.findViewById(R.id.time)).setText(hour_min);
        int repeat_option = alarm_list.get(position).getRepeat();
        setRepeatText(convertView,repeat_option);

        return convertView;
    }

    private void setRepeatText(View convertView,int repeat_option){
        if((repeat_option & 0x40) == 0x40){
            ((TextView)convertView.findViewById(R.id.sun)).setText(R.string.sun);
        }else{
            ((TextView)convertView.findViewById(R.id.sun)).setText("");
        }
        if((repeat_option & 0x20) == 0x20){
            ((TextView)convertView.findViewById(R.id.mon)).setText(R.string.mon);
        }else{
            ((TextView)convertView.findViewById(R.id.mon)).setText("");
        }
        if((repeat_option & 0x10) == 0x10){
            ((TextView)convertView.findViewById(R.id.tue)).setText(R.string.tue);
        }else{
            ((TextView)convertView.findViewById(R.id.tue)).setText("");
        }
        if((repeat_option & 0x08) == 0x08){
            ((TextView)convertView.findViewById(R.id.wed)).setText(R.string.wed);
        }else{
            ((TextView)convertView.findViewById(R.id.wed)).setText("");
        }
        if((repeat_option & 0x04) == 0x04){
            ((TextView)convertView.findViewById(R.id.thu)).setText(R.string.thu);
        }else{
            ((TextView)convertView.findViewById(R.id.thu)).setText("");
        }
        if((repeat_option & 0x02) == 0x02){
            ((TextView)convertView.findViewById(R.id.fri)).setText(R.string.fri);
        }else{
            ((TextView)convertView.findViewById(R.id.fri)).setText("");
        }
        if((repeat_option & 0x01) == 0x01){
            ((TextView)convertView.findViewById(R.id.sat)).setText(R.string.sat);
        }else{
            ((TextView)convertView.findViewById(R.id.sat)).setText("");
        }

    }
}