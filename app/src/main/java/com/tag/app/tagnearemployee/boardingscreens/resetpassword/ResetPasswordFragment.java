package com.tag.app.tagnearemployee.boardingscreens.resetpassword;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;

import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.boardingscreens.boardingactivity.BoardingActivity;
import com.tag.app.tagnearemployee.boardingscreens.login.LoginFragment;
import com.tag.app.tagnearemployee.pojomodels.ForgotPassword;
import com.tag.app.tagnearemployee.pojomodels.Employee;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ResetPasswordFragment extends BaseFragment implements ResetPasswordContract.View
{   @Inject
    ResetPasswordPresenter resetPasswordPresenter;

    @BindView( R.id.et_phone )
    AppCompatEditText et_phone;

    @BindView( R.id.et_otp )
    AppCompatEditText et_otp;

    @BindView( R.id.et_password )
    AppCompatEditText et_password;

    @BindView( R.id.et_confirmpassword )
    AppCompatEditText et_confirmpassword;

    private ForgotPassword forgotPassword;

    @Override
    protected void init(Bundle savedInstanceState)
    {   resetPasswordPresenter.setView( this );
        et_phone.setFocusable( false );
        et_otp.setFocusable( false );

        forgotPassword= (ForgotPassword) getArguments().get( "otpverified" );
        et_phone.setText( forgotPassword.getPhone() );
        et_otp.setText( forgotPassword.getOtp() ); }

    @Override
    protected int getContentResource()
    { return R.layout.fragment_reset_password; }

    @Override
    protected Object getModule()
    { return new ResetPasswordModule(); }

    @OnClick(R.id.btnReset)
    void resetpassword()
    {   forgotPassword.setCountryCode("91");
        forgotPassword.setPassword( et_password.getText().toString().trim() );

        if ( et_password.getText().toString().trim().equals( et_confirmpassword.getText().toString().trim() ) )
        {  forgotPassword.setConfirmPassword(et_confirmpassword.getText().toString().trim());
           resetPasswordPresenter.resetpassword( forgotPassword ); }
        else
        { Toast.makeText( getContext(),"Password doesn't match",Toast.LENGTH_SHORT ).show();
          et_confirmpassword.setText( null ); } }

    @Override
    public void resetpassword( ForgotPassword resetPassword )
    {   hideProgress();
        new Employee( et_password.getText().toString().trim() );
        showToast( resetPassword.getMessage() );
        ((BoardingActivity)getActivity()).replaceFragment( new LoginFragment(),LoginFragment.class.getName() ); }

    @Override
    public void onFailure(Throwable throwable)
    { hideProgress();
      showToast( throwable.getMessage() ); }

    @OnClick(R.id.iv_Back)
    public void clickback()
    {  if ( getActivity()!=null )
       getActivity().onBackPressed(); }
}
