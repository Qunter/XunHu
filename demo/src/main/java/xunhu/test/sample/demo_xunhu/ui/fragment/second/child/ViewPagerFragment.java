package xunhu.test.sample.demo_xunhu.ui.fragment.second.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xunhu.test.sample.R;
import xunhu.test.sample.demo_xunhu.adapter.ZhihuPagerFragmentAdapter;
import xunhu.test.sample.demo_xunhu.base.BaseFragment;

/**
 * Created by YoKeyword on 16/6/5.
 */
public class ViewPagerFragment extends BaseFragment {
    private TabLayout mTab;
    private ViewPager mViewPager;

    public static ViewPagerFragment newInstance() {

        Bundle args = new Bundle();

        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xunhu_fragment_second_pager, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTab = (TabLayout) view.findViewById(R.id.tab);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());

        mViewPager.setAdapter(new ZhihuPagerFragmentAdapter(getChildFragmentManager()));
        mTab.setupWithViewPager(mViewPager);
    }
}
