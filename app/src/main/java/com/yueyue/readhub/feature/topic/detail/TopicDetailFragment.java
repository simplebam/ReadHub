package com.yueyue.readhub.feature.topic.detail;

import android.Manifest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.yueyue.readhub.R;
import com.yueyue.readhub.base.BaseAdapter;
import com.yueyue.readhub.base.BaseSwipeBackFragment;
import com.yueyue.readhub.base.BaseViewHolder;
import com.yueyue.readhub.base.mvp.INetworkView;
import com.yueyue.readhub.common.Constant;
import com.yueyue.readhub.common.utils.BitmapUtil;
import com.yueyue.readhub.feature.common.WebViewFragment;
import com.yueyue.readhub.feature.main.MainActivity;
import com.yueyue.readhub.model.Topic;
import com.yueyue.readhub.model.TopicTimeLine;
import com.yueyue.readhub.widget.TopicShareView;

import org.parceler.Parcels;

import java.util.Collection;

import butterknife.BindView;

/**
 * author : yueyue on 2018/4/4 14:58
 * desc   :
 */
public class TopicDetailFragment
        extends BaseSwipeBackFragment<TopicDetailPresenter>
        implements INetworkView<TopicDetailPresenter, Topic>, Toolbar.OnMenuItemClickListener {
    public static final String TAG = TopicDetailFragment.class.getSimpleName();

    public static final int VIEW_TYPE_BOTTOM = 98;
    public static final int VIEW_TYPE_TOP = 99;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txt_topic_detail_title)
    TextView mTxtTopicTitle;
    @BindView(R.id.txt_topic_time)
    TextView mTxtTopicTime;
    @BindView(R.id.txt_topic_description)
    TextView mTxtTopicDescription;
    @BindView(R.id.linear_web_title_container)
    LinearLayout mLinearTitleContainer;
    @BindView(R.id.linear_time_line_container)
    LinearLayout mLinearTimelineContainer;
    @BindView(R.id.recycler_time_line)
    RecyclerView mRecyclerTimeline;
    @BindView(R.id.txt_toolbar_title)
    TextView mTxtToolbarTitle;
    @BindView(R.id.img_toolbar)
    ImageView mImgToolbar;
    @BindView(R.id.scroll_view)
    NestedScrollView mScrollView;
    @BindView(R.id.view_topic_share)
    TopicShareView mTopicShareView;

    private Topic mTopic;

    private BaseAdapter<TopicTimeLine> mTimelineAdapter = new BaseAdapter<TopicTimeLine>() {
        @Override
        public BaseViewHolder<TopicTimeLine> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TopicTimeLineViewHolder(getContext(), parent);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) return VIEW_TYPE_TOP;
            if (position == getItemCount() - 1) return VIEW_TYPE_BOTTOM;
            return super.getItemViewType(position);
        }
    };

    public static TopicDetailFragment newInstance(Topic topic) {
        TopicDetailFragment fragment = new TopicDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_TOPIC, Parcels.wrap(topic));
        fragment.setArguments(bundle);
        return fragment;
    }

    public static TopicDetailFragment newInstance(String topicId) {
        TopicDetailFragment fragment = new TopicDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BUNDLE_TOPIC_ID, topicId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_topic_detail;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachPresenter(new TopicDetailPresenter());
        mTopic = Parcels.unwrap(getArguments().getParcelable(Constant.EXTRA_TOPIC));
        if (mTopic != null) {
            onSuccess(mTopic);
            return;
        }
        String topicId = getArguments().getString(Constant.BUNDLE_TOPIC_ID);
        getPresenter().getTopicDetail(topicId);
    }

    protected void onGetTimeLineSuccess(Collection<TopicTimeLine> timeLines) {
        mTimelineAdapter.addItems(timeLines);
        mLinearTimelineContainer.setVisibility(
                mTimelineAdapter.getItemCount() != 0 ? View.VISIBLE : View.GONE);
    }

    private void setupView() {
        //设置导航图标
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(v -> pop());
        mToolbar.inflateMenu(R.menu.menu_topic_detail);
        mToolbar.setOnMenuItemClickListener(this);

        mTxtTopicTitle.setText(mTopic.getTitle());
        mTxtToolbarTitle.setText(mTopic.getTitle());
        mTxtTopicTime.setText(mTopic.getFormatPublishDate());
        mTxtTopicDescription.setText(mTopic.getSummary());
        mTxtTopicDescription.setVisibility(
                TextUtils.isEmpty(mTopic.getSummary()) ? View.GONE : View.VISIBLE);

        if (mLinearTitleContainer.getChildCount() > 0) {
            mLinearTitleContainer.removeAllViews();
        }

        for (final Topic topic : mTopic.getNewsArray()) {
            TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            textView.setLayoutParams(params);
            textView.setPadding(10, 16, 10, 16);
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_data, 0, 0, 0);
            textView.setCompoundDrawablePadding(15);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setTextColor(getResources().getColor(R.color.color_607D8B));
            textView.setBackgroundResource(R.drawable.selector_btn_background);
            if (TextUtils.isEmpty(topic.getSiteName())) {
                textView.setText(topic.getTitle());
            } else {
                SpannableString spannableTitle =
                        SpannableString.valueOf(topic.getTitle() + " " + topic.getSiteName());
                spannableTitle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_AAACB4)),
                        topic.getTitle().length() + 1,
                        topic.getTitle().length() + topic.getSiteName().length() + 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableTitle);
            }
            textView.setOnClickListener(v -> start(WebViewFragment.newInstance(topic)));
            mLinearTitleContainer.addView(textView);
        }
        mRecyclerTimeline.setAdapter(mTimelineAdapter);
        mRecyclerTimeline.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerTimeline.setNestedScrollingEnabled(false);
        mScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (view, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    mTxtToolbarTitle.setVisibility(
                            scrollY > mTxtTopicTime.getBottom() ? View.VISIBLE : View.GONE);

                    mImgToolbar.setVisibility(
                            scrollY > mTxtTopicTime.getBottom() ? View.GONE : View.VISIBLE);
                });

        mTopicShareView.setup(mTopic);
    }

    @Override
    public void onSuccess(Topic topic) {
        mTopic = topic;
        setupView();
        getPresenter().getTimeLine(topic.getId());
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(getContext(), "请求错误", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                applyForStorage();
                return true;
            default:
                return false;
        }
    }

    private void applyForStorage() {
        if (getContext() instanceof MainActivity) {
            ((MainActivity) getContext()).requestPermissionWithAction(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    MainActivity.PERMISSION_STORAGE, () -> {
                        new CreateSharePictureTask(mTopic).
                                execute(mTopicShareView.getChildAt(0));
                    });
        }
    }

    public static class CreateSharePictureTask extends AsyncTask<View, Void, Boolean> {
        private Topic mTopic;

        CreateSharePictureTask(Topic topic) {
            super();
            mTopic = topic;
        }

        @Override
        protected Boolean doInBackground(View... params) {
            return BitmapUtil.saveViewAsImage(params[0], "topic_" + mTopic.getId());
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            int msgId = isSuccess ? R.string.save_pic_success : R.string.save_error;
            ToastUtils.showShort(msgId);
        }
    }
}

