package com.android.hyoonseol.imagecollector.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.android.hyoonseol.imagecollector.R;
import com.android.hyoonseol.imagecollector.adapter.SearchAdapter;
import com.android.hyoonseol.imagecollector.model.ICModel;
import com.android.hyoonseol.imagecollector.util.ICUtils;

import java.util.List;

/**
 * Created by Administrator on 2016-08-03.
 */

public abstract class BaseFragment extends Fragment implements IFragment, SearchAdapter.OnImgClickListener, SearchAdapter.OnImgLongClickListener{

    // TODO.
    // 1. 프로그래스바

    protected String mSortType;

    private RecyclerView mRecyclerView;
    private ViewStub mEmptyVIewStub;
    private View mEmptyView;

    private SearchAdapter mSearchAdater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSortType();
    }

    protected abstract void setSortType();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getContentView(inflater, container);
    }

    protected abstract View getContentView(LayoutInflater inflater, ViewGroup viewGroup);

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_image);
        mEmptyVIewStub = (ViewStub)view.findViewById(R.id.vs_empty);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    protected void showImageList(List<ICModel> ICModelList, boolean isRefresh) {
        if (mSearchAdater == null) {
            mSearchAdater = new SearchAdapter(getActivity(), ICModelList, this, this);
            mRecyclerView.setAdapter(mSearchAdater);
        } else {
            if (!isRefresh) {
                ICModelList = ICUtils.concatList(mSearchAdater.getList(), ICModelList);
            }
            mSearchAdater.setList(ICModelList);
            mSearchAdater.notifyDataSetChanged();
        }
    }

    protected void showEmptyView() {
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
