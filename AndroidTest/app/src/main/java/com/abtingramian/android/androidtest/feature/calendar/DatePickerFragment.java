package com.abtingramian.android.androidtest.feature.calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final int REQUEST_CODE_DEFAULT = 200;
    public static final String KEY_REQUEST_CODE = "request_code";
    public static final String DATE_PICKER_YEAR = "date_picker_year";
    public static final String DATE_PICKER_MONTH = "date_picker_month";
    public static final String DATE_PICKER_DAY = "date_picker_day";

    int requestCode = REQUEST_CODE_DEFAULT;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get request code or use default if not supplied
        if (getArguments() != null) {
            requestCode = getArguments().getInt(KEY_REQUEST_CODE, REQUEST_CODE_DEFAULT);
        }
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Intent intent = new Intent();
        intent.putExtra(DATE_PICKER_YEAR, year);
        intent.putExtra(DATE_PICKER_MONTH, month);
        intent.putExtra(DATE_PICKER_DAY, day);
        getTargetFragment().onActivityResult(requestCode, Activity.RESULT_OK, intent);
    }

}
