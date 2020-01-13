package com.tag.app.tagnearemployee.boardingscreens.forgotpassword;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.ForgotPassword;

public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter {

    private final ForgotPasswordModel forgotPasswordModel;
    private ForgotPasswordContract.View view;

    public ForgotPasswordPresenter(ForgotPasswordModel forgotPasswordModel)
    { this.forgotPasswordModel=forgotPasswordModel; }

    @Override
    public void setView(Object view)
    { this.view = (ForgotPasswordContract.View) view; }

    @Override
    public void clearView()
    { forgotPasswordModel.destroy(); }

    @Override
    public void forgotpassword(ForgotPassword forgotPassword) {
         forgotPasswordModel.forgotvalid( forgotPassword, new ModelCallback() {
             @Override
             public void onSuccess(Object object) {
                 view.forgotpassword( (ForgotPassword) object ); }

             @Override
             public void onFailure(Throwable throwable) {
                 view.onFailure( throwable );
             }
         } );
    }
}
