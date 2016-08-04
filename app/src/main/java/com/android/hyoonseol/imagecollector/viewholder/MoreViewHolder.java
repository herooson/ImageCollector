package com.android.hyoonseol.imagecollector.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hyoonseol.imagecollector.R;
import com.android.hyoonseol.imagecollector.adapter.ICAdapter;

/**
 * 리스트 더보기 중(api 호출 중)임을 알리는 뷰홀더
 * Created by Administrator on 2016-07-31.
 */

public class MoreViewHolder extends RecyclerView.ViewHolder {

    public MoreViewHolder(View itemView) {
        super(itemView);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void onBindViewHolder(ICAdapter.OnMoreListener moreListener) {
        if (moreListener != null) {
            moreListener.onRequestMore();
        }
    }
}
