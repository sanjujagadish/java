package com.tag.app.tagnearemployee.homescreen.search;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = SearchFragment.class,addsTo = ApplicationModule.class)
public class SearchModule {
    @Provides
    SearchPresenter searchPresenter()
    { return new SearchPresenter(new SearchModel(new RestClient( Constants.BASE_URL ) )); }
}
