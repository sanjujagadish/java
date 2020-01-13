package com.tag.app.tagnearemployee.boardingscreens.login;

import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;
import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.Employee;

public class LoginContract
{
    public interface View extends BaseView
    {
        /**
         * View login success
         * @param customer
         */
        void loginsuccess(Employee customer); }

    public interface Presenter extends BasePresenter
    {
        /**
         * Presenter to validate login
         * @param customer
         */
        void loginvalidate(Employee customer); }

    public interface Model extends BaseModel
    {
        /**
         *Model class for login
         */
        void logincall(Employee customer, ModelCallback modelCallback); }
}
