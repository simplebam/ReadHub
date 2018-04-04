package com.yueyue.readhub.base.mvp;

import android.support.annotation.Nullable;

/**
 * author : yueyue on 2018/4/3 17:13
 * desc   :
 */
public interface IPresenter<V extends IView> {
    @Nullable
    V getView();

    void attachView(V view);

    void detachView();
}
