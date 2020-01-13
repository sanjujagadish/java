package com.tag.app.tagnearemployee.boardingscreens.boardingactivity;

import com.tag.app.tagnearemployee.retrofit.RestClient;

import io.reactivex.disposables.Disposable;

public class BoardingModel implements BoardingContract.Model {
    private final RestClient restClient;
    private Disposable subscription;

    public BoardingModel(RestClient restClient) {
        this.restClient=restClient;
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isDisposed())
            subscription.dispose(); }

}
