package com.tag.app.tagnearemployee.qrcode;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QrCodeModel implements QrCodeContract.Model
{   private final RestClient restClient;
    private Disposable subscription;

    public QrCodeModel(RestClient restClient)
    { this.restClient=restClient; }

    @Override
    public void destroy()
    { if(subscription!=null && !subscription.isDisposed())
      subscription.dispose(); }

    @Override
    public void qrcodevalid(String authorization, String qrCode, ModelCallback modelCallback)
    {   subscription=restClient.get().getqrcode(authorization,qrCode)
            .subscribeOn(Schedulers.computation())
            .observeOn( AndroidSchedulers.mainThread())
            .subscribe(modelCallback::onSuccess,modelCallback::onFailure); }
}
