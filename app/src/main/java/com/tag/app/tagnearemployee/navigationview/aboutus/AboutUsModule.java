package com.tag.app.tagnearemployee.navigationview.aboutus;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = AboutUsFragment.class,addsTo = ApplicationModule.class)
public class AboutUsModule {
    @Provides
    AboutUsPresenter aboutUsPresenter()
    { return new AboutUsPresenter(new AboutUsModel(new RestClient( Constants.BASE_URL ) )); } }
