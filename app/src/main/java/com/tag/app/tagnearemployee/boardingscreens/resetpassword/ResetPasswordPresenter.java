package com.tag.app.tagnearemployee.boardingscreens.resetpassword;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.ForgotPassword;

public class ResetPasswordPresenter implements ResetPasswordContract.Presenter {
    private final ResetPasswordModel resetPasswordModel;
    private ResetPasswordContract.View view;

    public ResetPasswordPresenter(ResetPasswordModel resetPasswordModel) {
        this.resetPasswordModel=resetPasswordModel;
    }

    @Override
    public void resetpassword(ForgotPassword resetPassword) {
     resetPasswordModel.resetstatus( resetPassword, new ModelCallback() {
         @Override
         public void onSuccess(Object object)
         { view.resetpassword( (ForgotPassword) object );}

         @Override
         public void onFailure(Throwable throwable)
         { view.onFailure( throwable );}
     } );
    }

    @Override
    public void setView(Object view) {
       this.view= (ResetPasswordContract.View) view; }

    @Override
    public void clearView()
    { resetPasswordModel.destroy(); }
}
