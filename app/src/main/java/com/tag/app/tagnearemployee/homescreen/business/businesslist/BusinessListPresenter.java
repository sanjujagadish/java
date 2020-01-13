package com.tag.app.tagnearemployee.homescreen.business.businesslist;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.BusinessList;

public class BusinessListPresenter implements BusinessListContract.Presenter
{
    private final BusinessListModel businessListModel;
    private BusinessListContract.View view;

    public BusinessListPresenter(BusinessListModel businessListModel)
    { this.businessListModel=businessListModel; }

    @Override
    public void fetchlist(String token, int currentPage)
    { businessListModel.businesslist( token,currentPage, new ModelCallback()
      { @Override
        public void onSuccess(Object object)
        { view.getBusinessList( (BusinessList) object ); }

        @Override
        public void onFailure(Throwable throwable)
        { view.onFailure( throwable ); }
    } );}

    @Override
    public void setView(Object view)
    { this.view= (BusinessListContract.View) view; }

    @Override
    public void clearView()
    { businessListModel.destroy(); }
}
