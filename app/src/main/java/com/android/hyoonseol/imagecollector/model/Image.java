package com.android.hyoonseol.imagecollector.model;

/**
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
