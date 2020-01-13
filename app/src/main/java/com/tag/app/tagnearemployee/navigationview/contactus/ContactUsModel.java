package com.tag.app.tagnearemployee.navigationview.contactus;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactUsModel implements ContactUsContract.Model{
    private final RestClient restClient;
    private Disposable subscription;

    public ContactUsModel(RestClient restClient) {
        this.restClient=restClient;
    }

    @Override
    public void getContact(String token, ModelCallback modelCallback) {
        subscription = restClient.get().getContactUs(token)
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess, modelCallback::onFailure);
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isDisposed())
            subscription.dispose();
    }
}
