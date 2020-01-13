package com.tag.app.tagnearemployee.boardingscreens.forgotpassword;

import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;
import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.ForgotPassword;

public class ForgotPasswordContract {

    public interface View extends BaseView {
     void forgotpassword(ForgotPassword forgotPassword);
    }

    public interface Model extends BaseModel {
        void forgotvalid(ForgotPassword ForgotPassword, ModelCallback modelCallback); }

    public interface Presenter extends BasePresenter {
        void forgotpassword(ForgotPassword ForgotPassword); }
}
