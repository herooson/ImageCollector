package com.android.hyoonseol.imagecollector.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hyoonseol.imagecollector.R;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016-07-31.
 */

public class ICImageView extends LinearLayout {

    private Context mContext;
    private ImageView mImage;
    private TextView mText;

    public ICImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_item_image, this);
        mContext = context;
        mImage = (ImageView)findViewById(R.id.iv_image);
        mText = (TextView)findViewById(R.id.tv_title);
    }

    public void setData(JSONObject jsonObject) {
        Glide.with(mContext).load(jsonObject.optString("image")).into(mImage);
        mText.setText(jsonObject.optString("title"));
    }
}
