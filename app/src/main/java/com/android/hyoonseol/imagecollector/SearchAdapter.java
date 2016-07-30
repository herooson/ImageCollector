package com.android.hyoonseol.imagecollector;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.hyoonseol.imagecollector.model.Search;
import com.android.hyoonseol.imagecollector.model.ViewType;
import com.android.hyoonseol.imagecollector.viewholder.ContentViewHolder;
import com.android.hyoonseol.imagecollector.viewholder.DateViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016-07-31.
 */

public class SearchAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Search> mSearchList;

    public SearchAdapter(Context context, List<Search> searchList) {
        mContext = context;
        mSearchList = searchList;
    }

    public void setList(List<Search> searchList) {
        mSearchList = searchList;
    }

    public List<Search> getList() {
        return mSearchList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ViewType.DATE.ordinal()) {
            viewHolder = new DateViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_recycler_item_date, null));
        } else if (viewType == ViewType.CONTENT.ordinal()) {
            viewHolder = new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_recycler_item_content, null));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DateViewHolder) {
            ((DateViewHolder)holder).onBindViewHolder(mSearchList.get(position).getDate());
        } else if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder)holder).onBindViewHolder(mSearchList.get(position).getJsonArray());
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mSearchList.get(position).getViewType().ordinal();
    }
}
