package com.android.hyoonseol.imagecollector.task;

import android.os.AsyncTask;

/**
 * AsyncTask 확장
 * Created by Administrator on 2016-07-30.
 */

public abstract class BaseAsyncTask extends AsyncTask {

    public interface OnPostExecuteListener {
        void onPostExecute(Object result);
    }

    private OnPostExecuteListener mOnPostExecuteListener;

    public void setOnPostExecuteListener(OnPostExecuteListener onPostExecuteListener) {
        mOnPostExecuteListener = onPostExecuteListener;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (mOnPostExecuteListener != null) {
            mOnPostExecuteListener.onPostExecute(o);
        }
    }
}
