package com.yueyue.readhub.base.mvp;

import android.support.annotation.NonNull;

/**
 * author : yueyue on 2018/4/3 17:13
 * desc   :
 */
public interface IView<P extends IPresenter> {
    P getPresenter();

    void attachPresenter(@NonNull P presenter);

    void detachPresenter();
}
