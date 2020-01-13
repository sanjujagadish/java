package com.tag.app.tagnearemployee.navigationview.myprofile;

import com.tag.app.tagnearemployee.retrofit.RestClient;

import io.reactivex.disposables.Disposable;

public class MyProfileModel implements MyProfileContract.Model {
    private final RestClient restClient;
    private Disposable subscription;

    public MyProfileModel(RestClient restClient) {
        this.restClient=restClient;
    }

    @Override
    public void destroy()
    { if ( subscription!=null && subscription.isDisposed() )
            subscription.dispose(); }

}
