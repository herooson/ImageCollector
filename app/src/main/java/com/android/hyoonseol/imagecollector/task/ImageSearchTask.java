package com.android.hyoonseol.imagecollector.task;

import android.content.Context;
import android.os.AsyncTask;

import com.android.hyoonseol.imagecollector.api.BaseApi;

/**
 * Created by Administrator on 2016-07-30.
 */

public class ImageSearchTask extends BaseAsyncTask {

    private Context mContext;

    public ImageSearchTask(Context context) {
        mContext = context;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        if (objects == null || objects.length == 0) {
            return null;
        }

        String keyword = (String)objects[0];
        int page = 1;
        String sortType = BaseApi.SORT_ACCU;

        if (objects.length > 1) {
            page = (int)objects[1];

            if (objects.length > 2) {
                sortType = (String)objects[2];
            }
        }
        return new BaseApi(mContext).getSearchResult(keyword, page, sortType);
    }
}
