package com.yueyue.readhub.base.mvp;

import io.reactivex.Observable;

/**
 * author : yueyue on 2018/4/4 15:05
 * desc   :
 */
public interface INetworkPresenter<V extends INetworkView> extends IPresenter<V> {
    void start();

    void startRequestMore();

    Observable request();

    Observable requestMore();
}
