package com.android.hyoonseol.imagecollector.helper;

import com.android.hyoonseol.imagecollector.api.ICApi;
import com.android.hyoonseol.imagecollector.model.ICModel;
import com.android.hyoonseol.imagecollector.model.Image;
import com.android.hyoonseol.imagecollector.model.ViewType;
import com.android.hyoonseol.imagecollector.util.ICUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 보관함 데이터 파서
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
                char c = '\0';
                List<Image> imageList = new ArrayList<>();
                for (int i = 0; i < fileArray.length; i++) {

                    // 날짜 순인 경우 헤더뷰 추가
                    if (sortType.equals(ICApi.SORT_DATE)) {
                        String itemDate = new SimpleDateFormat("yyyyMMdd").format(new Date(fileArray[i].lastModified()));
                        if (!date.equals(itemDate)) {
                            // 기존 날짜까지의 데이터는 별도로 추가
                            if (imageList != null && imageList.size() > 0) {
                                ICModelList.add(new ICModel(ViewType.CONTENT, imageList));
                                imageList = new ArrayList<>();
                            }
                            ICModelList.add(new ICModel(ViewType.DATE, itemDate));
                            date = itemDate;
                        }
                    } else if (sortType.equals(ICApi.SORT_NAME)) {
                        char itemChar = ICUtils.getFirstElement(fileArray[i].getName().charAt(0));
                        if (c != itemChar) {
                            // 기존 글자까지의 데이터는 별도로 추가
                            if (imageList != null && imageList.size() > 0) {
                                ICModelList.add(new ICModel(ViewType.CONTENT, imageList));
                                imageList = new ArrayList<>();
                            }
                            ICModelList.add(new ICModel(ViewType.DATE, itemChar + ""));
                            c = itemChar;
                        }
                    }

                    imageList.add(new Image(fileArray[i].getName(), fileArray[i].getAbsolutePath()));

                    if (imageList.size() == NUM_ROW_ITEM || (i + 1) == fileArray.length) {
                        ICModelList.add(new ICModel(ViewType.CONTENT, imageList));
                        imageList = new ArrayList<>();
                    }
                }
            }
        }
        return ICModelList;
    }
}
