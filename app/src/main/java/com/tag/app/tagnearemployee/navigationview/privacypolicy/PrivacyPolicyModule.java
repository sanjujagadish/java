package com.tag.app.tagnearemployee.navigationview.privacypolicy;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = PrivacyPolicyFragment.class,addsTo = ApplicationModule.class)
public class PrivacyPolicyModule {
    @Provides
    PrivacyPolicyPresenter termsPresenter() {
        return new PrivacyPolicyPresenter(new PrivacyPolicyModel(new RestClient( Constants.BASE_URL ) ));
    }
}
