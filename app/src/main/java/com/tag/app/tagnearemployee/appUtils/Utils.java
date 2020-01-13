package com.tag.app.tagnearemployee.appUtils;

import android.app.Activity;
import android.content.Intent;

import com.tag.app.tagnearemployee.boardingscreens.boardingactivity.BoardingActivity;

public class Utils {
    public static void performLogout(Activity appCompatActivity)
    {   SharedPreference.getInstance().removeObject( "CUSTOMERREGINFO" );
        SharedPreference.getInstance().removeObject("CUSTOMERLOGINFO");
        Intent intent = new Intent(appCompatActivity, BoardingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        appCompatActivity.startActivity(intent);
        appCompatActivity.finish(); }

}

