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
import android.widget.Button;

import com.android.hyoonseol.imagecollector.R;
import com.android.hyoonseol.imagecollector.adapter.ICAdapter;
import com.android.hyoonseol.imagecollector.model.ICModel;
import com.android.hyoonseol.imagecollector.util.ICUtils;

import java.util.List;

/**
 * Created by Administrator on 2016-08-03.
 */

public abstract class BaseFragment extends Fragment implements IFragment, ICAdapter.OnImgClickListener, ICAdapter.OnImgLongClickListener, ICAdapter.OnMoreListener {

    protected String mSortType;

    private RecyclerView mRecyclerView;
    private ViewStub mEmptyVIewStub;
    private View mEmptyView;
    private View mProg;
    private Button mSort;

    protected ICAdapter mSearchAdater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSortType();
    }

    protected abstract void setSortType();

    protected void setSort(String sort) {
        if (mSort != null) {
            mSort.setText(sort);
        }
    }

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
        mProg = view.findViewById(R.id.pb_loading);
        mSort = (Button)view.findViewById(R.id.btn_sort);
    }

    protected void showProgress(int visibility) {
        mProg.setVisibility(visibility);
    }

    protected void showImageList(List<ICModel> ICModelList, boolean isRefresh) {
        if (mSearchAdater == null) {
            mSearchAdater = new ICAdapter(getActivity(), ICModelList, this, this, this);
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
