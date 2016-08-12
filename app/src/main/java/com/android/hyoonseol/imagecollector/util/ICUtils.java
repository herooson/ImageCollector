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
 * 유틸
 * Created by Administrator on 2016-07-31.
 */

public class ICUtils {

    private static final char[] firstSounds = {
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ',
            'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ',
            'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    private static boolean isHangul(char c) {
        if (c < 0xAC00 || c > 0xD7A3) {
            return false;
        }
        return true;
    }

    public static char getFirstElement(char c) {
        if (!isHangul(c)) {
            return c;
        }
        return firstSounds[(c - 0xAC00) / (21 * 28)];
    }

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
        str = Html.fromHtml(str).toString();
        return str.replaceAll("\\<[^>]*>","");
    }
}
