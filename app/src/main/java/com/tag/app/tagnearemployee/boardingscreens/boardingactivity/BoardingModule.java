package com.tag.app.tagnearemployee.boardingscreens.boardingactivity;


import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = BoardingActivity.class,addsTo = ApplicationModule.class)
public class BoardingModule {
    @Provides
    BoardingPresenter boardingPresenter() {
        return new BoardingPresenter(new BoardingModel(new RestClient( Constants.BASE_URL ) )); }
}
