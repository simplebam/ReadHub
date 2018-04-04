package com.yueyue.readhub.network;

import com.yueyue.readhub.model.ApiData;
import com.yueyue.readhub.model.InstantReadData;
import com.yueyue.readhub.model.Topic;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * author : yueyue on 2018/4/4 18:38
 * desc   :
 */
public interface HotTopicService {
    // https://api.readhub.me/topic
    @GET("topic")
    Observable<ApiData> getHotTopic();

    // https://api.readhub.me/topic?lastCursor=&pageSize=20
    @GET("topic")
    Observable<ApiData> getMoreHotTopic(@Query("lastCursor") String lastCursor,
                                        @Query("pageSize") int pageSize);

    //https://api.readhub.me/topic/3J3szbIacYx
    @GET("topic/{topic_id}")
    Observable<Topic> getHotTopicDetail(@Path("topic_id") String topicId);

    // https://api.readhub.me/topic/instantview?topicId=2ywi9i7lBj7
    @GET("/topic/instantview")
    Observable<InstantReadData> getInstantRead(@Query("topicId") String topicId);
}

