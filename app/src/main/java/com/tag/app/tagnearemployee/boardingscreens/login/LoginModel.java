package com.tag.app.tagnearemployee.boardingscreens.login;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.Employee;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginModel implements LoginContract.Model
{
    private final RestClient restClient;
    private Disposable subscription;

    public LoginModel(RestClient restClient) {
        this.restClient=restClient;
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isDisposed())
            subscription.dispose(); }

    @Override
    public void logincall(Employee customer, ModelCallback modelCallback) {
        subscription = restClient.get().validlogin(customer)
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }
}
