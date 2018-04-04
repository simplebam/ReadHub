package com.yueyue.readhub.feature.blockchain;

import com.yueyue.readhub.base.BaseListPresenter;
import com.yueyue.readhub.feature.news.NewsFragment;
import com.yueyue.readhub.model.Topic;

/**
 * author : yueyue on 2018/4/4 19:57
 * desc   :
 */
public class BCNewsFragment extends NewsFragment {
    public static final String TAG = "BCNewsFragment";

    public static NewsFragment newInstance() {
        return new BCNewsFragment();
    }

    @Override public BaseListPresenter<Topic> createPresenter() {
        return new BCNewsPresenter();
    }
}
