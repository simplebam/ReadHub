package com.yueyue.readhub.feature.topic.instant;

import com.yueyue.readhub.base.BasePresenter;
import com.yueyue.readhub.base.mvp.INetworkPresenter;
import com.yueyue.readhub.model.InstantReadData;
import com.yueyue.readhub.network.ApiService;
import com.yueyue.readhub.network.HotTopicService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author : yueyue on 2018/4/4 19:35
 * desc   :
 */
public class InstantReadPresenter extends BasePresenter<InstantReadFragment>
        implements INetworkPresenter<InstantReadFragment> {
    private HotTopicService mService = ApiService.getInstance().createHotTopicService();
    private String mTopicId;

    @Override
    public void start() {
        Disposable disposable = request()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<InstantReadData>() {
                    @Override
                    public void accept(@NonNull InstantReadData instantReadData) {
                        if (getView() == null) return;
                        getView().onSuccess(instantReadData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) {
                        if (getView() == null) return;
                        throwable.printStackTrace();
                        getView().onError(throwable);
                    }
                });
    }

    @Override
    public void startRequestMore() {

    }

    @Override
    public Observable<InstantReadData> request() {
        return mService.getInstantRead(mTopicId);
    }

    @Override
    public Observable requestMore() {
        return null;
    }

    public void getInstantRead(String topicId) {
        mTopicId = topicId;
        start();
    }
}