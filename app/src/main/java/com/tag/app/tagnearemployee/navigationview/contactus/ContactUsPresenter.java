package com.tag.app.tagnearemployee.navigationview.contactus;


import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.ContactUs;

public class ContactUsPresenter implements ContactUsContract.Presenter{
    private final ContactUsModel contactUsModel;
    private ContactUsContract.View view;

    public ContactUsPresenter(ContactUsModel contactUsModel) {
        this.contactUsModel=contactUsModel;
    }

    @Override
    public void contactDetails(String token) {
        contactUsModel.getContact( token, new ModelCallback() {
            @Override
            public void onSuccess(Object object) {
                view.getContactdetails( (ContactUs) object );
            }

            @Override
            public void onFailure(Throwable throwable) {
               view.onFailure( throwable );
            }
        } );
    }

    @Override
    public void setView(Object view) {
         this.view= (ContactUsContract.View) view;
    }

    @Override
    public void clearView() {
       contactUsModel.destroy();
    }
}
