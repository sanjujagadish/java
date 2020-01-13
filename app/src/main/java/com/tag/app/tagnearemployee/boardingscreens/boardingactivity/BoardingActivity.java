package com.tag.app.tagnearemployee.boardingscreens.boardingactivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseActivity;
import com.tag.app.tagnearemployee.boardingscreens.login.LoginFragment;

import javax.inject.Inject;

public class BoardingActivity extends BaseActivity implements BoardingContract.View
{
    @Inject
    BoardingPresenter boardingPresenter;

    public static final int PERMISSIONS_REQUEST_LOCATION = 400;

    @Override
    protected int getContentResource() {
        return R.layout.activity_boarding;
    }

    @Override
    protected void init(Bundle savedInstanceState)
    {   boardingPresenter.setView( this );

        /**
         * API CALL
         */
        replaceFragment(new LoginFragment(),LoginFragment.class.getName() );
        SharedPreference.getInstance().initialize( getApplicationContext() );

        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED )
            ActivityCompat.requestPermissions
                    (       this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE},
                            PERMISSIONS_REQUEST_LOCATION
                    );
    }

    @Override
    protected Object getModule() {
        return new BoardingModule();
    }

    @Override
    public void onFailure(Throwable throwable)
    { hideProgress();
      showToast( throwable.getMessage() ); }

    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1)
        { new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage("Are you sure you want to exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes",(dialog, id) -> finish())
                .setNegativeButton("No",null)
                .show(); }
        else if ( getFragmentManager().getBackStackEntryCount() > 1 )
        { getFragmentManager().popBackStack(); }
        else { super.onBackPressed(); } }
}
