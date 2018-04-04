package com.yueyue.readhub.feature.news;

import android.view.ViewGroup;

import com.yueyue.readhub.base.BaseListFragment;
import com.yueyue.readhub.base.BaseListPresenter;
import com.yueyue.readhub.base.BaseViewHolder;
import com.yueyue.readhub.model.Topic;

/**
 * author : yueyue on 2018/4/4 19:43
 * desc   :
 */
public class NewsFragment extends BaseListFragment<Topic> {
    private static final String TAG = NewsFragment.class.getSimpleName();

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override public BaseViewHolder<Topic> provideViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(getActivity(), parent);
    }

    @Override public BaseListPresenter<Topic> createPresenter() {
        return new NewsPresenter();
    }
}
