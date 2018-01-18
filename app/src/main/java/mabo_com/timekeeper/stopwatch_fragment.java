package mabo_com.timekeeper;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by masaki on 2018/01/01.
 */

public class stopwatch_fragment extends android.support.v4.app.Fragment {
    private static final String TAG = "StopwatchTabFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.stopwatch_tab,container,false);
        return view;
    }
}
