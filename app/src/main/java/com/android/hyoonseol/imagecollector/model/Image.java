package com.android.hyoonseol.imagecollector.model;

/**
 * 이미지 기본 정보를 포함하는 데이터 모델
 * Created by Administrator on 2016-08-03.
 */

public class Image {

    private String mTitle;
    private String mPath;

    public Image(String title, String path) {
        mTitle = title;
        mPath = path;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPath() {
        return mPath;
    }
}
