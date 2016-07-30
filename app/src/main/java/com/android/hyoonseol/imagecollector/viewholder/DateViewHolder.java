package com.android.hyoonseol.imagecollector.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.hyoonseol.imagecollector.R;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2016-07-31.
 */

public class DateViewHolder extends RecyclerView.ViewHolder {

    private TextView mDate;

    public DateViewHolder(View itemView) {
        super(itemView);
        mDate = (TextView)itemView.findViewById(R.id.tv_date);
    }

    public void onBindViewHolder(String date) {
        mDate.setText(date);
    }
}
