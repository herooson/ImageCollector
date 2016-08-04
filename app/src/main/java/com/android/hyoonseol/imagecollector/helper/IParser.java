package com.android.hyoonseol.imagecollector.helper;

import com.android.hyoonseol.imagecollector.model.ICModel;

import java.util.List;

/**
 * 데이터 파서 인터페이스
 * Created by Administrator on 2016-08-03.
 */

public interface IParser {
    int NUM_ROW_ITEM = 3;

    List<ICModel> getICModelList(Object object, String sortType, boolean isLast);
}
