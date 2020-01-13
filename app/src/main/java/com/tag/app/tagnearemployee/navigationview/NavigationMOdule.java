package com.tag.app.tagnearemployee.navigationview;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = NavigationFragment.class,addsTo = ApplicationModule.class)
public class NavigationMOdule
{
    @Provides
    NavigationPresenter navigationPresenter() {
        return new NavigationPresenter(new NavigationModel(new RestClient( Constants.BASE_URL ) ));
    }
}
