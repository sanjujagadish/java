package com.example.anjum.nithyostava_artist.artistinfo.artistprofile;


import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anjum.nithyostava_artist.Dashboard.DashboardActivity;
import com.example.anjum.nithyostava_artist.Onboarding.User;
import com.example.anjum.nithyostava_artist.R;
import com.example.anjum.nithyostava_artist.base.BaseFragment;
import com.example.anjum.nithyostava_artist.utils.SharedPreference;
import com.google.gson.Gson;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ArtistProfileFragment extends BaseFragment implements ArtistProfileContract.View{

    @Inject
    ArtistProfilePresenter userProfilePresenter;

    @BindView( R.id.tv_Title)
    AppCompatTextView tv_Title;
    @BindView( R.id.iv_profile )
    ImageView iv_profile;
    @BindView( R.id.tv_name )
    TextView tv_name;
    @BindView( R.id.tv_fname)
    TextView tv_fname;
    @BindView( R.id.et_mobile )
    EditText et_mobile;
    @BindView( R.id.et_email )
    EditText et_emaill;
    @BindView( R.id.et_skills )
    TextView et_skills;
    @BindView( R.id.et_address )
    EditText et_address;

    @Override
    protected void init(Bundle savedInstanceState)
    {   userProfilePresenter.setView(this);
        ((DashboardActivity)getActivity()).getSupportActionBar().hide();
        tv_Title.setText("My Profile");
        User user = new Gson().fromJson( SharedPreference.getInstance().getString("user"), User.class);
        tv_fname.setText(user.getFirstName()+" "+user.getLastName() );
        tv_name.setText(user.getFirstName()+" "+user.getLastName() );
        et_mobile.setText( user.getPhone() );
        et_emaill.setText( user.getEmail() );
        et_skills.setText( (CharSequence) user.getSkills() );
        et_address.setText( user.getAddress() );
    }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_artist_profile;
    }

    @Override
    protected Object getModule()
    { return new ArtistProfileModule(); }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @OnClick(R.id.iv_Back)
    protected void onClickBack()
    {   if ( getActivity()!=null )
        getActivity().onBackPressed(); }

    @Override
    public void userprofileRes(User user) {

    }
}
