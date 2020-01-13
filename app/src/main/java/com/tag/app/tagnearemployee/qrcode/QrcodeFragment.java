package com.tag.app.tagnearemployee.qrcode;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.squareup.picasso.Picasso;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.dashboard.DashBoardActivity;
import com.tag.app.tagnearemployee.homescreen.HomeScreenFragment;
import com.tag.app.tagnearemployee.pojomodels.QrCodeResponse;
import com.tag.app.tagnearemployee.qrcode.QrCode.BarcodeTrackerFactory;
import com.tag.app.tagnearemployee.qrcode.QrCode.CameraSource;
import com.tag.app.tagnearemployee.qrcode.QrCode.CameraSourcePreview;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;
import static android.hardware.Camera.Parameters.FLASH_MODE_TORCH;
import static android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE;

public class QrcodeFragment extends BaseFragment implements QrCodeContract.View,OnQrCodeDetectedListener
{   @Inject
    QrCodePresenter qrCodePresenter;

    @BindView( R.id.qr_preview )
    CameraSourcePreview qr_preview;

    @BindView( R.id.toolbar )
    Toolbar toolbar;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 3000;
    private static final int RC_HANDLE_GMS = 9001;
    private CameraSource mCameraSource;
    private boolean isScanComplted;

    @Override
    protected void init(Bundle savedInstanceState)
    {   qrCodePresenter.setView(this);
        toolbar.setTitle( "QR Code Scan" );
        DashBoardActivity.onQrCodeDetectedListener = this; }

    @Override
    protected int getContentResource()
    { return R.layout.fragment_qrcode; }

    @Override
    protected Object getModule()
    { return new QrCodeModule(); }

    @Override
    public void onFailure(Throwable throwable)
    { hideProgress();
      getErrorMessage( throwable );
      customToast("Data not available",true ); }

    private boolean checkPermission()
    {  int result = ContextCompat.checkSelfPermission(getContext(), CAMERA);
       return result == PackageManager.PERMISSION_GRANTED; }

    private void requestPermission()
    { requestPermissions(new String[]{CAMERA}, CAMERA_PERMISSION_REQUEST_CODE); }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {   super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE)
        { if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            { createCameraSource(true, false);
                startCameraSource(); } } }

    @Override
    public void onResume()
    { if (checkPermission())
          { new Handler().post( new Runnable()
            {   @Override
                public void run()
                {   createCameraSource(true, false);
                    startCameraSource(); }}); }
      else
            requestPermission();
            super.onResume(); }

    private void startCameraSource() throws SecurityException
    {   int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());

        if(code != ConnectionResult.SUCCESS)
        { Dialog dlg = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(),code,RC_HANDLE_GMS);
          dlg.show(); }

        if (mCameraSource != null)
        {   try
            { qr_preview.start(mCameraSource); }
            catch (IOException e)
            {   mCameraSource.release();
                mCameraSource = null; } } }

    @SuppressLint("InlinedApi")
    private void createCameraSource(boolean autoFocus,boolean useFlash)
    {   try
        {   Context context = getContext();
            BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context)
                    .setBarcodeFormats( Barcode.ALL_FORMATS)
                    .build();
            BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(getContext());
            barcodeDetector.setProcessor(new MultiProcessor.Builder<>(barcodeFactory).build());

            if(!barcodeDetector.isOperational())
            {   IntentFilter lowstorageFilter = new IntentFilter( Intent.ACTION_DEVICE_STORAGE_LOW);
                boolean hasLowStorage = getContext().registerReceiver(null, lowstorageFilter) != null;
                if(hasLowStorage)
                { Toast.makeText(getContext(),"Face detector dependencies cannot be downloaded due to low device storage",Toast.LENGTH_LONG).show(); } }
            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            CameraSource.Builder builder = new CameraSource.Builder(getContext(), barcodeDetector)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(metrics.widthPixels, metrics.heightPixels)
                    .setRequestedFps(24.0f);
            // make sure that auto focus is an available option
            builder = builder.setFocusMode(autoFocus?FOCUS_MODE_CONTINUOUS_PICTURE : null);
            mCameraSource = builder.setFlashMode(useFlash ? FLASH_MODE_TORCH : null).build();
        }
        catch(Exception ignored){ } }

    @Override
    public void onQrCodeDetected(final String barcodeValue)
    {      if(!isScanComplted)
           { isScanComplted = true;
             String qrCode = barcodeValue;
             if(qrCode!=null)
             { sendQrCodeToServer(qrCode); } } }

    private void sendQrCodeToServer(String qrCode)
    { qrCodePresenter.qrcodeverify( SharedPreference.getInstance().getAuthToken(), qrCode ); }

    @Override
    public void qrcoderesponse( QrCodeResponse qrCodeResponse )
    {   hideProgress();
        if ( qrCodeResponse.getTinyshopdetails()!=null )
        {   Dialog dialog=new Dialog(getContext(),android.R.style.Theme_Black_NoTitleBar);
            dialog.setContentView(R.layout.layout_qrshopdetail);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable( Color.WHITE));

            /**
             * Initialise views
             */
            ImageView iv_shopimage= dialog.findViewById(R.id.iv_shopimage);
            CircleImageView iv_profile=dialog.findViewById( R.id.iv_profile );
            TextView tv_shopname = dialog.findViewById(R.id.tv_shopname);
            TextView tv_vendorname=dialog.findViewById( R.id.tv_vendorname );
            TextView tv_phone= dialog.findViewById( R.id.tv_phone );
            TextView tv_address= dialog.findViewById( R.id.tv_address );
            TextView tv_shopemail = dialog.findViewById( R.id.tv_shopemail );
            TextView tv_shopphone= dialog.findViewById( R.id.tv_shopphone );
            TextView tv_verified=dialog.findViewById( R.id.tv_verified );
            AppCompatButton btn_ok=dialog.findViewById( R.id.btn_ok );

            SwitchCompat shopstatus=dialog.findViewById( R.id.shop_status );
            RecyclerView rv_facilities=dialog.findViewById( R.id.rv_facilities );
            RecyclerView rv_department=dialog.findViewById( R.id.rv_departments );

            tv_shopname.setText( qrCodeResponse.getTinyshopdetails().getTinyshopName() );
            tv_address.setText( qrCodeResponse.getTinyshopdetails().getShopAddress() );
            tv_shopemail.setText( qrCodeResponse.getTinyshopdetails().getOfficeEmail() );
            tv_shopphone.setText( qrCodeResponse.getTinyshopdetails().getPhone() );

            tv_phone.setText( qrCodeResponse.getTinyshopdetails().getPhone() );
            tv_vendorname.setText( qrCodeResponse.getTinyshopdetails().getFirstName()+" "+qrCodeResponse.getTinyshopdetails().getLastName() );

            if (!TextUtils.isEmpty(qrCodeResponse.getTinyshopdetails().getLivePhoto()))
            Picasso.get()
                    .load(qrCodeResponse.getTinyshopdetails().getLivePhoto() )
                    .fit()
                    .into(iv_shopimage);

            if (!TextUtils.isEmpty(qrCodeResponse.getTinyshopdetails().getProfileImg()))
            Picasso.get()
                    .load(qrCodeResponse.getTinyshopdetails().getProfileImg())
                    .fit()
                    .into(iv_profile);

            if ( qrCodeResponse.getTinyshopdetails().getVerifiedDoc().equals( 0 ) )
            { tv_verified.setVisibility( View.GONE ); }
            else if ( qrCodeResponse.getTinyshopdetails().getVerifiedDoc().equals( 1 ) )
            { tv_verified.setVisibility( View.VISIBLE ); }

            if( qrCodeResponse.getTinyshopdetails().getShopOpen().equals( 1 ) )
            { shopstatus.setChecked(true); }
            else if ( qrCodeResponse.getTinyshopdetails().getShopOpen().equals( 0 ) )
            { shopstatus.setChecked(false); }

            btn_ok.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            } );

            dialog.show(); }
          else
          {
              Toast.makeText( getContext(), "Shop details not found", Toast.LENGTH_SHORT ).show();
              ((DashBoardActivity)getActivity()).replaceFragment( new HomeScreenFragment(),HomeScreenFragment.class.getName() );
          }
    }

    @OnClick(R.id.iv_back)
    void clickback()
    {if ( getActivity()!=null )
    getActivity().onBackPressed();}

}
