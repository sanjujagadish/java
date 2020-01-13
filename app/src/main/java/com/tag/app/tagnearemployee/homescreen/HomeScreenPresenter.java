package com.tag.app.tagnearemployee.homescreen;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.CategoryList;
import com.tag.app.tagnearemployee.pojomodels.MyProfile;
import com.tag.app.tagnearemployee.pojomodels.PendingList;
import com.tag.app.tagnearemployee.pojomodels.DutyStatus;

public class HomeScreenPresenter implements HomeScreenContract.Presenter
{
    private final HomeScreenModel homeScreenModel;
    private HomeScreenContract.View view;

    public HomeScreenPresenter(HomeScreenModel homeScreenModel)
    { this.homeScreenModel=homeScreenModel; }

    public void setView(Object view)
    { this.view= (HomeScreenContract.View) view; }

    @Override
    public void clearView()
    { homeScreenModel.destroy(); }

    @Override
    public void fetchinglist(String token,int page) {
        homeScreenModel.fetchlist( token,page,new ModelCallback() {
            @Override
            public void onSuccess(Object object) {
                view.getList( (PendingList) object );
            }

            @Override
            public void onFailure(Throwable throwable) {
                view.onFailure( throwable );
            }
        } ); }

    @Override
    public void checkshopstatus(String token) {
        homeScreenModel.shopstatus( token, new ModelCallback() {
            @Override
            public void onSuccess(Object object) {
                view.setStatus( (DutyStatus) object ); }

            @Override
            public void onFailure(Throwable throwable) {
               view.onFailure( throwable );
            }
        } ); }

    @Override
    public void profilefetch(String token) {
        homeScreenModel.profiledetails( token, new ModelCallback() {
            @Override
            public void onSuccess(Object object) {
             view.getProfiel( (MyProfile) object );
            }

            @Override
            public void onFailure(Throwable throwable) {
             view.onFailure( throwable );
            }
        } ); }

    @Override
    public void categories(String token) {
        homeScreenModel.fetchcategories( token, new ModelCallback() {
            @Override
            public void onSuccess(Object object) {
                view.getCategories( (CategoryList) object );
            }

            @Override
            public void onFailure(Throwable throwable) {
               view.onFailure( throwable );
            }
        } ); }

}
