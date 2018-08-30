package soyouarehere.imwork.speed.view.calendar.interf;


import soyouarehere.imwork.speed.view.calendar.model.CalendarDate;

/**
 * Created by ldf on 17/6/2.
 */

public interface OnSelectDateListener {
    void onSelectDate(CalendarDate date);

    void onSelectOtherMonth(int offset);//点击其它月份日期
}
