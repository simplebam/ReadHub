package com.yueyue.readhub.common;

import com.yueyue.readhub.base.BaseApplication;

import java.io.File;

/**
 * author : yueyue on 2018/4/3 10:11
 * desc   :
 */
public interface Constant {
    public static final String NET_CACHE = BaseApplication.getContext().getCacheDir().getPath() + File.separator + "NetCache";

    //API 网址
    String API_HOST = "https://api.readhub.me";
    String READHUB_PAGE_URL = "https://readhub.me";
    String TOPIC_DETAIL_URL = "https://readhub.me/topic/";

    //Extra
    String EXTRA_TOPIC = "EXTRA_TOPIC";
    String EXTRA_URL = "EXTRA_URL";
    String BUNDLE_TOPIC_ID = "BUNDLE_TOPIC_ID";

    //其他
    String BUGGLY_APP_ID = "547fe840fc";
    String GITHUB_PAGE_URL = "https://github.com/BryantPang/ReadHub";
    String PERSONAL_EMAIL = "chihopang10@gmail.com";
}
