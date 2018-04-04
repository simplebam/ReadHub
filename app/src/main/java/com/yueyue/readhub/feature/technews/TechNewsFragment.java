package com.yueyue.readhub.feature.technews;

import android.view.ViewGroup;

import com.yueyue.readhub.base.BaseListFragment;
import com.yueyue.readhub.base.BaseListPresenter;
import com.yueyue.readhub.base.BaseViewHolder;
import com.yueyue.readhub.feature.news.NewsViewHolder;
import com.yueyue.readhub.model.Topic;

/**
 * author : yueyue on 2018/4/4 19:55
 * desc   :
 */
public class TechNewsFragment extends BaseListFragment<Topic> {
    public static String TAG = "TechNewsFragment";

    public static TechNewsFragment newInstance() {
        return new TechNewsFragment();
    }

    @Override public BaseViewHolder<Topic> provideViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(getActivity(), parent);
    }

    @Override public BaseListPresenter<Topic> createPresenter() {
        return new TechNewsPresenter();
    }
}
