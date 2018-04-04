package com.yueyue.readhub.network;

import com.yueyue.readhub.model.ApiData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author : yueyue on 2018/4/4 18:44
 * desc   : 科技动态
 */
public interface NewsService {
    //https://api.readhub.me/news
    @GET("news")
    Observable<ApiData> getNews();

    //https://api.readhub.me/news?lastCursor=1522808400000&pageSize=10
    @GET("news")
    Observable<ApiData> getMoreNews(@Query("lastCursor") String lastCursor,
                                    @Query("pageSize") int pageSize);


    //https://api.readhub.me/blockchain
    @GET("blockchain")
    Observable<ApiData> getBCNews();

    //https://api.readhub.me/blockchain?lastCursor=1522808460000&pageSize=10
    @GET("blockchain")
    Observable<ApiData> getMoreBCNews(@Query("lastCursor") String lastCursor,
                                      @Query("pageSize") int pageSize);
}