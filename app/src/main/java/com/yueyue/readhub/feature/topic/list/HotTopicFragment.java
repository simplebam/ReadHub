package com.yueyue.readhub.feature.topic.list;

import android.view.ViewGroup;

import com.yueyue.readhub.base.BaseListFragment;
import com.yueyue.readhub.base.BaseListPresenter;
import com.yueyue.readhub.base.BaseViewHolder;
import com.yueyue.readhub.model.Topic;

/**
 * author : yueyue on 2018/4/4 18:52
 * desc   :
 */
public class HotTopicFragment extends BaseListFragment<Topic> {
    public static final String TAG = HotTopicFragment.class.getSimpleName();

    public static HotTopicFragment newInstance() {
        return new HotTopicFragment();
    }

    @Override
    public BaseViewHolder<Topic> provideViewHolder(ViewGroup parent, int viewType) {
        return new HotTopicViewHolder(getActivity(), parent);
    }

    @Override
    public BaseListPresenter<Topic> createPresenter() {
        return new HotTopicPresenter();
    }
}