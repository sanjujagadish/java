package com.tag.app.tagnearemployee.boardingscreens.resetpassword;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = ResetPasswordFragment.class,addsTo = ApplicationModule.class)
public class ResetPasswordModule
{   @Provides
    ResetPasswordPresenter resetPasswordPresenter()
    { return new ResetPasswordPresenter(new ResetPasswordModel(new RestClient( Constants.BASE_URL ) )); } }
