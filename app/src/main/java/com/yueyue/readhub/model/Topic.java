package com.yueyue.readhub.model;

import android.text.TextUtils;

import com.yueyue.readhub.common.utils.TimeUtil;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * author : yueyue on 2018/4/3 21:53
 * desc   :
 */
@Parcel
public class Topic {
    String id;
    String order;
    String title;
    String siteName;
    String authorName;
    String url;
    String mobileUrl;
    String summary;
    ArrayList<Topic> newsArray;
    String publishDate;
    Extra extra;

    public Topic() {
    }

    public String getSiteName() {
        return siteName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getUrl() {
        return TextUtils.isEmpty(mobileUrl) ? url : mobileUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return TextUtils.isEmpty(title) ? "" : title.trim();
    }

    public String getSummary() {
        return TextUtils.isEmpty(summary) ? null : summary.trim();
    }

    public List<Topic> getNewsArray() {
        return newsArray;
    }

    public String getPublishDateCountDown() {
        return TimeUtil.countDown(publishDate);
    }

    public String getFormatPublishDate() {
        return TimeUtil.getFormatDate(publishDate);
    }

    public String getOrder() {
        return order;
    }

    public String getLastCursor() {
        return !TextUtils.isEmpty(order) ? order : String.valueOf(TimeUtil.getTimeStamp(publishDate));
    }

    public boolean hasInstantView() {
        return (extra != null) && extra.instantView;
    }

    @Parcel
    public static class Extra {
        boolean instantView;
    }
}
