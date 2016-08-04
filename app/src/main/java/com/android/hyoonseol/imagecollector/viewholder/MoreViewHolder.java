package com.android.hyoonseol.imagecollector.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.hyoonseol.imagecollector.R;
import com.android.hyoonseol.imagecollector.adapter.ICAdapter;

/**
 * Created by Administrator on 2016-07-31.
 */

public class MoreViewHolder extends RecyclerView.ViewHolder {

    public MoreViewHolder(View itemView) {
        super(itemView);
    }

    public void onBindViewHolder(ICAdapter.OnMoreListener moreListener) {
        if (moreListener != null) {
            moreListener.onRequestMore();
        }
    }
}
