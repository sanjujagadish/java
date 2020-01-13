package com.tag.app.tagnearemployee.navigationview.privacypolicy;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PrivacyPolicyModel implements PrivacyPolicyContract.Model{
    private final RestClient restClient;
    private Disposable subscription;

    public PrivacyPolicyModel(RestClient restClient)
    { this.restClient=restClient; }

    @Override
    public void destroy()
    { if (subscription != null && !subscription.isDisposed())
            subscription.dispose(); }

    @Override
    public void policiesvalid(String token, ModelCallback modelCallback) {
        subscription = restClient.get().getPolicy(token)
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }
}
