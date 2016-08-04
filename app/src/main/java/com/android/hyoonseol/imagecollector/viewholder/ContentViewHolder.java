package com.android.hyoonseol.imagecollector.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.hyoonseol.imagecollector.R;
import com.android.hyoonseol.imagecollector.adapter.ICAdapter;
import com.android.hyoonseol.imagecollector.helper.SearchParser;
import com.android.hyoonseol.imagecollector.model.Image;
import com.android.hyoonseol.imagecollector.util.ICUtils;
import com.android.hyoonseol.imagecollector.view.ICImageView;

import java.util.List;

/**
 * 이미지 컨터이너가 포함되는 뷰홀더
 * Created by Administrator on 2016-07-31.
 */

public class ContentViewHolder extends RecyclerView.ViewHolder {

    private ICImageView mImage1, mImage2, mImage3;
    private int mDisplayWidth;

    private ICAdapter.OnImgClickListener mImgClickListener;
    private ICAdapter.OnImgLongClickListener mImgLongClickListener;

    public ContentViewHolder(Context context, View itemView, ICAdapter.OnImgClickListener imgClickListener, ICAdapter.OnImgLongClickListener imgLongClickListener) {
        super(itemView);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mDisplayWidth = ICUtils.getDisPlayWidth(context);

        mImage1 = (ICImageView)itemView.findViewById(R.id.ic_image1);
        mImage2 = (ICImageView)itemView.findViewById(R.id.ic_image2);
        mImage3 = (ICImageView)itemView.findViewById(R.id.ic_image3);

        setChildHeight(mImage1);
        setChildHeight(mImage2);
        setChildHeight(mImage3);

        mImgClickListener = imgClickListener;
        mImgLongClickListener = imgLongClickListener;
    }

    private void setChildHeight(View view) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.height = mDisplayWidth / SearchParser.NUM_ROW_ITEM;
        view.setLayoutParams(params);
    }

    public void onBindViewHolder(List<Image> imageList) {
        if (imageList != null && imageList.size() > 0) {
            setData(mImage1, imageList.get(0));

            if (imageList.size() > 1) {
                mImage2.setVisibility(View.VISIBLE);
                setData(mImage2, imageList.get(1));

                if (imageList.size() > 2) {
                    mImage3.setVisibility(View.VISIBLE);
                    setData(mImage3, imageList.get(2));
                } else {
                    mImage3.setVisibility(View.INVISIBLE);
                }
            } else {
                mImage2.setVisibility(View.INVISIBLE);
                mImage3.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void setData(ICImageView view, final Image image) {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImgClickListener != null) {
                    mImgClickListener.onClick(view, image);
                }
            }
        };
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mImgLongClickListener != null) {
                    mImgLongClickListener.onLongClick(view, image);
                }
                return false;
            }
        };
        view.setData(image, onClickListener, onLongClickListener);
    }

}
