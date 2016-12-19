package xunhu.test.sample.demo_xunhu.ui.fragment.third.child;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.SaveListener;
import xunhu.test.fragmentation.SupportFragment;
import xunhu.test.sample.R;
import xunhu.test.sample.demo_xunhu.base.BaseFragment;
import xunhu.test.sample.demo_xunhu.bean.ActivitySubmitBean;
import xunhu.test.sample.demo_xunhu.timePicket.TimePickerShow;
import xunhu.test.sample.demo_xunhu.ui.fragment.third.child.child.ContentFragment;


/**
 * Created by YoKeyword on 16/2/4.
 */
public class ShopFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = ShopFragment.class.getSimpleName();

    private LinearLayout linearLayout;
    private TimePickerShow timePickerShow;

    private Toolbar mToolbar;
    private EditText mSubmitTitleEt,mSubmitContentEt;
    private Button mSubmitBtn,mGetStartTimeBtn,mGetEndTimeBtn;
    private Date mStartDate = null,mEndDate = null;

    private static final int MSG_SUBMIT_SUCCESS=0x2003;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==MSG_SUBMIT_SUCCESS){
                mSubmitTitleEt.setText("");
                mSubmitContentEt.setText("");
                mGetStartTimeBtn.setText("获取活动开始时间");
                mGetEndTimeBtn.setText("获取活动结束时间");

            }
        }
    };

    public static ShopFragment newInstance() {
        Bundle args = new Bundle();

        ShopFragment fragment = new ShopFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.date_view);
        timePickerShow = new TimePickerShow(getContext());
        initView(view, savedInstanceState);

        return view;
    }

    private void initView(View view, Bundle savedInstanceState) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mSubmitTitleEt = (EditText) view.findViewById(R.id.submit_title_et);
        mSubmitContentEt = (EditText) view.findViewById(R.id.submit_content_et);

        mSubmitBtn = (Button) view.findViewById(R.id.submit_btn);
        mGetStartTimeBtn = (Button) view.findViewById(R.id.get_start_time);
        mGetEndTimeBtn = (Button) view.findViewById(R.id.get_end_time);
        mSubmitBtn.setOnClickListener(this);
        mGetStartTimeBtn.setOnClickListener(this);
        mGetEndTimeBtn.setOnClickListener(this);
        linearLayout.addView(timePickerShow.timePickerView());

        Bmob.initialize(this.getActivity(),"9b1d4c9589d7d59f2f834e34547ca4c4");
        mToolbar.setTitle("发起活动");
        initToolbarMenu(mToolbar);




    }

    @Override
    public boolean onBackPressedSupport() {
        // ContentFragment是ShopFragment的栈顶子Fragment,会先调用ContentFragment的onBackPressedSupport方法
        //Toast.makeText(_mActivity, "onBackPressedSupport-->返回false,交给上层处理!", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 替换加载 内容Fragment
     *
     * @param fragment
     */
    public void switchContentFragment(ContentFragment fragment) {
        SupportFragment contentFragment = findChildFragment(ContentFragment.class);
        if (contentFragment != null) {
            contentFragment.replaceFragment(fragment, false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_btn: {
                String SubmitTitle = mSubmitTitleEt.getText().toString();
                String SubmitContent = mSubmitContentEt.getText().toString();

                if (SubmitTitle.equals("") || SubmitContent.equals("")||mGetStartTimeBtn.getText().toString().equals("获取活动开始时间")||mGetEndTimeBtn.getText().toString().equals("获取活动结束时间")) {
                    Toast.makeText(getContext(), "标题、内容为空或活动时间未获取  请检查后再次提交", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    BmobDate SubmitStartDate = new BmobDate(mStartDate);
                    BmobDate SubmitEndDate = new BmobDate(mEndDate);
                    ActivitySubmitBean ActStBean = new ActivitySubmitBean();
                    ActStBean.setTitle(SubmitTitle);
                    ActStBean.setContent(SubmitContent);
                    ActStBean.setStartDate(SubmitStartDate);
                    ActStBean.setEndDate(SubmitEndDate);
                    ActStBean.save(this.getActivity(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getContext(), "活动发布成功", Toast.LENGTH_LONG).show();
                            mHandler.sendEmptyMessage(MSG_SUBMIT_SUCCESS);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(getContext(), "活动发布失败", Toast.LENGTH_LONG).show();
                        }
                    });

                }
                break;
            }
            case R.id.get_start_time:{
                try {
                    SimpleDateFormat mSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String DateString = timePickerShow.getTxtTime("-", "-", " ", ":", ":", "");
                    mStartDate = mSDF.parse(DateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mGetStartTimeBtn.setText(timePickerShow.getTxtTime("-", "-", " ", ":", ":", ""));
                break;
            }
            case R.id.get_end_time:{
                try {
                    SimpleDateFormat mSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String DateString = timePickerShow.getTxtTime("-", "-", " ", ":", ":", "");
                    mEndDate = mSDF.parse(DateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mGetEndTimeBtn.setText(timePickerShow.getTxtTime("-", "-", " ", ":", ":", ""));
                break;
            }
        }
    }
}
