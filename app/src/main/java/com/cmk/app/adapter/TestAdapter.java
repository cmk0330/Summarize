package com.cmk.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmk.app.R;
import com.cmk.component_base.listener.OnItemClickListener;


/**
 * Created by cmk on 2018/11/28.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestVH> {

    private Context mContext;
    private String[] mData;
    private OnItemClickListener<String> itemClickListener;

    public TestAdapter(Context context, String[] items) {
        this.mContext = context;
        this.mData = items;
    }

    @Override
    public TestVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_test, parent, false);
        return new TestVH(view, mData, itemClickListener);
    }

    @Override
    public void onBindViewHolder(TestVH holder, int position) {
        holder.tvItem.setText(mData[position]);
    }

    @Override
    public int getItemCount() {
        Log.e("大小===", mData.length + "");
        return mData.length;
    }

    public void setData(String[] data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    static class TestVH extends RecyclerView.ViewHolder {

        private final TextView tvItem;
        private int position;

        public TestVH(View itemView, final String[] item, final OnItemClickListener<String> listener) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onItemClick(view, position, item[position]);
                }
            });
        }

        private void setPosition(int position) {
            this.position = position;
        }
    }
}
