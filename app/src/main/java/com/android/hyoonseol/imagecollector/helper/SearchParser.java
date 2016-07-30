package com.android.hyoonseol.imagecollector.helper;

import android.widget.GridView;

import com.android.hyoonseol.imagecollector.model.Search;
import com.android.hyoonseol.imagecollector.model.ViewType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-31.
 */

public class SearchParser {

    private static final int NUM_ROW_ITEM = 3;

    public List<Search> getSearchList(JSONArray jsonArray) {
        List<Search> searchList = null;

        if (jsonArray != null && jsonArray.length() > 0) {
            searchList = new ArrayList<>();

            String date = "";
            JSONArray rowArray = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);

                if (i % NUM_ROW_ITEM == 0 || i == jsonArray.length() - 1) {
                    String itemDate = jsonObject.optString("pubDate").substring(0, 8);
                    if (!date.equals(itemDate)) {
                        searchList.add(new Search(ViewType.DATE, itemDate));
                    }
                    searchList.add(new Search(ViewType.CONTENT, rowArray));
                } else {
                    rowArray.put(jsonObject);
                    rowArray = new JSONArray();
                }
            }
        }
        return searchList;
    }

}
