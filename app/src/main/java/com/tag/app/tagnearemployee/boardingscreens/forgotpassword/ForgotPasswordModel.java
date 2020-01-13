package com.tag.app.tagnearemployee.boardingscreens.forgotpassword;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.ForgotPassword;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasswordModel implements ForgotPasswordContract.Model {

    private final RestClient restClient;
    private Disposable subscription;

    public ForgotPasswordModel(RestClient restClient)
    { this.restClient=restClient; }

    @Override
    public void destroy()
    { if (subscription!=null && !subscription.isDisposed())
      subscription.dispose(); }

    @Override
    public void forgotvalid(ForgotPassword forgotPassword, ModelCallback modelCallback)
    { subscription = restClient.get().forgotpassword( forgotPassword )
            .subscribeOn( Schedulers.computation())
            .observeOn( AndroidSchedulers.mainThread())
            .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }
}
