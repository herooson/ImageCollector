package com.android.hyoonseol.imagecollector.helper;

import com.android.hyoonseol.imagecollector.model.ICModel;
import com.android.hyoonseol.imagecollector.model.Image;
import com.android.hyoonseol.imagecollector.model.ViewType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-08-03.
 */

public class LocalParser implements IParser {

    @Override
    public List<ICModel> getICModelList(Object object, String sortType, boolean isLast) {
        List<ICModel> ICModelList = null;

        if (object != null && object instanceof File[]) {
            File[] fileArray = (File[]) object;

            if (fileArray.length > 0) {
                ICModelList = new ArrayList<>();

                String date = "";
                List<Image> imageList = new ArrayList<>();
                for (int i = 0; i < fileArray.length; i++) {
                    imageList.add(new Image(fileArray[i].getName(), fileArray[i].getAbsolutePath()));

                    if ((i + 1) % NUM_ROW_ITEM == 0 || (i + 1) == fileArray.length) {
                        //TODO
//                        if (sortType.equals(ICApi.SORT_DATE)) {
//                            String itemDate = jsonObject.optString("pubDate").substring(0, 8);
//                            if (!date.equals(itemDate)) {
//                                ICModelList.add(new ICModel(ViewType.DATE, itemDate));
//                            }
//                        }
                        ICModelList.add(new ICModel(ViewType.CONTENT, imageList));
                        imageList = new ArrayList<>();
                    }
                }
            }
        }
        return ICModelList;
    }
}
