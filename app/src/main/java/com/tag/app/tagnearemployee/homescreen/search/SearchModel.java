package com.tag.app.tagnearemployee.homescreen.search;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchModel implements SearchContract.Model {
    private final RestClient restClient;
    private Disposable subscription;

    public SearchModel(RestClient restClient)
    { this.restClient=restClient; }

    @Override
    public void searchlist(String token, String searchkey, ModelCallback modelCallback)
    { subscription = restClient.get().getsearchResponse(token,searchkey)
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }

    @Override
    public void destroy()
    { if ( subscription!=null && subscription.isDisposed() )
            subscription.dispose(); }
}
