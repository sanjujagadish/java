package com.tag.app.tagnearemployee.homescreen.search;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.SearchResponse;

public class SearchPresenter implements SearchContract.Presenter
{
    private final SearchModel searchModel;
    private SearchContract.View view;

    public SearchPresenter(SearchModel searchModel)
    { this.searchModel=searchModel; }

    @Override
    public void searchfrom(String token, String searchkey)
    { searchModel.searchlist( token,searchkey, new ModelCallback()
      { @Override
        public void onSuccess(Object object)
        { view.searchresult( (SearchResponse) object ); }

        @Override
        public void onFailure(Throwable throwable)
        { view.onFailure( throwable ); }
    } ); }

    @Override
    public void setView(Object view)
    { this.view= (SearchContract.View) view; }

    @Override
    public void clearView()
    { searchModel.destroy(); }
}
