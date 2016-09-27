package com.abtingramian.android.androidtest.feature.calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public static final int REQUEST_CODE_DEFAULT = 300;
    public static final String KEY_REQUEST_CODE = "request_code";
    public static final String TIME_PICKER_HOUR = "time_picker_hour";
    public static final String TIME_PICKER_MINUTE = "time_picker_minute";

    int requestCode = REQUEST_CODE_DEFAULT;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get request code or use default if not supplied
        if (getArguments() != null) {
            requestCode = getArguments().getInt(KEY_REQUEST_CODE, REQUEST_CODE_DEFAULT);
        }
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Intent intent = new Intent();
        intent.putExtra(TIME_PICKER_HOUR, hourOfDay);
        intent.putExtra(TIME_PICKER_MINUTE, minute);
        getTargetFragment().onActivityResult(requestCode, Activity.RESULT_OK, intent);
    }

}
