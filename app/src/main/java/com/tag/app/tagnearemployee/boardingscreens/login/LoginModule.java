package com.tag.app.tagnearemployee.boardingscreens.login;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = LoginFragment.class,addsTo = ApplicationModule.class)
public class LoginModule {
    @Provides
    LoginPresenter loginPresenter() {
        return new LoginPresenter(new LoginModel(new RestClient( Constants.BASE_URL ) ));
    }
}
