package com.tag.app.tagnearemployee.boardingscreens.forgototpverify;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = OtpForgotPasswordFragment.class,addsTo = ApplicationModule.class)
public class OtpForgotPwrdModule
{   @Provides
    OtpForgotPwrdPresenter otpForgotPwrdPresenter()
    { return new OtpForgotPwrdPresenter( new OtpForgotPwrdModel( new RestClient( Constants.BASE_URL ) )); } }
