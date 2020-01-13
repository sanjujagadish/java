package com.tag.app.tagnearemployee.homescreen.business.businesslist;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = BusinessListFragment.class,addsTo = ApplicationModule.class)
public class BusinessListModule {

    @Provides
    BusinessListPresenter businessListPresenter() {
        return new BusinessListPresenter(new BusinessListModel(new RestClient( Constants.BASE_URL ) ));
    }
}
