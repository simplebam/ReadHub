package com.yueyue.readhub.network;

import com.yueyue.readhub.model.ApiData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author : yueyue on 2018/4/4 18:45
 * desc   : 开发者资讯
 */
public interface TechNewsService {
    //https://api.readhub.me/technews
    @GET("technews")
    Observable<ApiData> getTechNews();

    //https://api.readhub.me/technews?lastCursor=1522808400000&pageSize=10
    @GET("technews")
    Observable<ApiData> getMoreTechNews(@Query("lastCursor") String lastCursor,
                                        @Query("pageSize") int pageSize);
}
