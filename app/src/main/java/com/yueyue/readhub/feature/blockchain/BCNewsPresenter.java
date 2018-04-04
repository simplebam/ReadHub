package com.yueyue.readhub.feature.blockchain;

import com.yueyue.readhub.base.BaseListPresenter;
import com.yueyue.readhub.model.ApiData;
import com.yueyue.readhub.model.Topic;
import com.yueyue.readhub.network.ApiService;
import com.yueyue.readhub.network.NewsService;

import io.reactivex.Observable;

/**
 * author : yueyue on 2018/4/4 19:57
 * desc   :
 */
public class BCNewsPresenter extends BaseListPresenter<Topic> {
    private NewsService mService = ApiService.getInstance().createNewsService();

    @Override
    public Observable<ApiData> request() {
        return mService.getBCNews();
    }

    @Override
    public Observable<ApiData> requestMore() {
        return mService.getMoreBCNews(getLastCursor(), 10);
    }
}
