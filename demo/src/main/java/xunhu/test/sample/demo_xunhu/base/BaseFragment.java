package xunhu.test.sample.demo_xunhu.base;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import xunhu.test.fragmentation.SupportFragment;
import xunhu.test.sample.R;

/**
 * Created by YoKeyword on 16/2/3.
 */
public class BaseFragment extends SupportFragment {
    private static final String TAG = "Fragmentation";

    protected void initToolbarMenu(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.hierarchy);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //显示栈视图以及日志，调试时使用
                /*
                switch (item.getItemId()) {
                    case R.id.action_hierarchy:
                        _mActivity.showFragmentStackHierarchyView();
                        _mActivity.logFragmentStackHierarchy(TAG);
                        break;
                }*/
                return true;
            }
        });
    }
}
