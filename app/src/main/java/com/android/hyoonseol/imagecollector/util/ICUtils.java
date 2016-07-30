package com.android.hyoonseol.imagecollector.util;

import com.android.hyoonseol.imagecollector.model.Search;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-31.
 */

public class ICUtils {

    public static List<Search> concatList(List<Search>... arrs) {
        List<Search> result = new ArrayList<>();
        for (List<Search> arr : arrs) {
            for (int i = 0; i < arr.size(); i++) {
                result.add(arr.get(i));
            }
        }
        return result;
    }
}
