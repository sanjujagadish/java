package com.tag.app.tagnearemployee.homescreen.pendingdetails;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = PendingDetailFragment.class,addsTo = ApplicationModule.class)
public class PendingDetailModule
{
    @Provides
    PendingDetailPresenter pendingDetailPresenter() {
        return new PendingDetailPresenter(new PendingDetailModel(new RestClient(Constants.BASE_URL)));
    }
}
