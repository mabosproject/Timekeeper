package mabo_com.timekeeper;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    static final int REQUEST_CODE_ALARM_CONFIG = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPageAdapter  mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewpager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_ALARM_CONFIG) {

            if (resultCode == RESULT_OK) {

            }
        }

    }


    private void setupViewpager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new alarm_fragment(),"Alarm");
        adapter.addFragment(new stopwatch_fragment(),"Stopwatch");
        adapter.addFragment(new timer_fragment(),"Timer");
        viewPager.setAdapter(adapter);
    }

    public void add_alarm(View view) {
        Intent intent = new Intent(getApplication(),alarm_config.class);
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        intent.putExtra("HOUR",hour);
        intent.putExtra("MINUTE",minute);
        intent.putExtra("REPEAT",0x00);
        intent.putExtra("COMMENT","");
        intent.putExtra("URI",uri);
        intent.putExtra("VOLUME",8);
        intent.putExtra("SNOOZE",0);
        intent.putExtra("VIBRATION",false);
        startActivityForResult(intent,REQUEST_CODE_ALARM_CONFIG);
    }
}
