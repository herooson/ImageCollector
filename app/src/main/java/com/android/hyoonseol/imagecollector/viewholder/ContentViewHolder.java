package com.android.hyoonseol.imagecollector.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.hyoonseol.imagecollector.R;
import com.android.hyoonseol.imagecollector.view.ICImageView;

import org.json.JSONArray;

/**
 * Created by Administrator on 2016-07-31.
 */

public class ContentViewHolder extends RecyclerView.ViewHolder {

    private ICImageView mImage1, mImage2, mImage3;

    public ContentViewHolder(View itemView) {
        super(itemView);

        mImage1 = (ICImageView)itemView.findViewById(R.id.ic_image1);
        mImage2 = (ICImageView)itemView.findViewById(R.id.ic_image2);
        mImage3 = (ICImageView)itemView.findViewById(R.id.ic_image3);
    }

    public void onBindViewHolder(JSONArray jsonArray) {
        if (jsonArray != null && jsonArray.length() > 0) {
            mImage1.setData(jsonArray.optJSONObject(0));

            if (jsonArray.length() > 1) {
                mImage2.setVisibility(View.VISIBLE);
                mImage2.setData(jsonArray.optJSONObject(1));

                if (jsonArray.length() > 2) {
                    mImage3.setVisibility(View.VISIBLE);
                    mImage3.setData(jsonArray.optJSONObject(2));
                } else {
                    mImage3.setVisibility(View.INVISIBLE);
                }
            } else {
                mImage2.setVisibility(View.INVISIBLE);
                mImage3.setVisibility(View.INVISIBLE);
            }
        }
    }
}
