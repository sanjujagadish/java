package com.tag.app.tagnearemployee.homescreen.business.businessupdate;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.BusinessDatum;
import com.tag.app.tagnearemployee.pojomodels.BusinessProResponse;
import com.tag.app.tagnearemployee.pojomodels.ImageResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BusinessUpdatePresenter implements BusinessUpdateContract.Presenter {
    private final BusinessUpdateModel businessProModel;
    private BusinessUpdateContract.View view;

    public BusinessUpdatePresenter(BusinessUpdateModel businessProModel)
    { this.businessProModel=businessProModel; }

    @Override
    public void setView(Object view)
    { this.view= (BusinessUpdateContract.View) view; }

    @Override
    public void clearView()
    { businessProModel.destroy(); }

    @Override
    public void businessproupdate(BusinessDatum businessPro, String token) {
        businessProModel.businessproupdate(businessPro,token,new ModelCallback() {
            @Override
            public void onSuccess(Object object) {
                view.businessupdate( (BusinessProResponse) object );
            }

            @Override
            public void onFailure(Throwable throwable) {
                view.onFailure( throwable );
            }
        } );
    }

    public void uploadvalidate(Map<String, RequestBody> payload,Map<String, RequestBody> payloads, MultipartBody.Part body) {
        businessProModel.uploadsuccess(payload,payloads,body, new ModelCallback() {
            @Override
            public void onSuccess(Object object) {
                view.imageupload( (ImageResponse) object );
            }

            @Override
            public void onFailure(Throwable throwable) {
                view.onFailure( throwable );
            }
        } ); }
}
