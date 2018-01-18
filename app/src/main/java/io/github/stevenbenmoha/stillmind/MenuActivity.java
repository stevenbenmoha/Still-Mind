package io.github.stevenbenmoha.stillmind;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import info.hoang8f.widget.FButton;

public class MenuActivity extends AppCompatActivity {


    info.hoang8f.widget.FButton beginSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TextView tv = (TextView) findViewById(R.id.menu);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/roboto/Roboto-Light.ttf");
        tv.setTypeface(face);

        beginSession = (FButton) findViewById(R.id.BeginSession);

        beginSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MenuActivity.this, SessionActivity.class));


            }
        });




    }
            }


