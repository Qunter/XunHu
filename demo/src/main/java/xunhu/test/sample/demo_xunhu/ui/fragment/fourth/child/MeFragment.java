package xunhu.test.sample.demo_xunhu.ui.fragment.fourth.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xunhu.test.sample.R;
import xunhu.test.sample.demo_xunhu.base.BaseFragment;
import xunhu.test.sample.demo_xunhu.ui.fragment.fourth.ZhihuFourthFragment;

/**
 * Created by YoKeyword on 16/6/6.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {
    private TextView mTvBtnMyInf,mTvBtnSettings,mTvBtnShare,mTvBtnSent,mTvBtnAbout;

    public static MeFragment newInstance() {

        Bundle args = new Bundle();

        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xunhu_fragment_fourth_me, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTvBtnMyInf = (TextView) view.findViewById(R.id.tv_btn_myinf);
        mTvBtnSettings = (TextView) view.findViewById(R.id.tv_btn_settings);
        mTvBtnShare = (TextView) view.findViewById(R.id.tv_btn_share);
        mTvBtnSent = (TextView) view.findViewById(R.id.tv_btn_sent);
        mTvBtnAbout = (TextView) view.findViewById(R.id.tv_btn_about);
        mTvBtnMyInf.setOnClickListener(this);
        mTvBtnSettings.setOnClickListener(this);
        mTvBtnShare.setOnClickListener(this);
        mTvBtnSent.setOnClickListener(this);
        mTvBtnAbout.setOnClickListener(this);


    }

    @Override
    public boolean onBackPressedSupport() {
        // 这里实际项目中推荐使用 EventBus接耦
        ((ZhihuFourthFragment)getParentFragment()).onBackToFirstFragment();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_btn_myinf:
                start(MyinfFragment.newInstance());
                break;

            case R.id.tv_btn_settings:
                start(SettingsFragment.newInstance());
                break;

            case R.id.tv_btn_share:
                start(ShareFragment.newInstance());
                break;

            case R.id.tv_btn_sent:
                start(SentFragment.newInstance());
                break;

            case R.id.tv_btn_about:
                start(AboutFragment.newInstance());
                break;
        }

    }
}
