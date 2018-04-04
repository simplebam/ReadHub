package com.yueyue.readhub.base;

import com.yueyue.readhub.base.mvp.IPresenter;
import com.yueyue.readhub.base.mvp.IView;

/**
 * author : yueyue on 2018/4/4 15:06
 * desc   :
 */
public class BasePresenter<V extends IView> implements IPresenter<V> {
    private V mView;

    @Override public V getView() {
        return mView;
    }

    @Override public void attachView(V view) {
        mView = view;
    }

    @Override public void detachView() {
        mView = null;
    }
}
