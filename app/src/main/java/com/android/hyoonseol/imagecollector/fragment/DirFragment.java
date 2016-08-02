package com.android.hyoonseol.imagecollector.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.hyoonseol.imagecollector.ImageActivity;
import com.android.hyoonseol.imagecollector.R;
import com.android.hyoonseol.imagecollector.api.BaseApi;
import com.android.hyoonseol.imagecollector.helper.LocalParser;
import com.android.hyoonseol.imagecollector.model.Image;
import com.android.hyoonseol.imagecollector.util.FileCompare;
import com.android.hyoonseol.imagecollector.util.ICUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static android.R.attr.path;

/**
 * Created by Administrator on 2016-07-29.
 */

public class DirFragment extends BaseFragment {

    @Override
    protected void setSortType() {
        mSortType = BaseApi.SORT_DATE;
    }

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dir, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadImageFileList();
    }

    private void loadImageFileList() {
        File dir = new File(ICUtils.getImageFolderPath());
        File[] fileArray = dir.listFiles();

        if (fileArray == null || fileArray.length == 0) {
            showEmptyView();
        } else {
            if (mSortType == BaseApi.SORT_NAME) {
                //TODO.
            } else if (mSortType == BaseApi.SORT_DATE) {
                fileArray = sortDate(fileArray);
                showImageList(new LocalParser().getICModelList(fileArray, mSortType));
            } else {
                showEmptyView();
            }
//              fileArray[i].getTotalSpace()    // TODO. 파일 용량
        }
    }

    private File[] sortDate(File[] fileArray) {
        FileCompare[] fileCompareArray = new FileCompare[fileArray.length];

        for (int i = 0; i < fileArray.length; i++) {
            fileCompareArray[i] = new FileCompare(fileArray[i]);
        }
        Arrays.sort(fileCompareArray);

        for (int i = 0; i < fileArray.length; i++) {
            fileArray[i] = fileCompareArray[i].getFile();
        }
        return fileArray;
    }

    @Override
    public void onClick(View view, Image image) {
        Intent intent = new Intent(getActivity(), ImageActivity.class);
        intent.putExtra(ImageActivity.EXTRA_IS_LOCAL, false);
        intent.putExtra(ImageActivity.EXTRA_IMG_URL, image.getPath());
        intent.putExtra(ImageActivity.EXTRA_TITLE, image.getTitle());
        getActivity().startActivity(intent);
    }

    @Override
    public void onLongClick(View view, Image image) {

    }
}
