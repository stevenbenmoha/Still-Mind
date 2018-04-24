package io.github.stevenbenmoha.stillmind;

import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TimePicker;

import info.hoang8f.widget.FButton;

/**
 * Created by SESA462608 on 4/12/2018.
 */

public class SettingsActivity extends AppCompatActivity  {

    info.hoang8f.widget.FButton setDefaultButton;
    info.hoang8f.widget.FButton logoutButton;

    long hours;
    long mins;
    long sessionLength = 0;
    int hour;
    int min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setDefaultButton = (FButton) findViewById(R.id.SetDefault);
        logoutButton = (FButton) findViewById(R.id.Logout);

        setDefaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog timePickerDialog = new TimePickerDialog(SettingsActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker,
                                                  int selectedHour, int selectedMinute) {

                                if (Build.VERSION.SDK_INT >= 23) {
                                    hours = timePicker.getHour();
                                    mins = timePicker.getMinute();
                                } else {
                                    hours = timePicker.getCurrentHour();
                                    mins = timePicker.getCurrentMinute();
                                }

                                long hourOfDayLong = (1000 * 3600 * hours);
                                long minuteLong = (1000 * 60 * mins);
                                long total = hourOfDayLong + minuteLong;

                                sessionLength = total;

                            }
                        }, hour, min, true);// Yes 24 hour time


                timePickerDialog.setTitle("Choose default meditation length in hrs/mins: ");
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();

            }
        });


    }

}
