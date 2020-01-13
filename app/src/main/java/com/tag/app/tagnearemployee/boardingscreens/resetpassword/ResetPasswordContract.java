package com.tag.app.tagnearemployee.boardingscreens.resetpassword;

import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;
import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.ForgotPassword;

public interface ResetPasswordContract
{
    public interface View extends BaseView
    {
        /**
         *
         * @param resetPassword
         */
        void resetpassword(ForgotPassword resetPassword); }

    public interface Model extends BaseModel
    {
        /**
         *
         * @param resetPassword
         * @param modelCallback
         */
        void resetstatus(ForgotPassword resetPassword, ModelCallback modelCallback); }

    public interface Presenter extends BasePresenter
    {
        /**
         *
         * @param resetPassword
         */
        void resetpassword(ForgotPassword resetPassword); }
}
