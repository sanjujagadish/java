package com.tag.app.tagnearemployee.boardingscreens.resetpassword;


import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.ForgotPassword;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ResetPasswordModel implements ResetPasswordContract.Model {
    private final RestClient restClient;
    private Disposable subscription;

    public ResetPasswordModel(RestClient restClient)
    { this.restClient=restClient; }

    @Override
    public void resetstatus(ForgotPassword resetPassword, ModelCallback modelCallback)
    { subscription = restClient.get().resetpassword(resetPassword)
            .subscribeOn( Schedulers.computation())
            .observeOn( AndroidSchedulers.mainThread())
            .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }

    @Override
    public void destroy()
    { if (subscription != null && !subscription.isDisposed())
            subscription.dispose(); }
}
