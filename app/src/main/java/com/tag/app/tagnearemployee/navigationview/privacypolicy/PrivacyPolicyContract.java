package com.tag.app.tagnearemployee.navigationview.privacypolicy;


import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;
import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.PrivacyPolicy;

public class PrivacyPolicyContract {
    public interface View extends BaseView
    {
        /**
         *
         * @param terms
         */
        void getPolicy(PrivacyPolicy terms); }

    public interface Model extends BaseModel
    {
        /**
         *
         * @param modelCallback
         */
        void policiesvalid(String token, ModelCallback modelCallback);
    }

    public interface Presenter extends BasePresenter
    {
        /**
         *
         * @param token
         */
        void privacypolicy(String token);
    }
}
