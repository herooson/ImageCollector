package com.android.hyoonseol.imagecollector.model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016-07-31.
 */

public class Search {

    private ViewType mViewType;
    private String mDate;
    private JSONArray mJsonArray;

    public Search(ViewType viewType, String date) {
        mViewType = viewType;
        mDate = date;
    }

    public Search(ViewType viewType, JSONArray jsonArray) {
        mViewType = viewType;
        mJsonArray = jsonArray;
    }

    public ViewType getViewType() {
        return mViewType;
    }

    public JSONArray getJsonArray() {
        return mJsonArray;
    }

    public String getDate() {
        return mDate;
    }
}
