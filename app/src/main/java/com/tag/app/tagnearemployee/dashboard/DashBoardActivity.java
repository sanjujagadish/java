package com.tag.app.tagnearemployee.dashboard;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.vision.barcode.Barcode;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.base.BaseActivity;
import com.tag.app.tagnearemployee.homescreen.HomeScreenFragment;
import com.tag.app.tagnearemployee.qrcode.QrCode.BarcodeTracker;
import com.tag.app.tagnearemployee.qrcode.QrcodeFragment;

import javax.inject.Inject;

public class DashBoardActivity extends BaseActivity implements DashBoardContract.View, BarcodeTracker.BarcodeGraphicTrackerCallback
{   @Inject
    DashBoardPresenter dashBoardPresenter;

    private HomeScreenFragment homeScreenFragment;
    public static QrcodeFragment onQrCodeDetectedListener;

    @Override
    protected int getContentResource()
    { return R.layout.activity_dash_board; }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void init(Bundle savedInstanceState)
    {   dashBoardPresenter.setView( this );
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        homeScreenFragment = new HomeScreenFragment();
        replaceFragment( homeScreenFragment, HomeScreenFragment.class.getName() ); }

    @Override
    protected Object getModule() {
        return new DashBoardModule();
    }

    @Override
    public void onFailure( Throwable throwable )
    { hideProgress();
      showToast( throwable.getMessage() ); }

    @Override
    public void onBackPressed()
    {   int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1)
        { new AlertDialog.Builder(this,R.style.AlertDialogTheme )
                .setMessage("Are you sure you want to exit?")
                .setCancelable( false )
                .setPositiveButton("Yes",(dialog, id) -> finish())
                .setNegativeButton("No",null)
                .show(); }
        else if ( getFragmentManager().getBackStackEntryCount() > 1 )
        {  getFragmentManager().popBackStack(); }
        else { super.onBackPressed(); } }

    @Override
    public void onDetectedQrCode(Barcode barcode)
    {   if (barcode != null)
        { onQrCodeDetectedListener.onQrCodeDetected(barcode.displayValue); }
        else
        { Toast.makeText(this,"Failed to detect", Toast.LENGTH_SHORT).show(); } }
}
