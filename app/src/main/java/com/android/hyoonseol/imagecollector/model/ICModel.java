package com.android.hyoonseol.imagecollector.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * 리스트 아답터에 전달하기 위한 데이터 모델
 * Created by Administrator on 2016-07-31.
 */

public class ICModel {

    private ViewType mViewType;
    private String mDate;
    private List<Image> mImageList;

    public ICModel(ViewType viewType) {
        mViewType = viewType;
    }

    public ICModel(ViewType viewType, String date) {
        mViewType = viewType;
        mDate = date;
    }

    public ICModel(ViewType viewType, List<Image> imageList) {
        mViewType = viewType;
        mImageList = imageList;
    }

    public ViewType getViewType() {
        return mViewType;
    }

    public List<Image> getImageList() {
        return mImageList;
    }

    public String getDate() {
        return mDate;
    }
}
