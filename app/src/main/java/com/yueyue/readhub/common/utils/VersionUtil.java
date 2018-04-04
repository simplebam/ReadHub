package com.yueyue.readhub.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.yueyue.readhub.BuildConfig;
import com.yueyue.readhub.component.PLog;
import com.yueyue.readhub.model.Version;
import com.yueyue.readhub.network.ApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author : yueyue on 2018/4/3 10:18
 * desc   :
 */
public class VersionUtil {
    private VersionUtil() {
    }

    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "1";
        }
    }

    /**
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            PLog.e("getAppVersionName", "Exception:" + e);
            e.printStackTrace();
        }
        return TextUtils.isEmpty(versionName) ? "" : versionName;
    }

    public static void checkVersion(Context context) {
        Disposable disposable = ApiService.getInstance()
                .createVersionService()
                .getLatestVersion(BuildConfig.FirApiToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(version -> {
                    String currentVersionName = VersionUtil.getVersion(context);
                    if (currentVersionName.compareTo(version.versionShort) < 0) {
                        showUpdateDialog(version, context);
                    } else {
                        ToastUtils.showShort("已经是最新版本(⌐■_■)");
                    }
                });
    }

    private static void showUpdateDialog(Version versionAPI, final Context context) {
        String title = "发现新版" + versionAPI.name + "版本号：" + versionAPI.versionShort;
        new AlertDialog.Builder(context).setTitle(title)
                .setMessage(versionAPI.changelog)
                .setCancelable(false)   //禁止触点在dialog外围时候dialog将消失
                .setPositiveButton("下载", (dialog, which) -> {
                    Uri uri = Uri.parse(versionAPI.updateUrl);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    context.startActivity(intent);
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
