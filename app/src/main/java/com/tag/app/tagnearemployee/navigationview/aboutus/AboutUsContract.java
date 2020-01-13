package com.tag.app.tagnearemployee.navigationview.aboutus;

import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;
import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.AboutUs;

public class AboutUsContract {

public interface View extends BaseView
{
    /**
     *
     * @param aboutUs
     */
    void getAboutUs(AboutUs aboutUs);
}

public interface Model extends BaseModel
{
    /**
     *
     * @param modelCallback
     */
    void aboutus(String token, ModelCallback modelCallback);
}

public interface Presenter extends BasePresenter
{
    /**
     *
     * @param token
     */
    void aboutvalid(String token);
}

}
