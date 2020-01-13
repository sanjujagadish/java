package com.tag.app.tagnearemployee.boardingscreens.forgototpverify;

import android.app.Activity;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.PermissionHelper;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.boardingscreens.boardingactivity.BoardingActivity;
import com.tag.app.tagnearemployee.boardingscreens.resetpassword.ResetPasswordFragment;
import com.tag.app.tagnearemployee.dashboard.DashBoardActivity;
import com.tag.app.tagnearemployee.homescreen.HomeScreenFragment;
import com.tag.app.tagnearemployee.interfaces.SMSListener;
import com.tag.app.tagnearemployee.interfaces.SMSReceiver;
import com.tag.app.tagnearemployee.pojomodels.BusinessPro;
import com.tag.app.tagnearemployee.pojomodels.BusinessProResponse;
import com.tag.app.tagnearemployee.pojomodels.ForgotPassword;
import com.tag.app.tagnearemployee.pojomodels.PendingDatum;
import com.tag.app.tagnearemployee.pojomodels.UpdateShopResponse;
import com.tag.app.tagnearemployee.pojomodels.VerifyVendorResponse;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static android.graphics.Typeface.BOLD;
import static com.tag.app.tagnearemployee.appUtils.PermissionHelper.PERMISSION_REQUEST_CODE;

public class OtpForgotPasswordFragment extends BaseFragment implements OtpForgotPwrdContract.View, SMSListener {
    @Inject
    OtpForgotPwrdPresenter forgotPasswordPresenter;

    @BindView(R.id.etOtp1)
    AppCompatEditText etOtp1;

    @BindView(R.id.etOtp2)
    AppCompatEditText etOtp2;

    @BindView(R.id.etOtp3)
    AppCompatEditText etOtp3;

    @BindView(R.id.etOtp4)
    AppCompatEditText etOtp4;

    @BindView(R.id.tv_resend)
    AppCompatTextView tv_resend;

    @BindView(R.id.otptext)
    TextView otptext;

    private SMSReceiver smsReceiver;
    private PermissionHelper permissionHelper;
    private String phone;
    private ForgotPassword forgotPassword;
    private UpdateShopResponse updateShopResponse;
    private PendingDatum updateShopInput;
    private BusinessProResponse businessProResponse;
    private BusinessPro businessPro;

    @Override
    protected void init(Bundle savedInstanceState)
    {   forgotPasswordPresenter.setView( this );
        permissionHelper = new PermissionHelper( (Activity) getContext() );
        if (isReadSMSPermissionGranted())
        { startSMSReceiveService(); }
        addTextWatcher(etOtp1);
        addTextWatcher(etOtp2);
        addTextWatcher(etOtp3);
        addTextWatcher(etOtp4);

        if ( getArguments().containsKey( "FORGOTKEY" ) )
        { phone=getArguments().getString("mobilenumber" );
          forgotPassword=new ForgotPassword(  );
          forgotPassword.setPhone( phone );
          forgotPassword.setCountryCode( "91" ); }
        else if ( getArguments().containsKey( "DETAILSPAGEKEY" ) )
        { updateShopResponse= (UpdateShopResponse) getArguments().getSerializable("details" );
          phone=updateShopResponse.getRequest().getPhone();
          updateShopInput=new PendingDatum();
          updateShopInput=updateShopResponse.getRequest(); }
        else if ( getArguments().containsKey( "BUSINESSPRO" ) )
        { businessProResponse = (BusinessProResponse) getArguments().getSerializable("BUSINESSRESPONSE" );
          phone=businessProResponse.getRequest().getPhone();
          businessPro=new BusinessPro();
          businessPro=businessProResponse.getRequest(); }

        String otptexts = getString(R.string.enterotp);
        String otpregText = getString(R.string.enterotp)+" "+phone+", ";

        Spannable spannableotp = new SpannableString(otpregText);
        spannableotp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), otptexts.length(), otpregText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableotp.setSpan(new StyleSpan(BOLD), otptexts.length(), otpregText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        otptext.setText(spannableotp,TextView.BufferType.SPANNABLE);

}

    private void addTextWatcher(AppCompatEditText editText)
    {  editText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        { }

        @Override
        public void afterTextChanged(Editable s)
        {  if (s.toString().length() == 1)
        { if (editText.getId() == etOtp1.getId()) {
            etOtp2.requestFocus(); }
        else if (editText.getId() == R.id.etOtp2)
        { etOtp3.requestFocus(); }
        else if ( editText.getId()==R.id.etOtp3 )
        { etOtp4.requestFocus(); } }
        else
          { if (editText.getId() == etOtp4.getId()) {
                etOtp3.requestFocus();
            } else if (editText.getId() == R.id.etOtp3) {
                etOtp2.requestFocus();
            } else if ( editText.getId()==R.id.etOtp2 ){
                etOtp1.requestFocus();
            } } } }); }

    private void startSMSReceiveService()
    {   smsReceiver = new SMSReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        Objects.requireNonNull(getActivity()).registerReceiver(smsReceiver, intentFilter);
        SMSReceiver.bindListener(this); }

    public boolean isReadSMSPermissionGranted()
    {     // Log.d(TAG, "inside isStoragePermissionGranted");
        if (permissionHelper.isAllReadSMSPermissionGranted())
        { // mLocationPermissionGranted = true;
            return true; }
        else { permissionHelper.requestAllPermissionForReadSMS();
            return false;
        } }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSMSReceiveService(); }
            else {
//                customToast(getString(R.string.allow_permission),false);
            } }}

    @Override
    public void onResume()
    { super.onResume(); }

    @Override
    public void onDestroy()
    {   super.onDestroy();
        if (smsReceiver != null)
        Objects.requireNonNull(getActivity()).unregisterReceiver(smsReceiver); }

    @Override
    public void messageReceived(String messageText)
    {   String[] split = messageText.split("" );
        etOtp1.setText(split[1]);
        etOtp2.setText(split[2]);
        etOtp3.setText(split[3]);
        etOtp4.setText(split[4]);
        forgotPassword.setOtp(messageText); }

    @OnClick(R.id.btnVerify)
    protected void onVerifyClick()
    {   if ( getArguments().containsKey( "FORGOTKEY" ) )
        { if (validateOTPFields() && forgotPassword != null)
          { showProgress();
            forgotPassword.setOtp(String.format("%s%s%s%s", etOtp1.getText().toString(), etOtp2.getText().toString(), etOtp3.getText().toString(), etOtp4.getText().toString()));
            forgotPasswordPresenter.verifyvalid(forgotPassword); }
        else { customToast(getString(R.string.enter_correct_otp),false); } }

        else if(getArguments().containsKey( "DETAILSPAGEKEY" ))
        { showProgress();
          updateShopInput.setOtp(String.format("%s%s%s%s", etOtp1.getText().toString(), etOtp2.getText().toString(), etOtp3.getText().toString(), etOtp4.getText().toString()));
          forgotPasswordPresenter.verifyvendor( SharedPreference.getInstance().getAuthToken(),updateShopInput ); }

        else if ( getArguments().containsKey( "BUSINESSPRO" ) )
       {  showProgress();
          businessPro.setShop_name(businessPro.getTinyshopName());
          businessPro.setOtp(String.format("%s%s%s%s", etOtp1.getText().toString(), etOtp2.getText().toString(), etOtp3.getText().toString(), etOtp4.getText().toString())  );
          forgotPasswordPresenter.businessregverified( SharedPreference.getInstance().getAuthToken(),businessPro ); }
    }

    private boolean validateOTPFields()
    {   if ( TextUtils.isEmpty(Objects.requireNonNull(etOtp1.getText()).toString()))
        return false;
        else if (TextUtils.isEmpty(Objects.requireNonNull(etOtp2.getText()).toString()))
        return false;
        else if (TextUtils.isEmpty(Objects.requireNonNull(etOtp3.getText()).toString()))
        return false;
        else return !TextUtils.isEmpty(Objects.requireNonNull(etOtp4.getText()).toString()); }

    @Override
    protected int getContentResource()
    { return R.layout.fragment_otp_forgot_password; }

    @Override
    protected Object getModule()
    { return new OtpForgotPwrdModule(); }

    @Override
    public void verifyotp( ForgotPassword otpVerify )
    { hideProgress();
      ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
      otpVerify.setPhone( phone );
      Bundle bundle=new Bundle(  );
      bundle.putSerializable("otpverified",otpVerify);
      resetPasswordFragment.setArguments( bundle );
      ((BoardingActivity) getActivity()).replaceFragment( resetPasswordFragment, ResetPasswordFragment.class.getName() ); }

    @Override
    public void forgotpassword(ForgotPassword forgotPassword)
    { hideProgress(); }

    @Override
    public void verifyVendor(VerifyVendorResponse verifyVendorResponse)
    {   hideProgress();
        showToast( verifyVendorResponse.getMessage() );
        ((DashBoardActivity)getActivity()).replaceFragment( new HomeScreenFragment(),HomeScreenFragment.class.getName() ); }

    @Override
    public void updateshop(UpdateShopResponse updateShopResponse) {
       hideProgress();
    }

    @Override
    public void verifiedbusinessregistered(BusinessPro businessPro)
    {   hideProgress();
        Toast.makeText( getContext(),businessPro.getMessage(),Toast.LENGTH_SHORT ).show();
        ((DashBoardActivity)getActivity()).replaceFragment( new HomeScreenFragment(),HomeScreenFragment.class.getName() ); }

    @Override
    public void regbusinessotp(BusinessProResponse businessProResponse)
    { hideProgress(); }

    @Override
    public void onFailure(Throwable throwable)
    { hideProgress();
      getErrorMessage( throwable ); }

    @OnClick(R.id.tv_resend)
    public void clickresend()
    {   if ( getArguments().containsKey( "FORGOTKEY" ) )
        {  showProgress();
           forgotPasswordPresenter.forgotpassword(forgotPassword); }
        else if ( getArguments().containsKey( "DETAILSPAGEKEY" ) )
        {  showProgress();
           forgotPasswordPresenter.otptrigger( SharedPreference.getInstance().getAuthToken(),updateShopInput ); }
        else if ( getArguments().containsKey( "BUSINESSPRO" ) )
        {  showProgress();
           forgotPasswordPresenter.otpbusinessreg( SharedPreference.getInstance().getAuthToken(),businessPro ); }
    }
}
