package com.tag.app.tagnearemployee.qrcode;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = QrcodeFragment.class,addsTo = ApplicationModule.class)
public class QrCodeModule
{   @Provides
    QrCodePresenter qrCodePresenter()
    { return new QrCodePresenter(new QrCodeModel(new RestClient( Constants.BASE_URL ) )); }
}
