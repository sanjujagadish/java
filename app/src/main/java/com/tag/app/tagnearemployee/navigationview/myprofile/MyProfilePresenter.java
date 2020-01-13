package com.tag.app.tagnearemployee.navigationview.myprofile;

public class MyProfilePresenter implements MyProfileContract.Presenter {

    private final MyProfileModel myProfileModel;
    private MyProfileContract.View view;

    public MyProfilePresenter(MyProfileModel myProfileModel)
    { this.myProfileModel=myProfileModel; }

    public void setView(Object view)
    { this.view= (MyProfileContract.View) view; }

    @Override
    public void clearView()
    { myProfileModel.destroy(); }
}
