package com.tag.app.tagnearemployee.boardingscreens.forgototpverify;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.BusinessPro;
import com.tag.app.tagnearemployee.pojomodels.BusinessProResponse;
import com.tag.app.tagnearemployee.pojomodels.ForgotPassword;
import com.tag.app.tagnearemployee.pojomodels.PendingDatum;
import com.tag.app.tagnearemployee.pojomodels.UpdateShopResponse;
import com.tag.app.tagnearemployee.pojomodels.VerifyVendorResponse;

public class OtpForgotPwrdPresenter implements OtpForgotPwrdContract.Presenter
{   private final OtpForgotPwrdModel otpForgotPwrdModel;
    private OtpForgotPwrdContract.View view;

    public OtpForgotPwrdPresenter(OtpForgotPwrdModel otpForgotPwrdModel)
    { this.otpForgotPwrdModel=otpForgotPwrdModel; }

    @Override
    public void verifyvalid(ForgotPassword forgotPassword)
    { otpForgotPwrdModel.verifysuccess( forgotPassword, new ModelCallback()
       { @Override
         public void onSuccess(Object object)
         { view.verifyotp( (ForgotPassword) object ); }

         @Override
         public void onFailure(Throwable throwable)
         { view.onFailure( throwable ); }
    } ); }

    @Override
    public void forgotpassword(ForgotPassword forgotPassword)
    { otpForgotPwrdModel.forgotvalid( forgotPassword, new ModelCallback()
      {     @Override
            public void onSuccess(Object object)
            { view.forgotpassword( (ForgotPassword) object ); }

            @Override
            public void onFailure(Throwable throwable)
            { view.onFailure( throwable ); }
        } ); }

    @Override
    public void verifyvendor(String token, PendingDatum verifyVendor) {
        otpForgotPwrdModel.verifyvalid( token, verifyVendor, new ModelCallback() {
            @Override
            public void onSuccess(Object object) {
                view.verifyVendor( (VerifyVendorResponse) object );
            }

            @Override
            public void onFailure(Throwable throwable) {
                view.onFailure( throwable );
            }
        } );
    }

    @Override
    public void otptrigger(String token, PendingDatum updateShopInput) {
       otpForgotPwrdModel.triggerotp( token, updateShopInput, new ModelCallback() {
           @Override
           public void onSuccess(Object object) {
               view.updateshop( (UpdateShopResponse) object );
           }

           @Override
           public void onFailure(Throwable throwable) {
               view.onFailure( throwable );
           }
       } );
    }

    @Override
    public void businessregverified(String token, BusinessPro businessPro) {
        otpForgotPwrdModel.verifybusinessreg( token, businessPro, new ModelCallback() {
            @Override
            public void onSuccess(Object object) {
                view.verifiedbusinessregistered( (BusinessPro) object );
            }

            @Override
            public void onFailure(Throwable throwable) {
                view.onFailure( throwable );
            }
        } );
    }

    @Override
    public void otpbusinessreg(String token, BusinessPro businessPro) {
        otpForgotPwrdModel.regotptrigger( token, businessPro, new ModelCallback() {
            @Override
            public void onSuccess(Object object) {
                view.regbusinessotp( (BusinessProResponse) object );
            }

            @Override
            public void onFailure(Throwable throwable) {
                view.onFailure( throwable );
            }
        } );
    }


    @Override
    public void setView(Object view)
    { this.view= (OtpForgotPwrdContract.View) view; }

    @Override
    public void clearView()
    { otpForgotPwrdModel.destroy(); }
}
