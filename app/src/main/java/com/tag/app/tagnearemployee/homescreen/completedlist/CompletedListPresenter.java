package com.tag.app.tagnearemployee.homescreen.completedlist;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.CompletedList;

public class CompletedListPresenter implements CompletedListContract.Presenter
{
    private final CompletedListModel completedListModel;
    private CompletedListContract.View view;

    public CompletedListPresenter(CompletedListModel completedListModel)
    { this.completedListModel=completedListModel; }

    @Override
    public void completed(String token, int currentPage)
    { completedListModel.completedlist( token,currentPage, new ModelCallback() {
        @Override
        public void onSuccess(Object object) {
            view.getCompletedlist( (CompletedList) object );
        }

        @Override
        public void onFailure(Throwable throwable) {
            view.onFailure( throwable );
        }
    } );}

    @Override
    public void setView(Object view)
    { this.view= (CompletedListContract.View) view; }

    @Override
    public void clearView()
    { completedListModel.destroy(); }
}
