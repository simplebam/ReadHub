package com.yueyue.readhub.feature.news;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yueyue.readhub.R;
import com.yueyue.readhub.base.BaseApplication;
import com.yueyue.readhub.base.BaseViewHolder;
import com.yueyue.readhub.feature.common.WebViewFragment;
import com.yueyue.readhub.feature.main.MainFragment;
import com.yueyue.readhub.model.Topic;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * author : yueyue on 2018/4/4 19:45
 * desc   :
 */
public class NewsViewHolder extends BaseViewHolder<Topic> {
    @BindView(R.id.txt_news_title)
    TextView mTxtTitle;
    @BindView(R.id.txt_news_summary)
    TextView mTxtSummary;
    @BindView(R.id.txt_news_time)
    TextView mTxtTime;

    public NewsViewHolder(final Context context, ViewGroup parent) {
        super(context, parent, R.layout.list_item_news);
    }

    @Override
    public void bindTo(final Topic value) {
        mTxtTitle.setText(value.getTitle());
        mTxtSummary.setText(value.getSummary());

        String time;
        if ((!TextUtils.isEmpty(value.getAuthorName())) && (!TextUtils.isEmpty(value.getSiteName()))) {
            time = BaseApplication.getContext().getString(R.string.site_author_time_format,
                    value.getSiteName(), value.getAuthorName(),
                    value.getPublishDateCountDown());
        } else if (TextUtils.isEmpty(value.getAuthorName()) && TextUtils.isEmpty(value.getSiteName())) {
            time = value.getPublishDateCountDown();
        } else if (TextUtils.isEmpty(value.getSiteName())) {
            time = BaseApplication.getContext().getString(R.string.author_time_format, value.getAuthorName(),
                            value.getPublishDateCountDown());
        } else {
            time = BaseApplication.getContext().getString(R.string.author_time_format, value.getSiteName(),
                            value.getPublishDateCountDown());
        }
        mTxtTime.setText(time);


        itemView.setOnClickListener(v -> {
            ((SupportActivity) v.getContext()).findFragment(MainFragment.class)
                    .start(WebViewFragment.newInstance(value));
        });

        mTxtTitle.setVisibility(TextUtils.isEmpty(value.getTitle()) ? View.GONE : View.VISIBLE);
        mTxtSummary.setVisibility(TextUtils.isEmpty(value.getSummary()) ? View.GONE : View.VISIBLE);
    }
}
