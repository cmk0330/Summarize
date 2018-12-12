package com.cmk.app.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import com.cmk.app.R;

import java.io.File;

/**
 * Created by cmk on 2018/11/29.
 */

public class StatusBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusbar);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup contentView = window.getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
        contentView.getChildAt(0).setFitsSystemWindows(false);


        SharedPreferences sp = getSharedPreferences("sss", MODE_PRIVATE);
    }

    ArrayMap<File, SharedPreferences> sSharedPrefsCache;
}
