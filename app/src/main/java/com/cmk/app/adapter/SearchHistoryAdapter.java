package com.cmk.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.cmk.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk on 2018/11/27.
 */

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryVH> {

    private Context mContext;
    private final List<String> mData;
    private OnRemoveListener removeListener;

    public SearchHistoryAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public SearchHistoryVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_search_history, parent, false);
        return new SearchHistoryVH(view);
    }

    @Override
    public void onBindViewHolder(SearchHistoryVH holder, final int position) {
        holder.tvRecord.setText(mData.get(position));
        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(position);
                removeListener.onRemove(mData.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setList(List<String> list) {
        mData.clear();
        append(list);
    }

    public void append(List<String> list) {
        int startPosition = mData.size();
        int content = list.size();
        mData.addAll(list);
        if (startPosition > 0 && content > 0) {
            notifyItemInserted(startPosition);
        } else {
            notifyDataSetChanged();
        }
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void setOnRemoveListener(OnRemoveListener listener) {
        this.removeListener = listener;
    }

    public interface OnRemoveListener {
        void onRemove(String keyWord);
    }

    static class SearchHistoryVH extends RecyclerView.ViewHolder {

        private final ImageView ivRemove;
        private final TextView tvRecord;

        public SearchHistoryVH(View itemView) {
            super(itemView);
            tvRecord = itemView.findViewById(R.id.tv_record);
            ivRemove = itemView.findViewById(R.id.iv_remove);
        }
    }
}
