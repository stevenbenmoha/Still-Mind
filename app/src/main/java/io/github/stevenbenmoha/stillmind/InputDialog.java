package io.github.stevenbenmoha.stillmind;

/**
 * Created by SESA462608 on 1/20/2018.
 */

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;



public class InputDialog extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {


    long sessionLength;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);


        final TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);

                    long hourOfDayLong = (1000*3600*hourOfDay);
                    long minuteLong = (1000*60*minute);
                    long total = hourOfDayLong+minuteLong;


                    sessionLength = total;

                }
            }

        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, 0, 0, true);
        timePickerDialog.setTitle("Choose duration (hrs/mins): ");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();

        return timePickerDialog;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {



    }


    public long getSessionLength() {

        return sessionLength;

    }


}