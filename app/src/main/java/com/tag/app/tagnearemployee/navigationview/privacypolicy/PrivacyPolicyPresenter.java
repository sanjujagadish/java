package com.tag.app.tagnearemployee.navigationview.privacypolicy;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.PrivacyPolicy;

public class PrivacyPolicyPresenter implements PrivacyPolicyContract.Presenter{
    private final PrivacyPolicyModel termsModel;
    private PrivacyPolicyContract.View view;

    public PrivacyPolicyPresenter(PrivacyPolicyModel termsModel) {
        this.termsModel=termsModel;
    }

    @Override
    public void setView(Object view) {
        this.view= (PrivacyPolicyContract.View) view;
    }

    @Override
    public void clearView() {
         termsModel.destroy();
    }

    @Override
    public void privacypolicy(String token) {

         termsModel.policiesvalid( token, new ModelCallback() {
             @Override
             public void onSuccess(Object object)
             { view.getPolicy( (PrivacyPolicy) object ); }

             @Override
             public void onFailure(Throwable throwable) {
                view.onFailure( throwable );
             }
         } );
    }
}
