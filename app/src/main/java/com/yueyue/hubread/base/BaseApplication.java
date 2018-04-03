package com.yueyue.hubread.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.bugtags.library.Bugtags;
import com.yueyue.hubread.BuildConfig;
import com.yueyue.hubread.component.CrashHandler;
import com.yueyue.hubread.component.PLog;

import me.yokeyword.fragmentation.Fragmentation;

/**
 * author : yueyue on 2018/4/3 10:00
 * desc   :
 */
public class BaseApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        //初始化工具类AndroidUtilCode
        Utils.init(this);

        Bugtags.start(BuildConfig.BugtagsKey, this, Bugtags.BTGInvocationEventBubble);
        CrashHandler.init(new CrashHandler(this));

        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(me.yokeyword.fragmentation.BuildConfig.DEBUG)
                .handleException(e -> PLog.e(e.toString())).install();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getContext() {
        return sContext;
    }
}
