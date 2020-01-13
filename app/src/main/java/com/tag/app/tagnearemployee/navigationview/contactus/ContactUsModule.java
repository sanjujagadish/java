package com.tag.app.tagnearemployee.navigationview.contactus;

import com.tag.app.tagnearemployee.appUtils.ApplicationModule;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.retrofit.RestClient;

import dagger.Module;
import dagger.Provides;

@Module(injects = ContactUsFragment.class,addsTo = ApplicationModule.class)
public class ContactUsModule
{   @Provides
    ContactUsPresenter contactUsPresenter()
    { return new ContactUsPresenter(new ContactUsModel(new RestClient( Constants.BASE_URL ) )); } }
