package xunhu.test.sample.demo_xunhu.ui.fragment.second.child.childpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import xunhu.test.fragmentation.SupportFragment;
import xunhu.test.sample.R;
import xunhu.test.sample.demo_xunhu.MainActivity;
import xunhu.test.sample.demo_xunhu.adapter.HomeAdapter;
import xunhu.test.sample.demo_xunhu.bean.ActivitySubmitBean;
import xunhu.test.sample.demo_xunhu.entity.Article;
import xunhu.test.sample.demo_xunhu.event.TabSelectedEvent;
import xunhu.test.sample.demo_xunhu.listener.OnItemClickListener;
import xunhu.test.sample.demo_xunhu.base.BaseFragment;
import xunhu.test.sample.demo_xunhu.ui.fragment.second.child.DetailFragment;

/**
 * Created by YoKeyword on 16/6/3.
 */
public class FirstPagerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecy;
    private SwipeRefreshLayout mRefreshLayout;

    private HomeAdapter mAdapter;

    private static final int MSG_SUCCESS=0x2001;
    private static final int MSG_REFRESH=0x2002;
    private boolean mAtTop = true;

    private int mScrollTotal,sum;
    //这里暂时不清楚应该怎么处理，以定义数组的方式来处理，但是很不好，暂时定为50，需要修改方式！！！
    private String[] mTitles = new String[50];
    private String[] mContents = new String[50];
    private String[] mContentStTime = new String[50];
    private String[] mContentEdTime = new String[50];
    /**
     * 以下方query函数在bmob上查询信息，接收了之后导入到数组中，然后用handler传递到主线程进行adapter的相关设置
     */
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SUCCESS:{
                    List<Article> articleList = new ArrayList<>();
                    for (int i = 0; i < sum; i++) {
                        Article article = new Article(mTitles[i], mContents[i]);
                        articleList.add(article);
                        //Toast.makeText(getContext(), "成功"+article.getTitle(), Toast.LENGTH_LONG).show();
                    }
                    mRecy.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                            // 这里的DetailFragment在flow包里
                            // 这里是父Fragment启动,要注意 栈层级
                            ((SupportFragment) getParentFragment()).start(DetailFragment.newInstance(mAdapter.getItem(position).getTitle(),mAdapter.getItem(position).getContent(),mContentStTime[position],mContentEdTime[position]));
                        }
                    });
                    mAdapter.setDatas(articleList);
                    Toast.makeText(getContext(), "加载数据成功", Toast.LENGTH_LONG).show();
                    break;}
                case MSG_REFRESH:{
                    List<Article> articleList = new ArrayList<>();
                    for (int i = 0; i < sum; i++) {
                        Article article = new Article(mTitles[i], mContents[i]);
                        articleList.add(article);
                        //Toast.makeText(getContext(), "成功"+article.getTitle(), Toast.LENGTH_LONG).show();
                    }
                    mAdapter.setDatas(articleList);
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "刷新列表成功", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
    };
    public static FirstPagerFragment newInstance() {

        Bundle args = new Bundle();

        FirstPagerFragment fragment = new FirstPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xunhu_fragment_second_pager_first, container, false);
        EventBus.getDefault().register(this);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(this);

        Bmob.initialize(this.getActivity(),"9b1d4c9589d7d59f2f834e34547ca4c4");



        mAdapter = new HomeAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);


        // Init Datas
        query();

        //Toast.makeText(getContext(), "成功"+sum, Toast.LENGTH_LONG).show();


        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0) {
                    mAtTop = true;
                } else {
                    mAtTop = false;
                }
            }
        });
    }


    private void query(){
        BmobQuery<ActivitySubmitBean> query = new BmobQuery<ActivitySubmitBean>();
        query.order("-createdAt");
        query.findObjects(this.getActivity(),new FindListener<ActivitySubmitBean>(){
            @Override
            public void onSuccess(List<ActivitySubmitBean> list) {
                int i=0;
                //SimpleDateFormat mSDF=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (ActivitySubmitBean activitysubmitbean:list){
                    mTitles[i]=activitysubmitbean.getTitle();
                    mContents[i]=activitysubmitbean.getContent();
                    mContentStTime[i]=activitysubmitbean.getStartDate().getDate();
                    mContentEdTime[i]=activitysubmitbean.getEndDate().getDate();
                    //Toast.makeText(getContext(), "成功了吗"+mTitles[i], Toast.LENGTH_LONG).show();
                    i++;
                    if(i==list.size())
                        break;
                }
                if(sum==0) {
                    sum = i;
                    mHandler.sendEmptyMessage(MSG_SUCCESS);
                }else{
                    sum = i;
                    mHandler.sendEmptyMessage(MSG_REFRESH);
                }

            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getContext(), "失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                query();

                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
    }

    /**
     * 选择tab事件
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainActivity.SECOND) return;

        if (mAtTop) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecy.setAdapter(null);
        EventBus.getDefault().unregister(this);
    }

}
