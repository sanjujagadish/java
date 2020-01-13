package com.tag.app.tagnearemployee.navigationview.contactus;

import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;
import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.ContactUs;

public interface ContactUsContract {

    public interface View extends BaseView
    {
        /**
         *
         * @param contactUs
         */
        void getContactdetails(ContactUs contactUs);
    }

    public interface Model extends BaseModel
    {
        /**
         *
         * @param token
         */
        void getContact(String token, ModelCallback modelCallback);
    }

    public interface Presenter extends BasePresenter
    {
        /**
         *
         * @param token
         */
        void contactDetails(String token);
    }
}
