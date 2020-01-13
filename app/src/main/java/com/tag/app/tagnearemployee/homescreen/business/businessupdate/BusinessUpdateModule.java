package com.tag.app.tagnearemployee.homescreen.business.businessupdate;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = BusinessUpdateFragment.class,addsTo = ApplicationModule.class)
public class BusinessUpdateModule {
    @Provides
    BusinessUpdatePresenter businessProPresenter()
    { return new BusinessUpdatePresenter(new BusinessUpdateModel(new RestClient( Constants.BASE_URL ) )); }
}
