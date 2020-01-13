package com.tag.app.tagnearemployee.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.boardingscreens.boardingactivity.BoardingActivity;
import com.tag.app.tagnearemployee.dashboard.DashBoardActivity;
import com.tag.app.tagnearemployee.pojomodels.Employee;

public class SplashActivity extends AppCompatActivity
{   private final int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() ->
        {   SharedPreference.getInstance().initialize(getApplicationContext());
            Intent intent = null;
            Employee user = new Gson().fromJson(SharedPreference.getInstance().getString("CUSTOMERLOGIN"), Employee.class);
            if (user != null)
            { intent = new Intent(this,DashBoardActivity.class); }
            else
            { intent = new Intent(this,BoardingActivity.class); }
            startActivity(intent);
            finish();
        }, SPLASH_TIME_OUT);
    } }


