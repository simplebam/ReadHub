package com.yueyue.readhub.feature.news;

import com.yueyue.readhub.base.BaseListPresenter;
import com.yueyue.readhub.model.ApiData;
import com.yueyue.readhub.model.Topic;
import com.yueyue.readhub.network.ApiService;
import com.yueyue.readhub.network.NewsService;

import io.reactivex.Observable;

/**
 * author : yueyue on 2018/4/4 19:51
 * desc   :
 */
public class NewsPresenter extends BaseListPresenter<Topic> {
    private NewsService mService = ApiService.getInstance().createNewsService();

    @Override public Observable<ApiData> request() {
        return mService.getNews();
    }

    @Override public Observable<ApiData> requestMore() {
        return mService.getMoreNews(getLastCursor(), 10);
    }
}
