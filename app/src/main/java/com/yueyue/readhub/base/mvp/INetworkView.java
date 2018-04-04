package com.yueyue.readhub.base.mvp;

/**
 * author : yueyue on 2018/4/4 15:05
 * desc   :
 */
public interface INetworkView<P extends INetworkPresenter, D> extends IView<P> {
    void onSuccess(D data);

    void onError(Throwable e);
}
