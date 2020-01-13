package com.tag.app.tagnearemployee.dashboard;


import com.tag.app.tagnearemployee.retrofit.RestClient;

import io.reactivex.disposables.Disposable;

public class DashBoardModel implements DashBoardContract.Model{
    private final RestClient restClient;
    private Disposable subscription;

    public DashBoardModel(RestClient restClient)
    { this.restClient=restClient; }

    @Override
    public void destroy() {
     if ( subscription!=null && subscription.isDisposed() )
         subscription.dispose();
    }
}
