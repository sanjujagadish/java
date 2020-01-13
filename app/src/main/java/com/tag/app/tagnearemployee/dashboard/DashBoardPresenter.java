package com.tag.app.tagnearemployee.dashboard;

public class DashBoardPresenter implements DashBoardContract.Presenter
{
    private final DashBoardModel dashBoardModel;
    private DashBoardContract.View view;

    public DashBoardPresenter(DashBoardModel dashBoardModel)
    { this.dashBoardModel=dashBoardModel; }

    @Override
    public void setView(Object view)
    { this.view= (DashBoardContract.View) view; }

    @Override
    public void clearView()
    { dashBoardModel.destroy(); }
}
