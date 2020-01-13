package com.tag.app.tagnearemployee.navigationview.aboutus;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AboutUsModel implements AboutUsContract.Model {
    private final RestClient restClient;
    private Disposable subscription;

    public AboutUsModel(RestClient restClient) {
        this.restClient=restClient; }

    @Override
    public void aboutus(String token, ModelCallback modelCallback) {
        subscription = restClient.get().getaboutus(token)
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isDisposed())
            subscription.dispose(); }
}
