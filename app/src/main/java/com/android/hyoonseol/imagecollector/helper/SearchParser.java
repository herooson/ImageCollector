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
 * 이미지리스트 데이터 파서
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

                    // 날짜 순인 경우 헤더뷰 추가
                    if (sortType.equals(ICApi.SORT_DATE)) {
                        String itemDate = jsonObject.optString("pubDate").substring(0, 8);
                        if (!date.equals(itemDate)) {
                            // 기존 날짜까지의 데이터는 별도로 추가
                            if (imageList != null && imageList.size() > 0) {
                                ICModelList.add(new ICModel(ViewType.CONTENT, imageList));
                                imageList = new ArrayList<>();
                            }
                            ICModelList.add(new ICModel(ViewType.DATE, itemDate));
                            date = itemDate;
                        }
                    }

                    String title = ICUtils.removeHtmlTag(jsonObject.optString("title"));
                    imageList.add(new Image(title, jsonObject.optString("image")));

                    if (imageList.size() == NUM_ROW_ITEM || (i + 1) == jsonArray.length()) {
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
