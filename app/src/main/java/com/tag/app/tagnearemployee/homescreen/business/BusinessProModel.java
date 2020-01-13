package com.tag.app.tagnearemployee.homescreen.business;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.BusinessPro;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BusinessProModel implements BusinessProContract.Model
{
    private final RestClient restClient;
    private Disposable subscription;

    public BusinessProModel(RestClient restClient)
    { this.restClient=restClient; }

    @Override
    public void destroy()
    {   if ( subscription!=null && subscription.isDisposed() )
        subscription.dispose(); }

    @Override
    public void businessprosubmit(BusinessPro businessPro, String token, ModelCallback modelCallback)
    {   subscription = restClient.get().businessregister(token,businessPro)
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess, modelCallback::onFailure); }


    public void uploadsuccess(Map<String, RequestBody> payload, Map<String, RequestBody> type,MultipartBody.Part body, ModelCallback modelCallback) {
        subscription = restClient.get().uploadProfilePic(payload,type,body)
                .subscribeOn( Schedulers.computation())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(modelCallback::onSuccess,modelCallback::onFailure); } }
