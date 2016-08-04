package com.android.hyoonseol.imagecollector.api;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences 매니저
 * Created by Administrator on 2016-08-04.
 */

public class ICPreference {

    private static ICPreference sInstance;
    private static SharedPreferences mPref;

    private ICPreference(Context context) {
        mPref = context.getSharedPreferences("ic_pref", Context.MODE_PRIVATE);
    }

    public synchronized static ICPreference getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ICPreference(context);
        }
        return sInstance;
    }

    public String getString(String key, String def) {
        return mPref.getString(key, def);
    }

    public long getLong(String key, long def) {
        return mPref.getLong(key, def);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.remove(key);
        editor.commit();
    }
}
