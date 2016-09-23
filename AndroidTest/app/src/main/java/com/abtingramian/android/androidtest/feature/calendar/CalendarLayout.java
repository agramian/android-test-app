package com.abtingramian.android.androidtest.feature.calendar;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.widget.CalendarView;
import android.widget.FrameLayout;

import com.example.gramian.androidtest.R;

import java.text.DateFormat;
import java.util.Calendar;

public class CalendarLayout extends FrameLayout {

    CalendarView calendarView;

    public CalendarLayout(Context context) {
        super(context);
    }

    public CalendarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        calendarView = (CalendarView) findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(i, i1, i2);
                calendarView.setDate(calendar.getTimeInMillis());
                Snackbar.make(CalendarLayout.this, DateFormat.getDateInstance().format(calendarView.getDate()), Snackbar.LENGTH_LONG).show();
            }
        });

    }
}
