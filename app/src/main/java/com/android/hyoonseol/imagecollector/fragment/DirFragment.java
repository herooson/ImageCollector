package com.android.hyoonseol.imagecollector.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
 * 보관함 프래그먼트
 * Created by Administrator on 2016-07-29.
 */

public class DirFragment extends BaseFragment {

    private static final String TAG = "DirFragment";

    private TextView mInfo;

    @Override
    protected void setSortType() {
        mSortType = ICApi.SORT_DATE;
    }

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dir, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInfo = (TextView)view.findViewById(R.id.tv_info);
        setSort("날짜순");
        showProgress(View.VISIBLE);
        loadImageFileList(false);
    }

    private void loadImageFileList(boolean isRefresh) {
        File dir = new File(ICUtils.getImageFolderPath());
        File[] fileArray = dir.listFiles();

        if (fileArray == null || fileArray.length == 0 || mSortType == null || TextUtils.isEmpty(mSortType)) {
            showProgress(View.GONE);
            showEmptyView();

        } else {
            showProgress(View.GONE);

            if (mSortType == ICApi.SORT_NAME) {
                fileArray = sortName(fileArray);
            } else if (mSortType == ICApi.SORT_DATE) {
                fileArray = sortDate(fileArray);
            }
            hideEmptyView();
            setInfo(fileArray.length, getTotalSize(fileArray));
            showImageList(new LocalParser().getICModelList(fileArray, mSortType, true), isRefresh);
        }
    }

    private long getTotalSize(File[] fileArray) {
        long size = 0;
        for (File file: fileArray) {
            size += file.length();
        }
        return size;
    }

    private void setInfo(int num, long size) {
        mInfo.setText("총 " + num + " 개  총 " + (size / 1000) + " Kb");
    }

    private File[] sortName(File[] fileArray) {
        File[] fileCompareArray = new File[fileArray.length];
        String[] fileNameArray = new String[fileArray.length];
        
        for (int i = 0; i < fileArray.length; i++) {
            fileNameArray[i] = fileArray[i].getName();
        }
        Arrays.sort(fileNameArray);

        for (int i = 0; i < fileNameArray.length; i++) {
            for (int j = 0; j < fileArray.length; j++) {
                if (fileNameArray[i].equals(fileArray[j].getName())) {
                    fileCompareArray[i] = fileArray[j];
                    break;
                }
            }
        }
        return fileCompareArray;
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
    protected int getMenuId() {
        return R.menu.local;
    }

    @Override
    protected PopupMenu.OnMenuItemClickListener getMenuItemClickListener() {
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.date) {
                    mSortType = ICApi.SORT_DATE;
                    setSort("날짜순");
                } else if (id == R.id.name) {
                    mSortType = ICApi.SORT_NAME;
                    setSort("이름순");
                }
                loadImageFileList(true);
                return true;
            }
        };
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
