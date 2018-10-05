package soyouarehere.imwork.speed.example.xiaomi_calendar.interf;


import soyouarehere.imwork.speed.example.xiaomi_calendar.model.CalendarDate;

/**
 * Created by ldf on 17/6/2.
 */

public interface OnSelectDateListener {
    void onSelectDate(CalendarDate date);

    void onSelectOtherMonth(int offset);//点击其它月份日期
}
