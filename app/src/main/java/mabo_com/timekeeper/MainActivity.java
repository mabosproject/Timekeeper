package mabo_com.timekeeper;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

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

    private void setupViewpager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new alarm_fragment(),"Alarm");
        adapter.addFragment(new stopwatch_fragment(),"Stopwatch");
        adapter.addFragment(new timer_fragment(),"Timer");
        viewPager.setAdapter(adapter);
    }

    public void add_alarm(View view) {
        Intent intent = new Intent(getApplication(),alarm_config.class);
        startActivity(intent);
    }
}
