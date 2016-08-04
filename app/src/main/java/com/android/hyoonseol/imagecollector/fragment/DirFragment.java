package com.android.hyoonseol.imagecollector.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.hyoonseol.imagecollector.ImageActivity;
import com.android.hyoonseol.imagecollector.R;
import com.android.hyoonseol.imagecollector.api.ICApi;
import com.android.hyoonseol.imagecollector.helper.LocalParser;
import com.android.hyoonseol.imagecollector.model.Image;
import com.android.hyoonseol.imagecollector.util.FileCompare;
import com.android.hyoonseol.imagecollector.util.ICUtils;
import com.android.hyoonseol.imagecollector.util.Log;

import java.io.File;
import java.util.Arrays;

/**
 * Created by Administrator on 2016-07-29.
 */

public class DirFragment extends BaseFragment {

    private static final String TAG = "DirFragment";

    @Override
    protected void setSortType() {
        mSortType = ICApi.SORT_DATE;
        setSort("날짜순");
    }

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dir, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProgress(View.VISIBLE);
        loadImageFileList(false);
    }

    private void loadImageFileList(boolean isRefresh) {
        File dir = new File(ICUtils.getImageFolderPath());
        File[] fileArray = dir.listFiles();

        if (fileArray == null || fileArray.length == 0) {
            showProgress(View.GONE);
            showEmptyView();
        } else {
            if (mSortType == ICApi.SORT_NAME) {
                showProgress(View.GONE);
                //TODO.
            } else if (mSortType == ICApi.SORT_DATE) {
                fileArray = sortDate(fileArray);
                showProgress(View.GONE);
                showImageList(new LocalParser().getICModelList(fileArray, mSortType, true), isRefresh);
            } else {
                showProgress(View.GONE);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult requestCode = " + requestCode + ", resultCode = " + resultCode);

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_LOCAL_DELETE) {
                Toast.makeText(getActivity(), "이미지 삭제가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            }
            loadImageFileList(true);
        }
    }

    @Override
    public void onClick(View view, Image image) {
        Intent intent = new Intent(getActivity(), ImageActivity.class);
        intent.putExtra(ImageActivity.EXTRA_IS_LOCAL, true);
        intent.putExtra(ImageActivity.EXTRA_IMG_URL, image.getPath());
        intent.putExtra(ImageActivity.EXTRA_TITLE, image.getTitle());
        getActivity().startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onLongClick(View view, Image image) {

    }

    @Override
    public void onRequestMore() {

    }
}
