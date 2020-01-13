package com.tag.app.tagnearemployee.dashboard;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = DashBoardActivity.class,addsTo = ApplicationModule.class)
public class DashBoardModule
{
    @Provides
    DashBoardPresenter dashBoardPresenter() {
        return new DashBoardPresenter(new DashBoardModel(new RestClient( Constants.BASE_URL ) ));
    }
}
