package io.github.stevenbenmoha.stillmind;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

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
    NumberFormat f = new DecimalFormat("00");
    boolean isPaused = false;
    long millisLeft;
    GravView clouds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        progressBar = (CircularProgressBar) findViewById(R.id.timer);

        clouds = (GravView) findViewById(R.id.grav);
        clouds.setVisibility(View.VISIBLE);

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
                    startTimer(30000);
                    fadeOutAndHideButton(view);

                }

            }
        });
    }

    private  void startTimer(long sessionLength) {
        cTimer = new CountDownTimer(sessionLength, 10) {
            public void onTick(long millisUntilFinished) {


                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;

                timeRemaining.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));

                double timeLeft = millisUntilFinished/10;
                float ratio = (float) (100-(100*(timeLeft/3000)));
                progressBar.setProgress(ratio);
                millisLeft = millisUntilFinished;


            }
            public void onFinish() {

                view.toggle();
                fadeIn(view);
                
                //save session

            }
        };
        cTimer.start();

    }

    /**
     * Start or Resume the countdown.
     * @return CountDownTimerPausable current instance
     */
    public void resume(){
        if(isPaused){

            startTimer(millisLeft);
            isPaused = false;
        }
    }

    private void pause()throws IllegalStateException{
        if(isPaused==false){
            cTimer.cancel();
        } else{
            throw new IllegalStateException("CountDownTimerPausable is already in pause state, start counter before pausing it.");
        }
        isPaused = true;
    }
    public boolean isPaused() {
        return isPaused;
    }

    private void fadeOutAndHideButton(final View view)
    {


        Animation fadeOut = new AlphaAnimation(1f,0f);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(2000);
        fadeOut.setFillAfter(true);

        view.startAnimation(fadeOut);
    }


    private void fadeIn(final View view)
    {
        Animation fadeIn = new AlphaAnimation(0f,1f);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(2000);
        fadeIn.setFillAfter(true);
        view.startAnimation(fadeIn);
    }

    private void fadeInButton(final View view)
    {
        Animation fadeIn = new AlphaAnimation(.5f,1f);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(10);
        fadeIn.setFillAfter(true);
        view.startAnimation(fadeIn);
    }

}