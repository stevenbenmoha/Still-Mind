package io.github.stevenbenmoha.stillmind;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.graphics.Typeface;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ohoussein.playpause.*;
import com.shinelw.library.ColorArcProgressBar;
import java.util.Date;
import android.os.CountDownTimer;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import android.widget.Toast;

public class SessionActivity extends AppCompatActivity {



    ColorArcProgressBar progressBar;
    PlayPauseView view;
    int ms = 1000;
    int length = 30000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        progressBar = (ColorArcProgressBar) findViewById(R.id.timer);

         view = (PlayPauseView) findViewById(R.id.play_pause_view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.toggle();

                final int interval = 1000;
                Handler handler = new Handler();
                Runnable runnable = new Runnable(){
                    public void run() {

                        new CountDownTimer(length,ms) {


                            public void onTick(long millisUntilFinished) {



                                progressBar.setMaxValues(length/ms);

                                progressBar.setCurrentValues(length-millisUntilFinished);
                            }

                            public void onFinish() {
                               progressBar.setUnit("Complete");
                            }

                        }.start();

                    }
                };

                handler.postAtTime(runnable, System.currentTimeMillis()+interval);
                handler.postDelayed(runnable, interval);


            }
        });



    }





}