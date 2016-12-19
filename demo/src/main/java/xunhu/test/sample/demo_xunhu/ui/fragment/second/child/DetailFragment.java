package xunhu.test.sample.demo_xunhu.ui.fragment.second.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import xunhu.test.sample.R;
import xunhu.test.sample.demo_xunhu.base.BaseBackFragment;

/**
 * Created by YoKeyword on 16/2/3.
 */
public class DetailFragment extends BaseBackFragment {
    public static final String TAG = DetailFragment.class.getSimpleName();
    private static final int REQ_MODIFY_FRAGMENT = 100;
    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_CONTENT = "arg_content";
    private static final String ARG_CONTENTSTTIME = "arg_contentStTime";
    private static final String ARG_CONTENTEDTIME = "arg_contentEdTime";

    static final String KEY_RESULT_TITLE = "title";

    private Toolbar mToolbar;
    private TextView mTvContent,mTvContent_StTime,mTvContent_EdTime;
    private String mTitle,mContent,mContentStTime,mContentEdTime;


    public static DetailFragment newInstance(String title,String content,String contentStTime,String contentEdTime) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        bundle.putString(ARG_CONTENT,content);
        bundle.putString(ARG_CONTENTSTTIME,contentStTime);
        bundle.putString(ARG_CONTENTEDTIME,contentEdTime);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(ARG_TITLE);
            mContent = bundle.getString(ARG_CONTENT);
            mContentStTime = bundle.getString(ARG_CONTENTSTTIME);
            mContentEdTime = bundle.getString(ARG_CONTENTEDTIME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
        mTvContent_StTime = (TextView) view.findViewById(R.id.tv_content_start_time);
        mTvContent_EdTime = (TextView) view.findViewById(R.id.tv_content_end_time);
        mToolbar.setTitle(mTitle);

        initToolbarNav(mToolbar);
    }

    /**
     * 这里演示:
     * 比较复杂的Fragment页面会在第一次start时,导致动画卡顿
     * Fragmentation提供了onEnterAnimationEnd()方法,该方法会在 入栈动画 结束时回调
     * 所以在onCreateView进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     */
    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        initDelayView();
    }

    private void initDelayView() {
        mTvContent.setText("         "+mContent);
        mTvContent_StTime.setText("         "+mContentStTime);
        mTvContent_EdTime.setText("         "+mContentEdTime);

    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_MODIFY_FRAGMENT && resultCode == RESULT_OK && data != null) {
            mTitle = data.getString(KEY_RESULT_TITLE);
            mToolbar.setTitle(mTitle);
            // 保存被改变的 title
            getArguments().putString(ARG_TITLE, mTitle);
            Toast.makeText(_mActivity, "修改标题成功!", Toast.LENGTH_SHORT).show();
        }
    }
}
