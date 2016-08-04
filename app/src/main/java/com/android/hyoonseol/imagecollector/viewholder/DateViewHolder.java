package com.android.hyoonseol.imagecollector.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hyoonseol.imagecollector.R;

import org.w3c.dom.Text;

/**
 * 소팅정보를 노출하는 뷰홀더
 * Created by Administrator on 2016-07-31.
 */

public class DateViewHolder extends RecyclerView.ViewHolder {

    private TextView mDate;

    public DateViewHolder(View itemView) {
        super(itemView);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mDate = (TextView)itemView.findViewById(R.id.tv_date);
    }

    public void onBindViewHolder(String date) {
        mDate.setText(date);
    }
}
