package xunhu.test.sample.demo_xunhu.ui.fragment.fourth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xunhu.test.sample.R;
import xunhu.test.sample.demo_xunhu.base.BaseLazyMainFragment;
import xunhu.test.sample.demo_xunhu.ui.fragment.fourth.child.AvatarFragment;
import xunhu.test.sample.demo_xunhu.ui.fragment.fourth.child.MeFragment;

/**
 * Created by YoKeyword on 16/6/3.
 */
public class ZhihuFourthFragment extends BaseLazyMainFragment {
    private Toolbar mToolbar;
    private View mView;

    public static ZhihuFourthFragment newInstance() {

        Bundle args = new Bundle();

        ZhihuFourthFragment fragment = new ZhihuFourthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.xunhu_fragment_fourth, container, false);
        return mView;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            loadFragment();
        } else {  // 这里可能会出现该Fragment没被初始化时,就被强杀导致的没有load子Fragment
            if (findChildFragment(AvatarFragment.class) == null) {
                loadFragment();
            }
        }

        mToolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mToolbar.setTitle("与我相关");
        initToolbarMenu(mToolbar);

    }

    private void loadFragment() {
        loadRootFragment(R.id.fl_fourth_container_upper, AvatarFragment.newInstance());
        loadRootFragment(R.id.fl_fourth_container_lower, MeFragment.newInstance());
    }

    public void onBackToFirstFragment() {
        _mBackToFirstListener.onBackToFirstFragment();
    }
}
