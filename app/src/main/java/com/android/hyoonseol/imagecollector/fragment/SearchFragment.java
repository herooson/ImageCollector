package com.android.hyoonseol.imagecollector.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.android.hyoonseol.imagecollector.R;
import com.android.hyoonseol.imagecollector.SearchAdapter;
import com.android.hyoonseol.imagecollector.api.BaseApi;
import com.android.hyoonseol.imagecollector.helper.SearchParser;
import com.android.hyoonseol.imagecollector.model.Search;
import com.android.hyoonseol.imagecollector.task.BaseAsyncTask;
import com.android.hyoonseol.imagecollector.task.ImageSearchTask;
import com.android.hyoonseol.imagecollector.util.ICUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016-07-29.
 */

public class SearchFragment extends Fragment {

    private ImageSearchTask mImageSearchTask;
    private int mPage;
    private String mSortType = BaseApi.SORT_ACCU;

    private RecyclerView mRecyclerView;
    private ViewStub mEmptyVIewStub;
    private View mEmptyView;

    private SearchAdapter mSearchAdater;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_image);
        mEmptyVIewStub = (ViewStub)view.findViewById(R.id.vs_empty);

        SearchView searchView = (SearchView)view.findViewById(R.id.sv_search);
        searchView.setFocusable(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
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
        mImageSearchTask = new ImageSearchTask();
        mImageSearchTask.setOnPostExecuteListener(new BaseAsyncTask.OnPostExecuteListener() {
            @Override
            public void onPostExecute(Object result) {
                if (result == null || !(result instanceof JSONObject)) {
                    showEmptyView();
                } else {
                    JSONObject jsonObject = (JSONObject)result;

                    if (jsonObject.optString("errorType").equals("200") && jsonObject.optJSONObject("channel") != null
                            && jsonObject.optJSONObject("channel").optJSONArray("item") != null
                            && jsonObject.optJSONObject("channel").optJSONArray("item").length() > 0) {

                        showImageList(new SearchParser().getSearchList(jsonObject.optJSONObject("channel").optJSONArray("item")));
                    } else {
                        showEmptyView();
                    }
                }
            }
        });
        mImageSearchTask.execute(keyword, mPage, mSortType);
    }

    private void showImageList(List<Search> searchList) {
        if (mSearchAdater == null) {
            mSearchAdater = new SearchAdapter(getActivity(), searchList);
            mRecyclerView.setAdapter(mSearchAdater);
        } else {
            searchList = ICUtils.concatList(mSearchAdater.getList(), searchList);
            mSearchAdater.setList(searchList);
            mSearchAdater.notifyDataSetChanged();
        }
    }

    private void showEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else if (mEmptyVIewStub != null) {
            mEmptyVIewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
                @Override
                public void onInflate(ViewStub viewStub, View view) {
                    mEmptyView = view;
                }
            });
            mEmptyVIewStub.inflate();
        }
    }
}
