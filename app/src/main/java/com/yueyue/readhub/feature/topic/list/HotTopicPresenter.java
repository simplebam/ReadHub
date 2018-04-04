package com.yueyue.readhub.feature.topic.list;

import com.yueyue.readhub.base.BaseListPresenter;
import com.yueyue.readhub.model.ApiData;
import com.yueyue.readhub.model.Topic;
import com.yueyue.readhub.network.ApiService;
import com.yueyue.readhub.network.HotTopicService;

import io.reactivex.Observable;

/**
 * author : yueyue on 2018/4/4 19:41
 * desc   :
 */
public class HotTopicPresenter extends BaseListPresenter<Topic> {
    private HotTopicService mService = ApiService.getInstance().createHotTopicService();

    @Override
    public Observable<ApiData> request() {
        return mService.getHotTopic();
    }

    @Override
    public Observable<ApiData> requestMore() {
        return mService.getMoreHotTopic(getLastCursor(), 10);
    }
}
