package com.android.hyoonseol.imagecollector.helper;

import com.android.hyoonseol.imagecollector.api.ICApi;
import com.android.hyoonseol.imagecollector.model.ICModel;
import com.android.hyoonseol.imagecollector.model.Image;
import com.android.hyoonseol.imagecollector.model.ViewType;
import com.android.hyoonseol.imagecollector.util.ICUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-31.
 */

public class SearchParser implements IParser {

    @Override
    public List<ICModel> getICModelList(Object object, String sortType, boolean isLast) {
        List<ICModel> ICModelList = null;

        if (object != null && object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray)object;

            if (jsonArray.length() > 0) {
                ICModelList = new ArrayList<>();

                String date = "";
                List<Image> imageList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    String title = ICUtils.removeHtmlTag(jsonObject.optString("title"));
                    imageList.add(new Image(title, jsonObject.optString("image")));

                    if ((i + 1) % NUM_ROW_ITEM == 0 || (i + 1) == jsonArray.length()) {
                        if (sortType.equals(ICApi.SORT_DATE)) {
                            String itemDate = jsonObject.optString("pubDate").substring(0, 8);
                            if (!date.equals(itemDate)) {
                                ICModelList.add(new ICModel(ViewType.DATE, itemDate));
                            }
                        }
                        ICModelList.add(new ICModel(ViewType.CONTENT, imageList));
                        imageList = new ArrayList<>();
                    }
                }
                if (!isLast) {
                    ICModelList.add(new ICModel(ViewType.MORE));
                }
            }
        }
        return ICModelList;
    }

}
