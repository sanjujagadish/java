package com.tag.app.tagnearemployee.homescreen;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = HomeScreenFragment.class,addsTo = ApplicationModule.class)
public class HomeScreenModule
{
    @Provides
    HomeScreenPresenter homeScreenPresenter()
    { return new HomeScreenPresenter(new HomeScreenModel(new RestClient( Constants.BASE_URL ))); }
}
