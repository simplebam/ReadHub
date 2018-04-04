package com.yueyue.readhub.base;

import android.util.Log;

import com.yueyue.readhub.base.mvp.INetworkPresenter;
import com.yueyue.readhub.model.ApiData;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author : yueyue on 2018/4/4 18:54
 * desc   :
 */
public abstract class BaseListPresenter<T> extends BasePresenter<BaseListFragment> implements
        INetworkPresenter<BaseListFragment> {
    private static final String TAG = BaseListPresenter.class.getSimpleName();

    private String lastCursor;

    @Override
    public void start() {
        Disposable disposable = request()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiData -> {
                    if (getView() == null) return;
                    if (apiData == null || apiData.getData() == null) {
                        getView().onError(new Throwable("请求失败"));
                        return;
                    }
                    getView().onSuccess(apiData.getData());
                    lastCursor = apiData.getData().get(apiData.getData().size() - 1).getLastCursor();
                }, throwable -> {
                    Log.e(TAG, "start: " + throwable.toString());
                    throwable.printStackTrace();
                    if (getView() == null) return;
                    getView().onError(throwable);
                });
    }

    @Override
    public void startRequestMore() {
        Disposable disposable = requestMore()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        apiData -> {
                            if (getView() == null) return;
                            if (apiData == null || apiData.getData() == null) {
                                getView().onError(new Throwable("请求失败"));
                                return;
                            }
                            getView().onSuccess(apiData.getData());
                            if (apiData.getData().isEmpty()) {
                                getView().hasMore = false;
                                return;
                            }
                            lastCursor = apiData.getData().get(apiData.getData().size() - 1).getLastCursor();
                        },
                        throwable -> {
                            if (getView() == null) return;
                            getView().onError(throwable);
                        });
    }

    @Override
    public abstract Observable<ApiData> request();

    @Override
    public abstract Observable<ApiData> requestMore();

    public String getLastCursor() {
        return lastCursor;
    }
}
