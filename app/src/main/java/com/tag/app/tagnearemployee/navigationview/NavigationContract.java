package com.tag.app.tagnearemployee.navigationview;

import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;

public interface NavigationContract {

    public interface View extends BaseView {}

    public interface Model extends BaseModel {}

    public interface Presenter extends BasePresenter {}
}
