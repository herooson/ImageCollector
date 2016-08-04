package com.android.hyoonseol.imagecollector.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.hyoonseol.imagecollector.ImageActivity;
import com.android.hyoonseol.imagecollector.R;
import com.android.hyoonseol.imagecollector.api.ICApi;
import com.android.hyoonseol.imagecollector.helper.SearchParser;
import com.android.hyoonseol.imagecollector.model.Image;
import com.android.hyoonseol.imagecollector.task.BaseAsyncTask;
import com.android.hyoonseol.imagecollector.task.ImageSearchTask;
import com.android.hyoonseol.imagecollector.util.Log;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016-07-29.
 */

public class SearchFragment extends BaseFragment {

    private static final String TAG = "SearchFragment";

    private ImageSearchTask mImageSearchTask;
    private int mPage;
    private String mQuery;

    @Override
    protected void setSortType() {
        mSortType = ICApi.SORT_ACCU;
        setSort("추천순");
    }

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SearchView searchView = (SearchView)view.findViewById(R.id.sv_search);
        searchView.setFocusable(true);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showProgress(View.VISIBLE);
                mPage = 1;
                mQuery = query;
                executeSearch(true);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void executeSearch(final boolean isRefresh) {
        if (mImageSearchTask != null) {
            mImageSearchTask.cancel(true);
        }
        mImageSearchTask = new ImageSearchTask(getActivity());
        mImageSearchTask.setOnPostExecuteListener(new BaseAsyncTask.OnPostExecuteListener() {
            @Override
            public void onPostExecute(Object result) {
                showProgress(View.GONE);
                if (result == null || !(result instanceof JSONObject)) {
                    showEmptyView();
                } else {
                    JSONObject jsonObject = (JSONObject)result;

                    if (jsonObject.optJSONObject("channel") != null
                            && jsonObject.optJSONObject("channel").optJSONArray("item") != null
                            && jsonObject.optJSONObject("channel").optJSONArray("item").length() > 0) {

                        boolean isLast = false;
                        if (jsonObject.optJSONObject("channel").optString("pageCount") != null) {
                            try {
                                int totalPage = Integer.parseInt(jsonObject.optJSONObject("channel").optString("pageCount"));
                                if (totalPage <= mPage) {
                                    isLast = true;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        showImageList(new SearchParser().getICModelList(jsonObject.optJSONObject("channel").optJSONArray("item"), mSortType, isLast), isRefresh);
                    } else {
                        showEmptyView();
                    }
                }
            }
        });
        mImageSearchTask.execute(mQuery, mPage, mSortType);
    }

    @Override
    protected void showEmptyView() {
        if (mSearchAdater.getList() == null || mSearchAdater.getList().size() == 0) {
            super.showEmptyView();
        }
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

    @Override
    public void onRequestMore() {
        mPage++;
        mSearchAdater.getList().remove(mSearchAdater.getItemCount() - 1);
        executeSearch(false);
    }
}
