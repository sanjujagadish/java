package com.tag.app.tagnearemployee.navigationview.contactus;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.SharedPreference;
import com.tag.app.tagnearemployee.base.BaseFragment;
import com.tag.app.tagnearemployee.pojomodels.Contact;
import com.tag.app.tagnearemployee.pojomodels.ContactUs;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends BaseFragment implements ContactUsContract.View {

    @Inject
    ContactUsPresenter contactUsPresenter;

    @BindView( R.id.btn_contact )
    AppCompatButton btn_contact;

    @BindView( R.id.et_name)
    AppCompatEditText et_name;

    @BindView( R.id.et_phone)
    AppCompatEditText et_phone;

    @BindView( R.id.et_message)
    AppCompatEditText et_message;

    private ContactUs contactUs;
    private List<Contact> contacts=new ArrayList<>( );

    @Override
    protected void init(Bundle savedInstanceState)
    {   contactUsPresenter.setView( this );
        /**
         * API CALL
         */
        showProgress();
        contactUsPresenter.contactDetails( SharedPreference.getInstance().getAuthToken() ); }

    @Override
    protected int getContentResource()
    { return R.layout.fragment_contact_us; }

    @Override
    protected Object getModule()
    { return new ContactUsModule(); }

    @Override
    public void getContactdetails(ContactUs contactUs)
    { hideProgress();
      this.contactUs=contactUs;
      contacts.addAll( contactUs.getContactus() ); }

    @Override
    public void onFailure(Throwable throwable)
    { hideProgress();
      showToast( throwable.getMessage() ); }
}
