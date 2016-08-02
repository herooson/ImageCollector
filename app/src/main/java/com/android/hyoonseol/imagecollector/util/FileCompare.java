package com.android.hyoonseol.imagecollector.util;

import java.io.File;

/**
 * Created by Administrator on 2016-08-03.
 */

public class FileCompare implements Comparable {

    private long mTime;
    private File mFile;

    public FileCompare(File file) {
        mFile = file;
        mTime = file.lastModified();
    }

    public int compareTo(Object object) {
        long time = ((FileCompare) object).mTime;
        return mTime < time ? -1 : mTime == time ? 0 : 1;
    }

    public File getFile() {
        return mFile;
    }
}
