package com.cmk.component_base.mvp.base;

import android.content.Context;

public interface BaseView {

    /**
     * 显示正在夹在的view
     */
    void showLoading();

    /**
     * 关闭正在加载的view
     */
    void hideLoading();

    /**
     * 显示提示
     */
    void showToast(String str);

    /**
     * 显示请求错误提示
     */
    void showErr();

    /**
     * 获取上下文
     */
    Context getContext();
}
