package com.tag.app.tagnearemployee.homescreen.completedlist;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = CompletedListFragment.class,addsTo = ApplicationModule.class)
public class CompletedListModule
{
    @Provides
    CompletedListPresenter completedListPresenter() {
        return new CompletedListPresenter(new CompletedListModel(new RestClient( Constants.BASE_URL ) ));
    }
}
