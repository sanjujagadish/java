package com.tag.app.tagnearemployee.navigationview.myprofile;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.Constants;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.dashboard.DashBoardActivity;
import com.tag.app.tagnearemployee.homescreen.HomeScreenFragment;
import com.tag.app.tagnearemployee.pojomodels.MyProfile;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileFragment extends BaseFragment implements MyProfileContract.View
{   @Inject
    MyProfilePresenter myProfilePresenter;

    @BindView( R.id.iv_profile )
    ImageView iv_profile;
    @BindView( R.id.iv_imageprofile )
    CircleImageView iv_imageprofile;
    @BindView( R.id.tv_name )
    AppCompatTextView tv_name;
    @BindView( R.id.tv_phone )
    AppCompatTextView tv_phone;
    @BindView( R.id.tv_email )
    TextView tv_email;
    @BindView( R.id.tv_organization )
    TextView tv_organization;
    @BindView( R.id.tv_gender )
    TextView tv_gender;
    @BindView( R.id.tv_address )
    TextView tv_address;
    @BindView( R.id.toolbar )
    Toolbar toolbar;

    private MyProfile myProfile;

    @Override
    protected void init(Bundle savedInstanceState)
    {
      myProfilePresenter.setView( this );
      toolbar.setTitle( "My Profile" );
      myProfile = new Gson().fromJson( SharedPreference.getInstance().getString( "MYPROFILE" ), MyProfile.class );
      tv_name.setText( myProfile.getProfiledata().getFirstName()+" "+myProfile.getProfiledata().getLastName() );
      tv_phone.setText( myProfile.getProfiledata().getPhone() );
      tv_address.setText( myProfile.getProfiledata().getAddress() );
      tv_email.setText( myProfile.getProfiledata().getEmail() );
      tv_organization.setText( myProfile.getProfiledata().getBranchName() );
      if ( myProfile.getProfiledata().getGender().equals( 0 ) )
      { tv_gender.setText("Male"); }
      else if ( myProfile.getProfiledata().getGender().equals( 1 ) )
      { tv_gender.setText("Female"); }
      if (myProfile.getProfiledata().getProfileImg().isEmpty() )
      {   Picasso.get().load( R.drawable.notavailable )
                     .fit()
                     .placeholder( R.drawable.notavailable )
                     .into( iv_profile );

          Picasso.get().load( R.drawable.notavailable )
                  .fit()
                  .placeholder( R.drawable.notavailable )
                  .into( iv_imageprofile ); }
      else
      {   Picasso.get().load(Constants.IMAGE_URL+myProfile.getProfiledata().getProfileImg() )
                .fit()
                .placeholder( R.drawable.notavailable )
                .into( iv_profile );

          Picasso.get().load( Constants.IMAGE_URL+myProfile.getProfiledata().getProfileImg() )
                  .fit()
                  .placeholder( R.drawable.notavailable )
                  .into( iv_imageprofile ); }
    }

    @Override
    protected int getContentResource()
    { return R.layout.fragment_profile; }

    @Override
    protected Object getModule()
    { return new MyProfileModule(); }

    @Override
    public void onFailure(Throwable throwable)
    { hideProgress();
      showToast(throwable.getMessage()); }

      @OnClick(R.id.iv_back)
      void clickback()
      { ((DashBoardActivity)getActivity()).replaceFragment( new HomeScreenFragment(),HomeScreenFragment.class.getName() ); }
}
