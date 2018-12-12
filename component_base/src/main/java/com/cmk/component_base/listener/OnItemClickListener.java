package com.cmk.component_base.listener;

import android.view.View;

/**
 * Created by cmk on 2018/11/28.
 */

public interface OnItemClickListener<T> {

    void onItemClick(View view, int position, T t);
}
