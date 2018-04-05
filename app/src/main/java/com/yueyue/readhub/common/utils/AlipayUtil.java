package com.yueyue.readhub.common.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.yueyue.readhub.base.BaseApplication;

import moe.feng.alipay.zerosdk.AlipayZeroSdk;

/**
 * author : yueyue on 2018/4/5 19:06
 * desc   : Andorid检测支付宝客户端是否安装 - CSDN博客
 * https://blog.csdn.net/u012045061/article/details/49638737
 */
public class AlipayUtil {

    public static boolean hasInstalledAlipayClient() {
        //Android 必知必会 - 使用 Intent 打开第三方应用及验证可用性 | Cafeting
        //          http://likfe.com/2017/08/30/android-is-intent-available/
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(
                BaseApplication.getContext().getPackageManager());

        return componentName != null;
    }

    public static boolean hasInstalledAlipayClient(Context context) {
        if (context == null) {
            context = BaseApplication.getContext();
        }
        return AlipayZeroSdk.hasInstalledAlipayClient(context);
    }

    public static void startAlipayClient(Activity activity,String urlCode) {
        if (activity == null) {
            return;
        }
        AlipayZeroSdk.startAlipayClient(activity, urlCode);
    }

}
