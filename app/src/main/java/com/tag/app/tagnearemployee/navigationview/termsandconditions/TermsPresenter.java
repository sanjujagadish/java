package com.tag.app.tagnearemployee.navigationview.termsandconditions;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.Terms;

public class TermsPresenter implements TermsContract.Presenter{
    private final TermsModel termsModel;
    private TermsContract.View view;

    public TermsPresenter(TermsModel termsModel) {
        this.termsModel=termsModel;
    }

    @Override
    public void setView(Object view) {
        this.view= (TermsContract.View) view;
    }

    @Override
    public void clearView() {
         termsModel.destroy();
    }

    @Override
    public void termsconditions(String token) {

         termsModel.termsvalid( token, new ModelCallback() {
             @Override
             public void onSuccess(Object object) {
                 view.getTerms( (Terms) object );
             }

             @Override
             public void onFailure(Throwable throwable) {
                view.onFailure( throwable );
             }
         } );
    }
}
