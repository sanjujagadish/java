package com.tag.app.tagnearemployee.boardingscreens.login;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.Employee;

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginModel loginModel;
    private LoginContract.View view;

    public LoginPresenter(LoginModel loginModel) {
        this.loginModel=loginModel;
    }


    @Override
    public void setView(Object view) {
     this.view= (LoginContract.View) view;
    }

    @Override
    public void clearView() {
     loginModel.destroy();
    }

    @Override
    public void loginvalidate(Employee customer)
    {   loginModel.logincall( customer, new ModelCallback() {
           @Override
           public void onSuccess(Object object) {
               view.loginsuccess( (Employee) object );
           }

           @Override
           public void onFailure(Throwable throwable) {
                view.onFailure( throwable );
           }
       } );
    }
}
