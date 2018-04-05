package com.yueyue.readhub.network;

import com.yueyue.readhub.model.Version;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author : yueyue on 2018/4/4 21:16
 * desc   :
 */
public interface VersionService {
    //http://api.fir.im/apps/latest/5ac4dca1959d69325b4093cd?api_token=7db041d0c3013b63e4bed2a554f02d85
    //fir.im - 版本查询 https://fir.im/docs/version_detection
    //而且在Retrofit 2.0中我们还可以在@Url里面定义完整的URL：这种情况下Base URL会被忽略。
    @GET("http://api.fir.im/apps/latest/5ac4dca1959d69325b4093cd")
    Observable<Version> getLatestVersion(@Query("api_token") String api_token);
}
