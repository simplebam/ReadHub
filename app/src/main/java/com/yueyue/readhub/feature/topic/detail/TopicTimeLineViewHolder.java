package com.yueyue.readhub.feature.topic.detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yueyue.readhub.R;
import com.yueyue.readhub.base.BaseViewHolder;
import com.yueyue.readhub.feature.main.MainActivity;
import com.yueyue.readhub.feature.main.MainFragment;
import com.yueyue.readhub.model.TopicTimeLine;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : yueyue on 2018/4/4 15:13
 * desc   :
 */
public class TopicTimeLineViewHolder extends BaseViewHolder<TopicTimeLine> {
    @BindView(R.id.txt_date)
    TextView mTxtDate;
    @BindView(R.id.txt_time_line_content)
    TextView mTxtContent;
    @BindView(R.id.view_top_line)
    View mDividerTop;
    @BindView(R.id.view_bottom_line)
    View mDividerBottom;

    private TopicTimeLine mTimeLine;

    public TopicTimeLineViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.list_item_time_line);
    }

    @Override
    public void bindTo(TopicTimeLine value) {
        mTimeLine = value;
        mTxtDate.setText(value.date);
        mTxtContent.setText(value.content);

        mDividerTop.setVisibility(
                getItemViewType() == TopicDetailFragment.VIEW_TYPE_TOP ? View.INVISIBLE : View.VISIBLE);

        mDividerBottom.setVisibility(
                getItemViewType() == TopicDetailFragment.VIEW_TYPE_BOTTOM ? View.INVISIBLE : View.VISIBLE);
    }

    @OnClick(R.id.txt_time_line_content)
    void onClickContent(View view) {
        ((MainActivity) view.getContext()).findFragment(MainFragment.class)
                .start(TopicDetailFragment.newInstance(mTimeLine.url));
    }
}
