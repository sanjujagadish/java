package com.tag.app.tagnearemployee.navigationview.privacypolicy;


import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.pojomodels.Privacy;
import com.tag.app.tagnearemployee.pojomodels.PrivacyPolicy;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivacyPolicyFragment extends BaseFragment implements PrivacyPolicyContract.View{

    @Inject
    PrivacyPolicyPresenter privacyPolicyPresenter;

    @BindView( R.id.tv_generative )
    TextView tv_generative;

    @BindView( R.id.tv_autoencoders )
    TextView tv_autoencoders;


    private List<Privacy> privacyPolicyList=new ArrayList<>(  );
    private PrivacyPolicy privacyPolicy;

    @Override
    protected void init(Bundle savedInstanceState)
    {   privacyPolicyPresenter.setView( this );

        /**
         * Api call
         */
        showProgress();
        privacyPolicyPresenter.privacypolicy( SharedPreference.getInstance().getAuthToken() ); }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_privacypolicy;
    }

    @Override
    protected Object getModule() {
        return new PrivacyPolicyModule();
    }

    @Override
    public void getPolicy(PrivacyPolicy privacyPolicy)
    {   hideProgress();
        this.privacyPolicy = privacyPolicy;
        tv_generative.setText( privacyPolicy.getPrivacy().get( 0 ).getP() );
        tv_autoencoders.setText( privacyPolicy.getPrivacy().get(0).getP() ); }

    @Override
    public void onFailure(Throwable throwable)
    {    hideProgress();
         showToast( throwable.getMessage() ); }
}
