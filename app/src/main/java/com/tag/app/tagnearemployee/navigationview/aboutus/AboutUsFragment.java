package com.tag.app.tagnearemployee.navigationview.aboutus;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.pojomodels.AboutUs;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends BaseFragment implements AboutUsContract.View{

    @Inject
    AboutUsPresenter aboutUsPresenter;

    @BindView( R.id.img_about )
    ImageView img_about;
    @BindView( R.id.tv_shortdescription )
    TextView tv_shortdescription;
    @BindView( R.id.tv_aboutus )
    TextView tv_aboutus;
    @BindView( R.id.tv_recognition )
    TextView tv_recognition;

    private AboutUs aboutUs;

    @Override
    protected void init(Bundle savedInstanceState) {
      aboutUsPresenter.setView( this );

        /**
         * Api call
         */
      showProgress();
      aboutUsPresenter.aboutvalid( SharedPreference.getInstance().getAuthToken() );
    }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_about_us; }

    @Override
    protected Object getModule()
    { return new AboutUsModule(); }

    @Override
    public void getAboutUs(AboutUs aboutUs) {
     hideProgress();
     this.aboutUs=aboutUs;

     tv_aboutus.setText( aboutUs.getAboutus().get( 0 ).getP()+","+aboutUs.getAboutus().get( 1 ).getP() );
     tv_shortdescription.setText( aboutUs.getAboutus().get( 0 ).getP() );
     tv_recognition.setText( aboutUs.getAboutus().get( 1 ).getP() );

    }

    @Override
    public void onFailure(Throwable throwable) {
       hideProgress();
       showToast( throwable.getMessage() );
    }
}
