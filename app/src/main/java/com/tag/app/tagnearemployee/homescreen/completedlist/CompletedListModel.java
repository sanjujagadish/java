package com.tag.app.tagnearemployee.homescreen.completedlist;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CompletedListModel implements CompletedListContract.Model
{
    private final RestClient restClient;
    private Disposable subscription;

    public CompletedListModel(RestClient restClient)
    { this.restClient=restClient; }

    @Override
    public void completedlist(String token,int page,ModelCallback modelCallback)
    { subscription = restClient.get().getCompletedlist(token,page)
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }

    @Override
    public void destroy()
    { if ( subscription!=null && subscription.isDisposed() )
            subscription.dispose(); }
}
