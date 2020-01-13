package com.tag.app.tagnearemployee.homescreen.completedlist;

import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;
import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.CompletedList;

public class CompletedListContract {
    public interface View extends BaseView
    {
        /**
         * completedlist fetch
         * @param completedList
         */
        void getCompletedlist(CompletedList completedList); }

    public interface Model extends BaseModel
    {
        /**
         *
         * @param token
         * @param page
         * @param modelCallback
         */
        void completedlist(String token,int page, ModelCallback modelCallback); }

    public interface Presenter extends BasePresenter
    {
        /**
         *
         * @param token
         * @param currentPage
         */
        void completed(String token, int currentPage); }
}
