package com.yueyue.readhub.feature.topic.detail;

import android.os.AsyncTask;

import com.yueyue.readhub.base.BasePresenter;
import com.yueyue.readhub.base.mvp.INetworkPresenter;
import com.yueyue.readhub.common.Constant;
import com.yueyue.readhub.component.PLog;
import com.yueyue.readhub.model.Topic;
import com.yueyue.readhub.model.TopicTimeLine;
import com.yueyue.readhub.network.ApiService;
import com.yueyue.readhub.network.HotTopicService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author : yueyue on 2018/4/4 14:59
 * desc   :
 */
public class TopicDetailPresenter extends BasePresenter<TopicDetailFragment>
        implements INetworkPresenter<TopicDetailFragment> {
    private static final String TAG = TopicDetailPresenter.class.getSimpleName();

    private HotTopicService mService = ApiService.getInstance().createHotTopicService();
    private String mTopicId;

    @Override
    public void start() {
        Disposable disposable = request()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        topic -> {
                            if (getView() == null) return;
                            getView().onSuccess(topic);
                        },
                        throwable -> {
                            PLog.e(TAG, "start: " + throwable.toString());
                            throwable.printStackTrace();
                            if (getView() == null) return;
                            getView().onError(throwable);
                        });
    }

    @Override
    public void startRequestMore() {
    }

    @Override
    public Observable<Topic> request() {
        return mService.getHotTopicDetail(mTopicId);
    }

    @Override
    public Observable<Topic> requestMore() {
        return null;
    }

    public void getTopicDetail(String topicId) {
        mTopicId = topicId;
        start();
    }

    protected void getTimeLine(final String topicId) {
        new TimeLineAsyncTask(topicId, this).execute();
    }

    public static class TimeLineAsyncTask extends AsyncTask<Void, Void, Document> {
        private String mTopicId;
        private TopicDetailPresenter mPresenter;

        public TimeLineAsyncTask(String topicId, TopicDetailPresenter presenter) {
            mTopicId = topicId;
            mPresenter = presenter;
        }

        @Override
        protected Document doInBackground(Void... params) {
            Document document = null;
            try {
                document = Jsoup.connect(Constant.TOPIC_DETAIL_URL + mTopicId).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return document;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            if (document == null) return;
            Elements timelineContainer = document.getElementsByClass(
                    "timeline__container___3jHS8 timeline__container--PC___1D1r7");
            if (timelineContainer == null || timelineContainer.select("li").isEmpty()) return;
            List<TopicTimeLine> timeLines = new ArrayList<>();
            for (Element liElement : timelineContainer.select("li")) {
                TopicTimeLine timeLine = new TopicTimeLine();
                timeLine.date = liElement.getElementsByClass("date-item___1io1R").text();
                Element contentElement = liElement.getElementsByClass("content-item___3KfMq").get(0);
                timeLine.content = contentElement.getElementsByTag("a").get(0).text();
                timeLine.url = contentElement.getElementsByTag("a").get(0).attr("href");
                timeLines.add(timeLine);
            }
            if (mPresenter.getView() != null) mPresenter.getView().onGetTimeLineSuccess(timeLines);
            mPresenter = null;
        }
    }

}

