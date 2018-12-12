package com.cmk.app.presenter;

import com.cmk.app.model.TestModel;
import com.cmk.component_base.mvp.base.BasePresenter;
import com.cmk.component_base.mvp.base.CallBackListener;
import com.cmk.app.view.TestView;

public class TestPresenter extends BasePresenter<TestView> {
    /**
     * 获取网络数据
     *
     * @param params 参数
     */
    public void getData(String params) {
        if (!isViewAttached()) {
            // 如果没有view引用就不需要加载数据
            return;
        }

        // 显示正在加载dialog
        getView().showLoading();

        // 调用Model请求数据
        TestModel.getNetData(params, new CallBackListener<String>() {
            @Override
            public void onSuccess(String data) {
                //调用view接口显示数据
                if (isViewAttached()) {
                    getView().showData(data);
                }
            }

            @Override
            public void onFailure(String msg) {
                //调用view接口提示失败信息
                if (isViewAttached()) {
                    getView().showToast(msg);
                }
            }

            @Override
            public void onError() {
                //调用view接口提示请求异常
                if (isViewAttached()) {
                    getView().showErr();
                }
            }

            @Override
            public void onComplete() {
                if (isViewAttached()) {
                    getView().hideLoading();
                }
            }
        });
    }

    @Override
    public TestView getView() {
        return super.getView();
    }
}
