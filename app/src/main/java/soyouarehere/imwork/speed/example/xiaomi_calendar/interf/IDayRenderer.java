package soyouarehere.imwork.speed.example.xiaomi_calendar.interf;

import android.graphics.Canvas;

import soyouarehere.imwork.speed.example.xiaomi_calendar.view.Day;


/**
 * Created by ldf on 17/6/26.
 */

public interface IDayRenderer {

    void refreshContent();

    void drawDay(Canvas canvas, Day day);

    IDayRenderer copy();

}
