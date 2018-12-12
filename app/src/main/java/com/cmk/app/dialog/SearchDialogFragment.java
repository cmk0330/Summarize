package com.cmk.app.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmk.app.R;
import com.cmk.app.adapter.SearchHistoryAdapter;
import com.cmk.app.util.KeyboardUtils;
import com.cmk.app.util.SearchHistoryDB;
import com.cmk.app.util.CircularRevealAnim;
import com.cmk.component_base.widget.CircleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk on 2018/11/23.
 */

public class SearchDialogFragment extends DialogFragment implements View.OnClickListener, CircularRevealAnim.AnimListener, Dialog.OnKeyListener,
        ViewTreeObserver.OnPreDrawListener, TextWatcher, SearchHistoryAdapter.OnRemoveListener {


    private EditText etKey;
    private View view;
    private FrameLayout flSearch;
    private CircularRevealAnim circularRevealAnim;
    private Context mContext;
    private SearchHistoryDB historyDB;
    private SearchHistoryAdapter mAdapter;
    private View searchUnderLine;
    private ArrayList<String> historyList;
    private List<String> allHistoryList;

    public static SearchDialogFragment newInstance() {
        SearchDialogFragment fragment = new SearchDialogFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.SearchDialog);
    }


    @Override
    public void onStart() {
        super.onStart();
        initDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dialog_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView ivBack = view.findViewById(R.id.iv_back);
        etKey = view.findViewById(R.id.et_key);
        flSearch = view.findViewById(R.id.fl_search);
        searchUnderLine = view.findViewById(R.id.search_underline);
        View viewOutSide = view.findViewById(R.id.view_outside);
        TextView tvRemoveAll = view.findViewById(R.id.tv_remove_all);
        RecyclerView mRecycleView = view.findViewById(R.id.recycler_view);
        CircleView cvTest = view.findViewById(R.id.cv_test);

        ivBack.setOnClickListener(this);
        flSearch.setOnClickListener(this);
        viewOutSide.setOnClickListener(this);
        tvRemoveAll.setOnClickListener(this);

        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new SearchHistoryAdapter(mContext);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setOnRemoveListener(this);

        circularRevealAnim = new CircularRevealAnim();
        circularRevealAnim.setAnimListener(this);

        getDialog().setOnKeyListener(this);

        etKey.addTextChangedListener(this);

        flSearch.getViewTreeObserver().addOnPreDrawListener(this); //绘制监听

        historyDB = new SearchHistoryDB(mContext, SearchHistoryDB.DB_NAME, null, 1);
        historyList = new ArrayList<>();
        allHistoryList = historyDB.queryAllHistory();
        setAllHistorys();
        mAdapter.setList(historyList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_search:
                if (isVisible()) {
                    search();
                } else {
                    showAnim();
                }
                break;
            case R.id.iv_back:
                //circularRevealAnim.hide(flSearch, this.view);
                hideAnim();
                break;
            case R.id.view_outside:
                hideAnim();
                break;
            case R.id.tv_remove_all:
                hideAnim();
                historyDB.removeAllHistory();
                mAdapter.removeAll();
                break;
            default:
                break;
        }
    }

    private void showAnim() {
        KeyboardUtils.openKeyboard(mContext, etKey);
        circularRevealAnim.show(flSearch, view);
    }

    private void hideAnim() {
        KeyboardUtils.closeKeyboard(mContext, etKey);
        circularRevealAnim.hide(flSearch, view);
    }

    private void search() {
        String keyWord = etKey.getText().toString();
        if (TextUtils.isEmpty(keyWord.trim())) {
            Toast.makeText(mContext, "请输入关键字", Toast.LENGTH_SHORT).show();
        } else {
            historyDB.insertHistory(keyWord);
        }
    }

    private void checkHistorySize() {
        if (historyList.size() < 1) {
            searchUnderLine.setVisibility(View.GONE);
        } else {
            searchUnderLine.setVisibility(View.VISIBLE);
        }
    }

    private void setAllHistorys() {
        historyList.clear();
        historyList.addAll(allHistoryList);
        checkHistorySize();
    }

    private void setKeyWork(String keyWork) {
        historyList.clear();
        for (String s : allHistoryList) {
            if (s.contains(keyWork)) {
                historyList.add(s);
            }
        }
        mAdapter.setList(historyList);
        checkHistorySize();
    }

    private void initDialog() {
        Window window = getDialog().getWindow();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = (int) (dm.widthPixels * 0.95);
        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.TOP);
        window.setWindowAnimations(R.style.DialogEmptyAnimation);
    }

    @Override
    public void onRemove(String keyWord) {
        historyDB.deleteHistory(keyWord);
    }

    @Override
    public void onShowAnimationEnd() {
        if (isVisible()) {
            KeyboardUtils.openKeyboard(mContext, etKey);
        }
    }

    @Override
    public void onHideAnimationEnd() {
        etKey.setText("");
        dismiss();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String keyWord = editable.toString().trim();
        if (TextUtils.isEmpty(keyWord)) {
            setAllHistorys();
            mAdapter.setList(historyList);
        } else {
            setKeyWork(keyWord);
        }
    }

    @Override
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
            hideAnim();
        } else if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            search();
        }
        return false;
    }

    @Override
    public boolean onPreDraw() {
        flSearch.getViewTreeObserver().removeOnPreDrawListener(this);
        Log.e("大小=======", "宽" + this.view.getWidth() + "高度" + this.view.getHeight());
        circularRevealAnim.show(flSearch, view);
        return true;
    }

}
