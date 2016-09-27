package com.abtingramian.android.androidtest.feature.calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import com.abtingramian.android.androidtest.MainActivity;
import com.example.gramian.androidtest.R;

import java.text.DateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CalendarLayout extends LinearLayout {

    private static final String ON_RESULT_FRAGMENT_TAG = "on_result_fragment";

    @BindView(R.id.calendar)
    CalendarView calendarView;
    @BindView(R.id.pick_date)
    Button pickDate;
    @BindView(R.id.pick_time)
    Button pickTime;
    DatePickerFragment datePickerFragment;
    TimePickerFragment timePickerFragment;

    public CalendarLayout(Context context) {
        super(context);
    }

    public CalendarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        if (isInEditMode()) {
            return;
        }
        // get activity reference
        final MainActivity mainActivity = (MainActivity) getContext();
        // fragment to catch on activity result
        final Fragment onResultFragment = new OnActivityResultFragment(mainActivity, this);
        // restrict calendar to current month
        Calendar calendar = Calendar.getInstance();
        // min date
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendarView.setMinDate(calendar.getTimeInMillis());
        // max date
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendarView.setMaxDate(calendar.getTimeInMillis());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(i, i1, i2);
                calendarView.setDate(calendar.getTimeInMillis());
                Snackbar.make(CalendarLayout.this, DateFormat.getDateInstance().format(calendarView.getDate()), Snackbar.LENGTH_LONG).show();
            }
        });
        datePickerFragment = new DatePickerFragment();
        pickDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFragment.setTargetFragment(onResultFragment, DatePickerFragment.REQUEST_CODE_DEFAULT);
                datePickerFragment.show(mainActivity.getSupportFragmentManager(), DatePickerFragment.class.getSimpleName());
            }
        });
        timePickerFragment = new TimePickerFragment();
        pickTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerFragment.setTargetFragment(onResultFragment, TimePickerFragment.REQUEST_CODE_DEFAULT);
                timePickerFragment.show(mainActivity.getSupportFragmentManager(), TimePickerFragment.class.getSimpleName());
            }
        });
        // add on result fragment on ui thread
        post(new Runnable() {
            @Override
            public void run() {
                mainActivity.getSupportFragmentManager().beginTransaction().add(onResultFragment, ON_RESULT_FRAGMENT_TAG).commitNow();
            }
        });
    }

    public void dismissDialogsFragments() {
        if (datePickerFragment.getDialog() != null) {
            datePickerFragment.dismiss();
        }
        if (timePickerFragment.getDialog() != null) {
            timePickerFragment.dismiss();
        }
    }

    public static class OnActivityResultFragment extends Fragment {

        MainActivity mainActivity;
        CalendarLayout view;

        public OnActivityResultFragment(MainActivity mainActivity, CalendarLayout view) {
            this.mainActivity = mainActivity;
            this.view = view;
        }

        public OnActivityResultFragment() {

        }

        @Override
        public void onPause() {
            super.onPause();
            view.dismissDialogsFragments();
            mainActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Calendar calendar = Calendar.getInstance();
                switch (requestCode) {
                    case DatePickerFragment.REQUEST_CODE_DEFAULT:
                        calendar.set(data.getIntExtra(DatePickerFragment.DATE_PICKER_YEAR, calendar.get(Calendar.YEAR)),
                                data.getIntExtra(DatePickerFragment.DATE_PICKER_MONTH, calendar.get(Calendar.MONTH)),
                                data.getIntExtra(DatePickerFragment.DATE_PICKER_DAY, calendar.get(Calendar.DAY_OF_MONTH)));
                        Snackbar.make(view, DateFormat.getDateInstance().format(calendar.getTime()), Snackbar.LENGTH_LONG).show();
                        break;
                    case TimePickerFragment.REQUEST_CODE_DEFAULT:
                        calendar.set(0, 0, 0,
                                data.getIntExtra(TimePickerFragment.TIME_PICKER_HOUR, calendar.get(Calendar.HOUR)),
                                data.getIntExtra(TimePickerFragment.TIME_PICKER_MINUTE, calendar.get(Calendar.MINUTE)),
                                0);
                        Snackbar.make(view, DateFormat.getTimeInstance().format(calendar.getTime()), Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
            mainActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        }

    }

}
