package com.tag.app.tagnearemployee.homescreen.business;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = BusinessProFragment.class,addsTo = ApplicationModule.class)
public class BusinessProModule {
    @Provides
    BusinessProPresenter businessProPresenter()
    { return new BusinessProPresenter(new BusinessProModel(new RestClient( Constants.BASE_URL ) )); }
}
