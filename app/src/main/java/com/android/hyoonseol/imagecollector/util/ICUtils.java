package com.android.hyoonseol.imagecollector.util;

import android.content.Context;
import android.os.Environment;
import android.text.Html;
import android.view.WindowManager;

import com.android.hyoonseol.imagecollector.model.ICModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-31.
 */

public class ICUtils {

    public static List<ICModel> concatList(List<ICModel>... arrs) {
        List<ICModel> result = new ArrayList<>();
        for (List<ICModel> arr : arrs) {
            for (int i = 0; i < arr.size(); i++) {
                result.add(arr.get(i));
            }
        }
        return result;
    }

    public static int getDisPlayWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static String getImageFolderPath() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return "";
        }
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "ImageCollector");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static String removeHtmlTag(String str) {
        str = Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY).toString();
        return str.replaceAll("\\<[^>]*>","");
    }
}
