package com.yueyue.readhub.network;

import com.yueyue.readhub.base.BaseApplication;
import com.yueyue.readhub.common.Constant;
import com.yueyue.readhub.common.utils.AppUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author : yueyue on 2018/4/4 18:40
 * desc   :
 */
public class ApiService {

    private static Retrofit sRetrofit = null;
    private static OkHttpClient sOkHttpClient = null;

    private void init() {
        initOkHttp();
        initRetrofit();
    }

    private ApiService() {
        init();
    }

    public static ApiService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ApiService INSTANCE = new ApiService();
    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 缓存 http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(Constant.NET_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = chain -> {
            Request request = chain.request();
            if (!AppUtil.isNetworkConnected(BaseApplication.getContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            Response.Builder newBuilder = response.newBuilder();
            if (AppUtil.isNetworkConnected(BaseApplication.getContext())) {
                int maxAge = 0;
                // 有网络时 设置缓存超时时间0个小时
                newBuilder.header("Cache-Control", "public, max-age=" + maxAge);
            } else {
                // 无网络时，设置超时为4周
                int maxStale = 60 * 60 * 24 * 28;
                newBuilder.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
            }
            return newBuilder.build();
        };
        builder.cache(cache).addInterceptor(cacheInterceptor);
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        sOkHttpClient = builder.build();
    }

    private static void initRetrofit() {
        sRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_HOST)
                .client(sOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())  //设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //RxJava 适配器
                .build();
    }


    public HotTopicService createHotTopicService() {
        return sRetrofit.create(HotTopicService.class);
    }

    public NewsService createNewsService() {
        return sRetrofit.create(NewsService.class);
    }

    public TechNewsService createTechNewsService() {
        return sRetrofit.create(TechNewsService.class);
    }

    public VersionService createVersionService() {
        return sRetrofit.create(VersionService.class);
    }
}
