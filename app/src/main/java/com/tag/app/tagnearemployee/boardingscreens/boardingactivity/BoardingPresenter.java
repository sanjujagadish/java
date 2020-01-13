package com.tag.app.tagnearemployee.boardingscreens.boardingactivity;


import com.tag.app.tagnearemployee.base.ModelCallback;

public class BoardingPresenter implements BoardingContract.Presenter {

    private final BoardingModel boardingModel;
    private BoardingContract.View view;

    public BoardingPresenter(BoardingModel boardingModel) {
        this.boardingModel=boardingModel;
    }

    @Override
    public void setView(Object view) {
      this.view= (BoardingContract.View) view;
    }

    @Override
    public void clearView()
    { boardingModel.destroy(); }

}
