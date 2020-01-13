package com.tag.app.tagnearemployee.homescreen.business.businesslist;

import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;
import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.BusinessList;

public class BusinessListContract
{
    public interface View extends BaseView
    {
        /**
         *
         * @param businessList
         */
        void getBusinessList(BusinessList businessList);
    }

    public interface Model extends BaseModel
    {
        /**
         *
         * @param token
         * @param modelCallback
         */
        void businesslist(String token,int page,ModelCallback modelCallback);
    }

    public interface Presenter extends BasePresenter
    {
        /**
         *
         * @param token
         * @param currentPage
         */
        void fetchlist(String token,int currentPage);
    }
}
