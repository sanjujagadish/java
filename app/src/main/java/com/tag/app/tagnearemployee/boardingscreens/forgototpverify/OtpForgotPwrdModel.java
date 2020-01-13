package com.tag.app.tagnearemployee.boardingscreens.forgototpverify;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.BusinessPro;
import com.tag.app.tagnearemployee.pojomodels.ForgotPassword;
import com.tag.app.tagnearemployee.pojomodels.PendingDatum;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OtpForgotPwrdModel implements OtpForgotPwrdContract.Model
{   private final RestClient restClient;
    private Disposable subscription;

    public OtpForgotPwrdModel(RestClient restClient)
    { this.restClient=restClient; }

    @Override
    public void verifysuccess(ForgotPassword forgotPassword, ModelCallback modelCallback)
    { subscription = restClient.get().verifyotp( forgotPassword )
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }

    @Override
    public void forgotvalid(ForgotPassword forgotPassword, ModelCallback modelCallback)
    { subscription = restClient.get().forgotpassword( forgotPassword )
            .subscribeOn( Schedulers.computation())
            .observeOn( AndroidSchedulers.mainThread())
            .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }

    @Override
    public void verifyvalid(String token, PendingDatum verifyVendor, ModelCallback modelCallback)
    { subscription = restClient.get().verifyvendor( token,verifyVendor )
            .subscribeOn( Schedulers.computation())
            .observeOn( AndroidSchedulers.mainThread())
            .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }

    @Override
    public void triggerotp(String token, PendingDatum updateShopInput, ModelCallback modelCallback)
        { subscription = restClient.get().updatedetails( token,updateShopInput )
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }

    @Override
    public void verifybusinessreg(String token, BusinessPro businessPro, ModelCallback modelCallback) {
        subscription = restClient.get().businessverify( token,businessPro )
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess, modelCallback::onFailure);
    }

    @Override
    public void regotptrigger(String token, BusinessPro businessPro, ModelCallback modelCallback) {
        subscription = restClient.get().businessregister( token,businessPro )
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess, modelCallback::onFailure);
    }

    @Override
    public void destroy()
    { if (subscription!=null && !subscription.isDisposed())
      subscription.dispose(); }
}
