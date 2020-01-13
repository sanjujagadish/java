package com.tag.app.tagnearemployee.homescreen.business.businesslist;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BusinessListModel implements BusinessListContract.Model
{
    private final RestClient restClient;
    private Disposable subscription;

    public BusinessListModel(RestClient restClient)
    { this.restClient=restClient; }

    @Override
    public void businesslist(String token,int page, ModelCallback modelCallback)
    { subscription = restClient.get().getbusinesslist(token,page)
            .subscribeOn( Schedulers.computation())
            .observeOn( AndroidSchedulers.mainThread())
            .subscribe(modelCallback::onSuccess, modelCallback::onFailure);}

    @Override
    public void destroy()
    { if ( subscription!=null && subscription.isDisposed() )
        subscription.dispose(); }
}
