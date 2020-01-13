package com.tag.app.tagnearemployee.navigationview.myprofile;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = MyProfileFragment.class,addsTo = ApplicationModule.class)
public class MyProfileModule
{   @Provides
    MyProfilePresenter myProfilePresenter()
    { return new MyProfilePresenter(new MyProfileModel(new RestClient( Constants.BASE_URL ) )); } }
