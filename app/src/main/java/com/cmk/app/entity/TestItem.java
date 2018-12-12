package com.cmk.app.entity;

import android.app.Activity;


import com.cmk.app.activity.SearchDialogActivity;
import com.cmk.app.activity.StatusBarActivity;
import com.cmk.app.activity.TestActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cmk on 2018/11/28.
 */

public class TestItem {


    public final static List<Map<String, Activity>> item = new ArrayList<>();
    public final static String[] names = {"搜索dialog", "状态栏", "mvpDemo"};
    public final static Class[] activitys = {SearchDialogActivity.class, StatusBarActivity.class, TestActivity.class};

    public static void addActivityItem(String name, Activity activity) {
        Map<String, Activity> map = new HashMap<>();
        map.put(name, activity);
        item.add(map);
    }


}
