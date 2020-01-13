package com.tag.app.tagnearemployee.navigationview.termsandconditions;


import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.pojomodels.Terms;
import com.tag.app.tagnearemployee.pojomodels.TermsCondition;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermsAndConditionsFragment extends BaseFragment implements TermsContract.View{

    @Inject
    TermsPresenter termsPresenter;

    @BindView( R.id.tv_intro )
    TextView tv_intro;

    @BindView( R.id.tv_generative )
    TextView tv_generative;

    @BindView( R.id.tv_autoencoders )
    TextView tv_autoencoders;

    private Terms terms;
    private List<TermsCondition> termsConditions=new ArrayList<>( );

    @Override
    protected void init(Bundle savedInstanceState)
    {   termsPresenter.setView( this );

        /**
         * Api call
         */
        showProgress();
        termsPresenter.termsconditions( SharedPreference.getInstance().getAuthToken() ); }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_terms_and_conditions;
    }

    @Override
    protected Object getModule() {
        return new TermsModule();
    }

    @Override
    public void getTerms(Terms terms) {
        hideProgress();
        this.terms = terms;
      tv_intro.setText( terms.getTerms().get(0).getP()+terms.getTerms().get( 1 ).getP() );
      tv_generative.setText( terms.getTerms().get(0).getP() );
      tv_autoencoders.setText( terms.getTerms().get( 1 ).getP());
    }

    @Override
    public void onFailure(Throwable throwable)
    {    hideProgress();
         showToast( throwable.getMessage() ); }
}
