package com.tag.app.tagnearemployee.boardingscreens.forgotpassword;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.boardingscreens.boardingactivity.BoardingActivity;
import com.tag.app.tagnearemployee.boardingscreens.forgototpverify.OtpForgotPasswordFragment;
import com.tag.app.tagnearemployee.pojomodels.ForgotPassword;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgotPasswordFragment extends BaseFragment implements ForgotPasswordContract.View
{   @Inject
    ForgotPasswordPresenter forgotPasswordPresenter;

    @BindView( R.id.et_phone )
    AppCompatEditText et_phone;

    @BindView( R.id.tv_errormeassage )
    AppCompatTextView tv_errormeassage;

    private String phone;

    @Override
    protected void init(Bundle savedInstanceState)
    { forgotPasswordPresenter.setView( this );
      tv_errormeassage.setVisibility( View.GONE );
      phone=SharedPreference.getInstance().getString( "mobilenumber" ); }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_forgot_password;
    }

    @Override
    protected Object getModule()
    { return new ForgotPasswordModule(); }

    @Override
    public void onFailure(Throwable throwable)
    {   hideProgress();
        getErrorMessage( throwable );
        tv_errormeassage.setVisibility( View.VISIBLE ); }

    @OnClick(R.id.iv_back)
    void backpress()
    { if ( getActivity()!=null )
      getActivity().onBackPressed(); }

      @OnClick(R.id.btn_reset)
      void clickreset()
      { if (!checkEmptyText( et_phone, getString(R.string.field_cant_be_empty) ))
        {   if ( phone.equals( et_phone.getText().toString().trim() ) )
           { showProgress();
             tv_errormeassage.setVisibility( View.GONE );
             forgotPasswordPresenter.forgotpassword( new ForgotPassword(et_phone.getText().toString().trim(),"91") ); }
           else
           { tv_errormeassage.setVisibility( View.VISIBLE ); } } }

    @Override
    public void forgotpassword(ForgotPassword forgotPassword)
    {   hideProgress();
        tv_errormeassage.setVisibility( View.GONE );
        showToast( forgotPassword.getMessage() );
        OtpForgotPasswordFragment resetPasswordFragment=new OtpForgotPasswordFragment();
        Bundle bundle=new Bundle(  );
        bundle.putString( "mobilenumber", et_phone.getText().toString().trim() );
        bundle.putInt( "FORGOTKEY",1 );
        resetPasswordFragment.setArguments( bundle );
        ((BoardingActivity)getActivity()).replaceFragment( resetPasswordFragment,OtpForgotPasswordFragment.class.getName() ); }
}
