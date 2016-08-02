package com.android.hyoonseol.imagecollector.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.android.hyoonseol.imagecollector.ImageActivity;
import com.android.hyoonseol.imagecollector.R;
import com.android.hyoonseol.imagecollector.SearchAdapter;
import com.android.hyoonseol.imagecollector.api.BaseApi;
import com.android.hyoonseol.imagecollector.helper.SearchParser;
import com.android.hyoonseol.imagecollector.model.ICModel;
import com.android.hyoonseol.imagecollector.model.Image;
import com.android.hyoonseol.imagecollector.task.BaseAsyncTask;
import com.android.hyoonseol.imagecollector.task.ImageSearchTask;
import com.android.hyoonseol.imagecollector.util.ICUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016-07-29.
 */

public class SearchFragment extends BaseFragment {

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

                        showImageList(new SearchParser().getICModelList(jsonObject.optJSONObject("channel").optJSONArray("item"), mSortType));
                    } else {
                        showEmptyView();
                    }
                }
            }
        });
        mImageSearchTask.execute(keyword, mPage, mSortType);
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
