package xunhu.test.sample.demo_xunhu.bean;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/10/10.
 */
public class SchoolInformation {

    private BmobFile icon;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String Title) {
        this.title = Title;
    }

    public BmobFile getIcon() {
        return icon;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }
}
