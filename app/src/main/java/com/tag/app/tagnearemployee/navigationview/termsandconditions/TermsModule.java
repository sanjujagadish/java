package com.tag.app.tagnearemployee.navigationview.termsandconditions;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = TermsAndConditionsFragment.class,addsTo = ApplicationModule.class)
public class TermsModule {
    @Provides
    TermsPresenter termsPresenter() {
        return new TermsPresenter(new TermsModel(new RestClient( Constants.BASE_URL ) ));
    }
}
