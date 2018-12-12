package com.cmk.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.cmk.app.R;
import com.cmk.app.dialog.SearchDialogFragment;


/**
 * Created by cmk on 2018/11/23.
 */

public class SearchDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dialog);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        ImageView ivSearch = findViewById(R.id.iv_search);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchDialogFragment fragment = SearchDialogFragment.newInstance();
                fragment.show(getFragmentManager(), "");
            }
        });
    }
}
