package com.android.hyoonseol.imagecollector.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.hyoonseol.imagecollector.R;
import com.android.hyoonseol.imagecollector.model.ICModel;
import com.android.hyoonseol.imagecollector.model.Image;
import com.android.hyoonseol.imagecollector.model.ViewType;
import com.android.hyoonseol.imagecollector.viewholder.ContentViewHolder;
import com.android.hyoonseol.imagecollector.viewholder.DateViewHolder;
import com.android.hyoonseol.imagecollector.viewholder.MoreViewHolder;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016-07-31.
 */

public class ICAdapter extends RecyclerView.Adapter {

    public interface OnImgClickListener {
        void onClick(View view, Image image);
    }

    public interface OnImgLongClickListener {
        void onLongClick(View view, Image image);
    }

    public interface OnMoreListener {
        void onRequestMore();
    }

    private Context mContext;
    private List<ICModel> mICModelList;
    private OnImgClickListener mImgClickListener;
    private OnImgLongClickListener mImgLongClickListener;
    private OnMoreListener mMoreListener;

    public ICAdapter(Context context, List<ICModel> ICModelList, OnImgClickListener imgClickListener, OnImgLongClickListener imgLongClickListener, OnMoreListener moreListener) {
        mContext = context;
        mICModelList = ICModelList;
        mImgClickListener = imgClickListener;
        mImgLongClickListener = imgLongClickListener;
        mMoreListener = moreListener;
    }

    public void setList(List<ICModel> ICModelList) {
        mICModelList = ICModelList;
    }

    public List<ICModel> getList() {
        return mICModelList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ViewType.DATE.ordinal()) {
            viewHolder = new DateViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_recycler_item_date, null));
        } else if (viewType == ViewType.CONTENT.ordinal()) {
            viewHolder = new ContentViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.view_recycler_item_content, null)
                                                ,mImgClickListener, mImgLongClickListener);
        } else if (viewType == ViewType.MORE.ordinal()) {
            viewHolder = new MoreViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_recycler_item_more, null));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DateViewHolder) {
            ((DateViewHolder)holder).onBindViewHolder(mICModelList.get(position).getDate());
        } else if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder)holder).onBindViewHolder(mICModelList.get(position).getImageList());
        } else if (holder instanceof MoreViewHolder) {
            ((MoreViewHolder)holder).onBindViewHolder(mMoreListener);
        }
    }

    @Override
    public int getItemCount() {
        return mICModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mICModelList.get(position).getViewType().ordinal();
    }
}
