package io.github.stevenbenmoha.stillmind;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import info.hoang8f.widget.FButton;

/**
 * Created by SESA462608 on 4/12/2018.
 */

public class SettingsActivity extends AppCompatActivity  {

    info.hoang8f.widget.FButton setDefaultButton;
    info.hoang8f.widget.FButton logoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setDefaultButton = (FButton) findViewById(R.id.SetDefault);
        logoutButton = (FButton) findViewById(R.id.Logout);


    }

}
