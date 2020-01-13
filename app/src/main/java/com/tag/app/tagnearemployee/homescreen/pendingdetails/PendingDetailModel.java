package com.tag.app.tagnearemployee.homescreen.pendingdetails;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.PendingDatum;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PendingDetailModel implements PendingDetailContract.Model {
    private final RestClient restClient;
    private Disposable subscription;

    public PendingDetailModel(RestClient restClient)
    { this.restClient=restClient; }

    @Override
    public void setdetails(String token,PendingDatum updateShopInput,ModelCallback modelCallback)
    { subscription = restClient.get().updatedetails(token,updateShopInput)
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }

    public void uploadsuccess(Map<String, RequestBody> payload,Map<String, RequestBody> type, MultipartBody.Part body, ModelCallback modelCallback) {
        subscription = restClient.get().uploadProfilePic(payload,type,body)
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess,modelCallback::onFailure); }
    @Override
    public void destroy()
    {   if ( subscription!=null && subscription.isDisposed() )
        subscription.dispose(); }
}
