package com.tag.app.tagnearemployee.homescreen.search;

import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;
import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.SearchResponse;

public interface SearchContract {

    public interface View extends BaseView
    {
        /**
         *
         * @param searchList
         */
        void searchresult(SearchResponse searchList);
    }

    public interface Model extends BaseModel
    {
        /**
         *  @param token
         * @param searchkey
         * @param modelCallback
         */
        void searchlist(String token, String searchkey, ModelCallback modelCallback);
    }

    public interface Presenter extends BasePresenter
    {
        /**
         *
         * @param token
         * @param searchkey
         */
        void searchfrom(String token, String searchkey);
    }
}
