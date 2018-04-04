package com.yueyue.readhub.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yueyue.readhub.R;

import butterknife.BindView;

/**
 * author : yueyue on 2018/4/4 19:27
 * desc   :
 */
public class LoadingViewHolder extends BaseViewHolder<Boolean> {
    @BindView(R.id.txt_loading_item)
    TextView mTxtNoMore;
    @BindView(R.id.progress_bar_loading_item)
    ProgressBar mProgressBar;

    public LoadingViewHolder(ViewGroup parent, boolean hasMore) {
        super(parent.getContext(), parent, R.layout.list_item_loading);
        bindTo(hasMore);
    }

    @Override
    public void bindTo(Boolean hasMore) {
        mTxtNoMore.setVisibility(hasMore ? View.GONE : View.VISIBLE);
        mProgressBar.setVisibility(hasMore ? View.VISIBLE : View.GONE);
    }
}