package xunhu.test.sample.demo_xunhu.timePicket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import xunhu.test.sample.R;

@SuppressLint("SimpleDateFormat")
public class TimePickerShow {

	private Context context;
	private WheelMain wheelMain;

	public TimePickerShow(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 获得选中的时间
	 * 
	 * @param strYear
	 *            间隔符号
	 * @param strMon
	 * @param strDay
	 * @param strHour
	 * @param strMins
	 * @param strSecond
	 * @return
	 */
	public String getTxtTime(String strYear, String strMon, String strDay,
			String strHour, String strMins, String strSecond) {
		return wheelMain.getTime(strYear, strMon, strDay, strHour, strMins,
				strSecond);
	}

	public View timePickerView() {
		View timepickerview = View.inflate(context, R.layout.timepicker, null);
		wheelMain = new WheelMain(timepickerview);
		// 获取当前时间
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		wheelMain.setSTART_YEAR(year);
		wheelMain.setEND_YEAR(year+1);// 设置最大年份
		wheelMain.initDateTimePicker(year, month, day, hour, min, second);

		return timepickerview;
	}

	/**
	 * 时间选择控件
	 * 
	 * @param dateStr
	 *            需显示的日期
	 * @return
	 */
	public View timePickerView(String dateStr) {
		View timepickerview = View.inflate(context, R.layout.timepicker, null);
		wheelMain = new WheelMain(timepickerview);
		// 获取当前时间
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		// int hour = calendar.get(Calendar.HOUR_OF_DAY);
		// int min = calendar.get(Calendar.MINUTE);
		// int second = calendar.get(Calendar.SECOND);
		wheelMain.setEND_YEAR(year);
		// 若为空显示当前时间
		if (dateStr != null && !dateStr.equals("")) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = format.parse(dateStr);
				calendar.setTime(date);
				year = calendar.get(Calendar.YEAR);
				month = calendar.get(Calendar.MONTH);
				day = calendar.get(Calendar.DAY_OF_MONTH);
				wheelMain.initDateTimePicker(year, month, day, -1, -1, -1);// 传-1表示不显示
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			wheelMain.initDateTimePicker(year, month, day, -1, -1, -1);
		}
		return timepickerview;
	}

}
