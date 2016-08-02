package com.android.hyoonseol.imagecollector.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.hyoonseol.imagecollector.ImageActivity;
import com.android.hyoonseol.imagecollector.R;
import com.android.hyoonseol.imagecollector.api.BaseApi;
import com.android.hyoonseol.imagecollector.helper.SearchParser;
import com.android.hyoonseol.imagecollector.model.Image;
import com.android.hyoonseol.imagecollector.task.BaseAsyncTask;
import com.android.hyoonseol.imagecollector.task.ImageSearchTask;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016-07-29.
 */

public class SearchFragment extends BaseFragment {

    private static final String TAG = "SearchFragment";

    private ImageSearchTask mImageSearchTask;
    private int mPage;

    @Override
    protected void setSortType() {
        mSortType = BaseApi.SORT_ACCU;
    }

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchView searchView = (SearchView)view.findViewById(R.id.sv_search);
        searchView.setFocusable(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPage = 1;
                executeSearch(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void executeSearch(String keyword) {
        if (mImageSearchTask != null) {
            mImageSearchTask.cancel(true);
        }
        mImageSearchTask = new ImageSearchTask(getActivity());
        mImageSearchTask.setOnPostExecuteListener(new BaseAsyncTask.OnPostExecuteListener() {
            @Override
            public void onPostExecute(Object result) {
                if (result == null || !(result instanceof JSONObject)) {
                    showEmptyView();
                } else {
                    JSONObject jsonObject = (JSONObject)result;

                    if (jsonObject.optJSONObject("channel") != null
                            && jsonObject.optJSONObject("channel").optJSONArray("item") != null
                            && jsonObject.optJSONObject("channel").optJSONArray("item").length() > 0) {

                        showImageList(new SearchParser().getICModelList(jsonObject.optJSONObject("channel").optJSONArray("item"), mSortType), false);
                    } else {
                        showEmptyView();
                    }
                }
            }
        });
        mImageSearchTask.execute(keyword, mPage, mSortType);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult requestCode = " + requestCode + ", resultCode = " + resultCode);
    }

    @Override
    public void onClick(View view, Image image) {
        Intent intent = new Intent(getActivity(), ImageActivity.class);
        intent.putExtra(ImageActivity.EXTRA_IS_LOCAL, false);
        intent.putExtra(ImageActivity.EXTRA_IMG_URL, image.getPath());
        intent.putExtra(ImageActivity.EXTRA_TITLE, image.getTitle());
        getActivity().startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onLongClick(View view, Image image) {

    }
}
