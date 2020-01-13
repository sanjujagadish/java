package com.tag.app.tagnearemployee.homescreen;

import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;
import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.CategoryList;
import com.tag.app.tagnearemployee.pojomodels.MyProfile;
import com.tag.app.tagnearemployee.pojomodels.PendingList;
import com.tag.app.tagnearemployee.pojomodels.DutyStatus;

public interface HomeScreenContract {

    public interface View extends BaseView
    {
        /**
         * View for pending details
         * @param pendingList
         */
        void getList(PendingList pendingList);

        /**
         * View Shopstatus
         * @param shopstatus
         */
        void setStatus(DutyStatus shopstatus);

        /**
         * My profile
         * @param myProfile
         */
        void getProfiel(MyProfile myProfile);

        /**
         * view for categories
         * @param categoryList
         */
        void getCategories(CategoryList categoryList);
    }

    public interface Model extends BaseModel
    {
        /**
         * Call for pending list
         * @param token
         * @param modelCallback
         */
        void fetchlist(String token,int page, ModelCallback modelCallback);

        /**
         * Call for shop status
         * @param token
         * @param modelCallback
         */
        void shopstatus(String token, ModelCallback modelCallback);

        /**
         * Myprofile details page
         * @param token
         */
        void profiledetails(String token, ModelCallback modelCallback);

        /**
         * model class for categories
         * @param token
         * @param modelCallback
         */
        void fetchcategories(String token, ModelCallback modelCallback);
    }

    public interface Presenter extends BasePresenter
    {
        /**
         * Pending list details
         * @param token
         */
        void fetchinglist(String token,int page);

        /**
         * shop status
         * @param token
         */
        void checkshopstatus(String token);

        /**
         * Myprofile call
         * @param myProfile
         */
        void profilefetch(String myProfile);

        /**
         * categories fetch
         * @param token
         */
        void categories(String token);

    }
}