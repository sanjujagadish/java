package com.tag.app.tagnearemployee.navigationview.aboutus;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.AboutUs;

public class AboutUsPresenter implements AboutUsContract.Presenter{
    private final AboutUsModel aboutUsModel;
    private AboutUsContract.View view;

    public AboutUsPresenter(AboutUsModel aboutUsModel) {
        this.aboutUsModel=aboutUsModel;
    }

    @Override
    public void aboutvalid(String token) {
      aboutUsModel.aboutus( token, new ModelCallback() {
          @Override
          public void onSuccess(Object object) {
              view.getAboutUs( (AboutUs) object );
          }

          @Override
          public void onFailure(Throwable throwable) {
              view.onFailure( throwable );
          }
      } );
    }

    @Override
    public void setView(Object view)
    { this.view= (AboutUsContract.View) view; }

    @Override
    public void clearView()
    { aboutUsModel.destroy(); }
}
