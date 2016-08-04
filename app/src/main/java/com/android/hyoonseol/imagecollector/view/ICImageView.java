package com.android.hyoonseol.imagecollector.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hyoonseol.imagecollector.R;
import com.android.hyoonseol.imagecollector.model.Image;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

/**
 * 리스트에 노출되는 이미지 컨테이너 뷰
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

    public void setData(Image image, OnClickListener onClickListener, OnLongClickListener onLongClickListener) {
        Glide.with(mContext).load(image.getPath()).into(mImage);
        mText.setText(image.getTitle());
        setOnClickListener(onClickListener);
        setOnLongClickListener(onLongClickListener);
    }
}
