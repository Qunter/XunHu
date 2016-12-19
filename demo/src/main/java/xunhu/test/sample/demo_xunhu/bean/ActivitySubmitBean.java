package xunhu.test.sample.demo_xunhu.bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ActivitySubmitBean extends BmobObject {

    private String title,content;
    Date mDate;
    private BmobDate startDate,endDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String Title) {
        this.title = Title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStartDate(BmobDate startDate){
        this.startDate = startDate;
    }

    public BmobDate getStartDate(){
        return startDate;
    }

    public void setEndDate(BmobDate endDate){
        this.endDate = endDate;
    }

    public BmobDate getEndDate(){
        return endDate;
    }

}
