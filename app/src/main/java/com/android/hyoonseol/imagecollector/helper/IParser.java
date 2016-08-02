package com.android.hyoonseol.imagecollector.helper;

import com.android.hyoonseol.imagecollector.model.ICModel;

import java.util.List;

/**
 * Created by Administrator on 2016-08-03.
 */

public interface IParser {
    int NUM_ROW_ITEM = 3;

    List<ICModel> getICModelList(Object object, String sortType);
}
