package com.tag.app.tagnearemployee.navigationview;

public class NavigationPresenter implements NavigationContract.Presenter {
    private final NavigationModel navigationModel;
    private NavigationContract.View view;

    public NavigationPresenter(NavigationModel navigationModel) {
        this.navigationModel=navigationModel; }

    @Override
    public void setView(Object view) {
      this.view= (NavigationContract.View) view; }

    @Override
    public void clearView() {
      navigationModel.destroy(); }
}
