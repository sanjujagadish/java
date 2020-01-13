package com.tag.app.tagnearemployee.navigationview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.appUtils.Utils;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.dashboard.DashBoardActivity;
import com.tag.app.tagnearemployee.navigationview.aboutus.AboutUsFragment;
import com.tag.app.tagnearemployee.navigationview.contactus.ContactUsFragment;
import com.tag.app.tagnearemployee.navigationview.myprofile.MyProfileFragment;
import com.tag.app.tagnearemployee.navigationview.privacypolicy.PrivacyPolicyFragment;
import com.tag.app.tagnearemployee.navigationview.termsandconditions.TermsAndConditionsFragment;
import com.tag.app.tagnearemployee.pojomodels.MyProfile;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class NavigationFragment extends BaseFragment implements NavigationContract.View,NavigationView.OnNavigationItemSelectedListener
{
    @Inject
    NavigationPresenter navigationPresenter;

    @BindView( R.id.nav_view )
    NavigationView navigationView;

    private MyProfile myProfile;

    @Override
    protected void init(Bundle savedInstanceState)
    {   navigationPresenter.setView( this );
        navigationView.setNavigationItemSelectedListener(this);
        setNavDrawerWithUserData(); }

        @SuppressLint("SetTextI18n")
        private void setNavDrawerWithUserData()
        {   View headerLayout = navigationView.getHeaderView(0 );
            TextView tv_name = headerLayout.findViewById(R.id.tv_name);
            TextView tv_phone = headerLayout.findViewById(R.id.tv_phone);
            CircleImageView imageView = headerLayout.findViewById( R.id.imageView );

         myProfile = new Gson().fromJson( SharedPreference.getInstance().getString("MYPROFILE"),MyProfile.class);
         tv_name.setText(myProfile.getProfiledata().getFirstName());
         tv_phone.setText(myProfile.getProfiledata().getPhone());
         Picasso.get()
         .load( Constants.IMAGE_URL + myProfile.getProfiledata().getProfileImg() )
         .centerCrop()
         .fit()
         .placeholder( R.drawable.notavailable )
         .into(imageView);
        }

    @Override
    protected int getContentResource()
    { return R.layout.fragment_navigation; }

    @Override
    protected Object getModule()
    { return new NavigationMOdule(); }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected( MenuItem item )
    {   // Handle navigation view item clicks here.
        int id = item.getItemId();

        if ( id == R.id.nav_profile )
        { ((DashBoardActivity)getActivity()).replaceFragment( new MyProfileFragment(),MyProfileFragment.class.getName() ); }
        else if ( id == R.id.nav_aboutus )
        { ((DashBoardActivity)getActivity()).replaceFragment( new AboutUsFragment(),AboutUsFragment.class.getName() ); }
        else if ( id == R.id.nav_contactus )
        { ((DashBoardActivity)getActivity()).replaceFragment( new ContactUsFragment(),ContactUsFragment.class.getName() ); }
        else if ( id == R.id.nav_policy )
        { ((DashBoardActivity)getActivity()).replaceFragment( new PrivacyPolicyFragment(),PrivacyPolicyFragment.class.getName() ); }
        else if ( id==R.id.nav_terms )
        { ((DashBoardActivity)getActivity()).replaceFragment( new TermsAndConditionsFragment(),TermsAndConditionsFragment.class.getName() ); }
        else if ( id == R.id.nav_logout )
        { Utils.performLogout( (Activity) getContext() ); }
        return true;
    }

    @Override
    public void onFailure(Throwable throwable)
    { hideProgress();
      showToast( throwable.getMessage() ); }
 }
