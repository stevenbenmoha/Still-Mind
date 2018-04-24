package io.github.stevenbenmoha.stillmind;

import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.glomadrian.grav.GravView;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.ohoussein.playpause.PlayPauseView;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class SessionActivity extends AppCompatActivity {


    CircularProgressBar progressBar;
    CountDownTimer cTimer = null;
    PlayPauseView view;
    TextView timeRemaining;
    ImageView sessionSettings;
    NumberFormat f = new DecimalFormat("00");
    boolean isPaused = false;
    long millisLeft;
    GravView clouds;
    int hour;
    int min;
    long sec;
    long hours;
    long mins;
    long sessionLength = 5000;
    long origTime = 5000;
    boolean isNewTime = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        progressBar = (CircularProgressBar) findViewById(R.id.timer);
        clouds = (GravView) findViewById(R.id.grav);
        clouds.setVisibility(View.VISIBLE);
        sessionSettings = (ImageView) findViewById(R.id.sessionSettings);
        timeRemaining = (TextView) findViewById(R.id.timeRemaining);
        timeRemaining.setText(f.format(0) + ":" + f.format(0) + ":" + f.format(0));
        TextView tv = (TextView) findViewById(R.id.timeRemaining);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/roboto/Roboto-Light.ttf");
        tv.setTypeface(face);
        view = (PlayPauseView) findViewById(R.id.play_pause_view);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view.toggle();

                if (cTimer != null) {

                    if (isPaused) {
                        resume();
                        fadeOutAndHideButton(view);
                        fadeIn(clouds);

                    } else {
                        pause();
                        fadeInButton(view);
                        fadeOutAndHideButton(clouds);
                    }
                } else {
                    startTimer(sessionLength);

                    fadeOutAndHideButton(view);
                }

            }
        });


        sessionSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog timePickerDialog = new TimePickerDialog(SessionActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
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
                                isNewTime = true;
                                timeRemaining.setText(f.format(hours) + ":" + f.format(mins) + ":" + f.format(00));
                                progressBar.setProgress(0);
                                view.setClickable(true);


                            }
                        }, hour, min, true);// Yes 24 hour time


                timePickerDialog.setTitle("Choose duration (hrs/mins): ");
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();

            }
        });


    }

    private void startTimer(final long sessionLength) {

        if (isNewTime) {

            origTime = sessionLength;

        }

        cTimer = new CountDownTimer(sessionLength, 10) {
            public void onTick(long millisUntilFinished) {


                long hourCount = (millisUntilFinished / 3600000) % 24;
                long minCount = (millisUntilFinished / 60000) % 60;
                long secCount = (millisUntilFinished / 1000) % 60;

                timeRemaining.setText(f.format(hourCount) + ":" + f.format(minCount) + ":" + f.format(secCount));

                double timeLeft = millisUntilFinished/10;
                double sessionDivisor = origTime / 10;


                Log.d("i", "sessionLength: " + Double.toString(sessionLength));

                float ratio = (float) (100.0f - (float) (100.0f * (float) (timeLeft / sessionDivisor)));

                Log.d("i", "Ratio: " + Double.toString(ratio));

                progressBar.setProgress(ratio);
                millisLeft = millisUntilFinished;
                isNewTime = false;


            }

            public void onFinish() {

                progressBar.setProgress(100);
                view.toggle();
                fadeIn(view);
                //save session
            }
        };
        cTimer.start();

    }

    /**
     * Start or Resume the countdown.
     *
     * @return CountDownTimerPausable current instance
     */
    public void resume() {
        if (isPaused && (!isNewTime)) {
            startTimer(millisLeft);
            isPaused = false;
        }

        if (isPaused && isNewTime) {
            startTimer(sessionLength);
            isPaused = false;
        }


    }

    private void pause() throws IllegalStateException {
        if (isPaused == false) {
            cTimer.cancel();
        } else {
            throw new IllegalStateException("CountDownTimerPausable is already in pause state, start counter before pausing it.");
        }
        isPaused = true;
    }

    public boolean isPaused() {
        return isPaused;
    }

    private void fadeOutAndHideButton(final View view) {


        Animation fadeOut = new AlphaAnimation(1f, 0f);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(2000);
        fadeOut.setFillAfter(true);

        view.startAnimation(fadeOut);
    }


    private void fadeIn(final View view) {
        Animation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(2000);
        fadeIn.setFillAfter(true);
        view.startAnimation(fadeIn);
    }

    private void fadeInButton(final View view) {
        Animation fadeIn = new AlphaAnimation(.5f, 1f);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(10);
        fadeIn.setFillAfter(true);
        view.startAnimation(fadeIn);
    }


    public void saveSession() {





    }



}