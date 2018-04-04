package com.yueyue.readhub.feature.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.blankj.utilcode.util.ToastUtils;
import com.yueyue.readhub.R;
import com.yueyue.readhub.base.BaseActivity;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int PERMISSION_STORAGE = 1;
    private boolean isBackPressed = false;//防止误触返回退出
    private Runnable mRequestPermissionAction;//请求运行时权限成功后的回调，由 requestPermissionWithAction() 传入。

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFrameMain();
    }

    private void setFrameMain() {
        MainFragment mainFragment = findFragment(MainFragment.class);
        if (mainFragment == null) {
            loadRootFragment(R.id.frame_main, MainFragment.newInstance(), true, true);
        } else {
            loadRootFragment(R.id.frame_main, mainFragment);
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    public void requestPermissionWithAction(String permission, int requestCode, Runnable action) {
        mRequestPermissionAction = action;
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            mRequestPermissionAction.run();
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mRequestPermissionAction.run();
            return;
        }
        ToastUtils.showShort(R.string.permission_failure_reminder);
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
            return;
        }

        doublePressBackToQuit();
    }

    private void doublePressBackToQuit() {
        if (isBackPressed) {
            super.onBackPressedSupport();
            return;
        }

        isBackPressed = true;
        ToastUtils.showShort(R.string.leave_app);
        new Handler().postDelayed(() -> isBackPressed = false, 2000);
    }
}
