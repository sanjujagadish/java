package com.tag.app.tagnearemployee.boardingscreens.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.textfield.TextInputEditText;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.boardingscreens.boardingactivity.BoardingActivity;
import com.tag.app.tagnearemployee.boardingscreens.forgotpassword.ForgotPasswordFragment;
import com.tag.app.tagnearemployee.dashboard.DashBoardActivity;
import com.tag.app.tagnearemployee.pojomodels.Employee;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment implements LoginContract.View
{   @Inject
    LoginPresenter loginPresenter;

    @BindView( R.id.et_phone )
    TextInputEditText et_phone;

    @BindView( R.id.et_password )
    TextInputEditText et_password;

    @BindView( R.id.tv_errormeassage )
    AppCompatTextView tv_errormeassage;

    @Override
    protected void init(Bundle savedInstanceState)
    {   loginPresenter.setView( this );
        tv_errormeassage.setVisibility( View.GONE ); }

    @OnClick(R.id.tv_forgotpassword)
    void clickforgot()
    { ((BoardingActivity)getActivity()).replaceFragment(new ForgotPasswordFragment(),LoginFragment.class.getName() ); }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_login;
    }

    @Override
    protected Object getModule() {
        return new LoginModule();
    }

    @Override
    public void onFailure(Throwable throwable)
    {   hideProgress();
        getErrorMessage(throwable);
        tv_errormeassage.setVisibility( View.VISIBLE ); }

    @OnClick(R.id.btn_signin)
    void signinclick()
    {   if ( !checkEmptyText( et_phone,getString( R.string.field_cant_be_empty ) ) )
        { if (!checkEmptyText( et_password,getString(R.string.field_cant_be_empty )))
          { showProgress();
            loginPresenter.loginvalidate( new Employee(et_phone.getText().toString(),et_password.getText().toString(),"91") );
          } } }

    @Override
    public void loginsuccess(Employee customer)
    {   hideProgress();
        tv_errormeassage.setVisibility( View.GONE );
        SharedPreference.getInstance().setUser( "CUSTOMERLOGIN",customer );
        SharedPreference.getInstance().removeObject("mobilenumber");
        SharedPreference.getInstance().putString("mobilenumber",et_phone.getText().toString().trim());
        Intent intent = new Intent( getActivity(),DashBoardActivity.class );
        startActivity(intent);
        getActivity().finish();
    }
}
