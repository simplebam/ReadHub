package com.yueyue.readhub.feature.technews;

import com.yueyue.readhub.base.BaseListPresenter;
import com.yueyue.readhub.model.ApiData;
import com.yueyue.readhub.model.Topic;
import com.yueyue.readhub.network.ApiService;
import com.yueyue.readhub.network.TechNewsService;

import io.reactivex.Observable;

/**
 * author : yueyue on 2018/4/4 19:55
 * desc   :
 */
public class TechNewsPresenter extends BaseListPresenter<Topic> {
    private TechNewsService mService = ApiService.getInstance().createTechNewsService();

    @Override
    public Observable<ApiData> request() {
        return mService.getTechNews();
    }

    @Override
    public Observable<ApiData> requestMore() {
        return mService.getMoreTechNews(getLastCursor(), 10);
    }
}
