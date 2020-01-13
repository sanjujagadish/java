package com.tag.app.tagnearemployee.boardingscreens.forgotpassword;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = ForgotPasswordFragment.class,addsTo = ApplicationModule.class)
public class ForgotPasswordModule
{   @Provides
    ForgotPasswordPresenter forgotPasswordPresenter()
    { return new ForgotPasswordPresenter(new ForgotPasswordModel(new RestClient( Constants.BASE_URL ) )); } }
