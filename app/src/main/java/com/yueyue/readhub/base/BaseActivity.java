package com.yueyue.readhub.base;

import android.support.annotation.NonNull;
import android.view.MotionEvent;

import com.bugtags.library.Bugtags;
import com.yueyue.readhub.base.mvp.IPresenter;
import com.yueyue.readhub.base.mvp.IView;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * author : yueyue on 2018/4/3 17:14
 * desc   :
 */
public abstract class BaseActivity<P extends IPresenter> extends SupportActivity implements IView<P> {

    private P mPresenter;
    private boolean mIsPresenterNeeded = false;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachPresenter();
    }

    @Override
    public P getPresenter() {
        if (!mIsPresenterNeeded) {
            throw new RuntimeException(
                    "This activity hasn't attached any presenters,if the presenter is needed,please invoke attachPresenter() first.");
        }
        return mPresenter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void attachPresenter(@NonNull P presenter) {
        mIsPresenterNeeded = true;
        mPresenter = presenter;
        presenter.attachView(this);

    }

    @Override
    public void detachPresenter() {
        if (mPresenter == null) return;

        mPresenter.detachView();
        mPresenter = null;
    }
}
