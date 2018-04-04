package com.yueyue.readhub.feature.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.SparseArray;
import android.view.View;

import com.yueyue.readhub.R;
import com.yueyue.readhub.base.BaseFragment;
import com.yueyue.readhub.base.BaseListFragment;
import com.yueyue.readhub.component.PLog;
import com.yueyue.readhub.feature.blockchain.BCNewsFragment;
import com.yueyue.readhub.feature.more.MoreFragment;
import com.yueyue.readhub.feature.news.NewsFragment;
import com.yueyue.readhub.feature.technews.TechNewsFragment;
import com.yueyue.readhub.feature.topic.list.HotTopicFragment;

import java.lang.reflect.Field;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * author : yueyue on 2018/4/3 19:55
 * desc   :
 */
public class MainFragment extends BaseFragment {

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;

    private SparseArray<SupportFragment> mFragments = new SparseArray<>(5);

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragments();
        initBottomNavigationView();
        initListener();
    }

    private void initBottomNavigationView() {
        //如何去掉BottomNavigationView的Item大于3个时的动画效果 - CSDN博客
        // https://blog.csdn.net/aiynmimi/article/details/72967585
        BottomNavigationMenuView menuView =
                (BottomNavigationMenuView) mBottomNavigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            PLog.e("initBottomNavigationView", "Unable to get shift mode field" + e.toString());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            PLog.e("initBottomNavigationView", "Unable to change value of shift mode" + e.toString());
            e.printStackTrace();
        }
    }

    private void initFragments() {
        if (findFragment(HotTopicFragment.class) == null) {
            mFragments.clear();
            mFragments.put(R.id.menu_item_hot_topic, HotTopicFragment.newInstance());
            mFragments.put(R.id.menu_item_news, NewsFragment.newInstance());
            mFragments.put(R.id.menu_item_tech_news, TechNewsFragment.newInstance());
            mFragments.put(R.id.menu_item_block_chain, BCNewsFragment.newInstance());
            mFragments.put(R.id.menu_item_more, MoreFragment.newInstance());

            loadMultipleRootFragment(R.id.frame_main_content, 0,
                    mFragments.get(R.id.menu_item_hot_topic),
                    mFragments.get(R.id.menu_item_news),
                    mFragments.get(R.id.menu_item_tech_news),
                    mFragments.get(R.id.menu_item_block_chain),
                    mFragments.get(R.id.menu_item_more));
        } else {
            mFragments.put(R.id.menu_item_hot_topic, findFragment(HotTopicFragment.class));
            mFragments.put(R.id.menu_item_news, findFragment(NewsFragment.class));
            mFragments.put(R.id.menu_item_tech_news, findFragment(TechNewsFragment.class));
            mFragments.put(R.id.menu_item_block_chain, findFragment(BCNewsFragment.class));
            mFragments.put(R.id.menu_item_more, findFragment(MoreFragment.class));
        }

    }


    private void initListener() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            ISupportFragment currentFragment;
            switch (item.getItemId()) {
                case R.id.menu_item_hot_topic:
                    currentFragment = mFragments.get(R.id.menu_item_hot_topic);
                    break;
                case R.id.menu_item_news:
                    currentFragment = mFragments.get(R.id.menu_item_news);
                    break;
                case R.id.menu_item_tech_news:
                    currentFragment = mFragments.get(R.id.menu_item_tech_news);
                    break;
                case R.id.menu_item_block_chain:
                    currentFragment = mFragments.get(R.id.menu_item_block_chain);
                    break;
                case R.id.menu_item_more:
                    currentFragment = mFragments.get(R.id.menu_item_more);
                    break;
                default:
                    currentFragment = mFragments.get(R.id.menu_item_hot_topic);
            }

            if (currentFragment instanceof BaseListFragment) {
                //当前页面已经为对应页面时，则回到顶部或刷新
                ((BaseListFragment) currentFragment).onTabClick();
            } else if (currentFragment instanceof MoreFragment) {
                ((MoreFragment) currentFragment).onTabClick();
            }
            showHideFragment(currentFragment);
            return true;
        });
    }
}
