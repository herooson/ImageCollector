package com.android.hyoonseol.imagecollector.helper;

import com.android.hyoonseol.imagecollector.api.BaseApi;
import com.android.hyoonseol.imagecollector.model.ICModel;
import com.android.hyoonseol.imagecollector.model.Image;
import com.android.hyoonseol.imagecollector.model.ViewType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016-07-31.
 */

public class SearchParser implements IParser {

    @Override
    public List<ICModel> getICModelList(Object object, String sortType) {
        List<ICModel> ICModelList = null;

        if (object != null && object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray)object;

            if (jsonArray.length() > 0) {
                ICModelList = new ArrayList<>();

                String date = "";
                List<Image> imageList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    imageList.add(new Image(jsonObject.optString("title"), jsonObject.optString("image")));

                    if ((i + 1) % NUM_ROW_ITEM == 0 || (i + 1) == jsonArray.length()) {
                        if (sortType.equals(BaseApi.SORT_DATE)) {
                            String itemDate = jsonObject.optString("pubDate").substring(0, 8);
                            if (!date.equals(itemDate)) {
                                ICModelList.add(new ICModel(ViewType.DATE, itemDate));
                            }
                        }
                        ICModelList.add(new ICModel(ViewType.CONTENT, imageList));
                        imageList = new ArrayList<>();
                    }
                }
            }
        }
        return ICModelList;
    }

}
