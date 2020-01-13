package com.tag.app.tagnearemployee.boardingscreens.forgototpverify;

import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;
import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.BusinessPro;
import com.tag.app.tagnearemployee.pojomodels.BusinessProResponse;
import com.tag.app.tagnearemployee.pojomodels.ForgotPassword;
import com.tag.app.tagnearemployee.pojomodels.PendingDatum;
import com.tag.app.tagnearemployee.pojomodels.UpdateShopResponse;
import com.tag.app.tagnearemployee.pojomodels.VerifyVendorResponse;

public class OtpForgotPwrdContract
{
    public interface View extends BaseView
    {
      void verifyotp(ForgotPassword otpVerify);

      void forgotpassword(ForgotPassword forgotPassword);

      void verifyVendor(VerifyVendorResponse verifyVendorResponse);

      void updateshop(UpdateShopResponse updateShopResponse);

      void verifiedbusinessregistered(BusinessPro businessPro);

      void regbusinessotp(BusinessProResponse businessProResponse);
    }

    public interface Model extends BaseModel
    {
      void verifysuccess(ForgotPassword forgotPassword, ModelCallback modelCallback);

      void forgotvalid(ForgotPassword ForgotPassword, ModelCallback modelCallback);

      void verifyvalid(String token, PendingDatum verifyVendor, ModelCallback modelCallback);

      void triggerotp(String token, PendingDatum updateShopInput,ModelCallback modelCallback);

      void verifybusinessreg(String token,BusinessPro businessPro,ModelCallback modelCallback);

      void regotptrigger(String token,BusinessPro businessPro,ModelCallback modelCallback);
    }

    public interface Presenter extends BasePresenter
    {
      void verifyvalid(ForgotPassword forgotPassword);

      void forgotpassword(ForgotPassword ForgotPassword);

      void verifyvendor(String token,PendingDatum verifyVendor);

      void otptrigger(String token,PendingDatum updateShopInput);

      void  businessregverified(String token,BusinessPro businessPro);

      void otpbusinessreg(String token,BusinessPro businessPro);
    }

}
