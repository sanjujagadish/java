package com.tag.app.tagnearemployee.navigationview.termsandconditions;


import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;
import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.Terms;

public class TermsContract {
    public interface View extends BaseView
    {
        /**
         *
         * @param terms
         */
        void getTerms(Terms terms); }

    public interface Model extends BaseModel
    {
        /**
         *
         * @param modelCallback
         */
        void termsvalid(String token, ModelCallback modelCallback);
    }

    public interface Presenter extends BasePresenter
    {
        /**
         *
         * @param token
         */
        void termsconditions(String token);
    }
}
