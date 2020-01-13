package com.tag.app.tagnearemployee.homescreen;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeScreenModel implements HomeScreenContract.Model {
    private final RestClient restClient;
    private Disposable subscription;

    public HomeScreenModel(RestClient restClient)
    { this.restClient=restClient; }

    public void destroy()
    { if ( subscription!=null && subscription.isDisposed() )
      subscription.dispose(); }

    @Override
    public void fetchlist(String token,int page, ModelCallback modelCallback) {
        subscription = restClient.get().getPendinglist(token,page)
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }

    @Override
    public void shopstatus(String token, ModelCallback modelCallback) {
        subscription = restClient.get().setDutyStatus(token)
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }

    @Override
    public void profiledetails(String token, ModelCallback modelCallback) {
        subscription = restClient.get().getprofiledetails(token)
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }

    @Override
    public void fetchcategories(String token, ModelCallback modelCallback) {
            subscription = restClient.get().getCategories(token)
                    .subscribeOn( Schedulers.computation())
                    .observeOn( AndroidSchedulers.mainThread())
                    .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }



}
